#!/usr/bin/env groovy
import groovy.xml.XmlSlurper

File file = args ? new File(args[0]) : new File("WRP0001.xml")
assert file.exists(): "Nie widzę pliku: ${file.absolutePath}"

def root = new XmlSlurper(false, false).parse(file)
def errors = []

def asInt = { String path, String v ->
    if (!v?.trim()) return null
    try { Integer.parseInt(v.trim()) }
    catch (Exception e) { errors << "${path} musi być int, jest: '${v}'"; return null }
}

def asBool = { String path, String v ->
    if (!v?.trim()) return null
    if (!(v.trim() in ['true','false'])) { errors << "${path} musi być boolean true/false, jest: '${v}'"; return null }
    return v.trim() == 'true'
}

def text = { node, String name ->
    node?."$name"?.text()?.trim()
}

def walkGroup
walkGroup = { node, String path ->

    // --- basic required fields for a group
    def nameXml = text(node, 'nameXML')
    def name = text(node, 'name')
    if (!nameXml) errors << "${path}: brak nameXML"
    if (!name) errors << "${path}: brak name"

    // --- booleans
    ['groupEnabled','groupVisibility','root'].each { f ->
        def v = text(node, f)
        if (v != null && v != '') asBool("${path}.${f}", v)
    }

    // --- multiplicity
    def min = asInt("${path}.multiplicityMin", text(node,'multiplicityMin'))
    def max = asInt("${path}.multiplicityMax", text(node,'multiplicityMax'))
    if (min != null && max != null && min > max) {
        errors << "${path}: multiplicityMin (${min}) > multiplicityMax (${max})"
    }

    // --- position
    asInt("${path}.position", text(node,'position'))

    // --- validate data items inside this group
    def itemsContainer = node.dataItem
    itemsContainer?.dataItem?.eachWithIndex { it, idx ->
        def ip = "${path}.dataItem[${idx}]"
        def ix = text(it,'nameXML')
        if (!ix) errors << "${ip}: brak nameXML"

        asBool("${ip}.required", text(it,'required') ?: '')
        asBool("${ip}.readOnlyMode", text(it,'readOnlyMode') ?: '')
        asBool("${ip}.encrypted", text(it,'encrypted') ?: '')
        asBool("${ip}.criteria", text(it,'criteria') ?: '')
        asInt("${ip}.position", text(it,'position'))

        def minL = asInt("${ip}.minLength", text(it,'minLength'))
        def maxL = asInt("${ip}.maxLength", text(it,'maxLength'))
        if (minL != null && maxL != null && minL > maxL) {
            errors << "${ip}: minLength (${minL}) > maxLength (${maxL})"
        }

        def type = text(it,'type')
        if (!type) errors << "${ip}: brak type"
    }

    // --- children groups
    def kids = node.childDataGroups?.childDataGroups
    if (kids && kids.size() > 0) {

        // sibling duplicates of nameXML
        def sib = kids.collect { text(it,'nameXML') }.findAll { it }
        def dup = sib.groupBy { it }.findAll { k,v -> v.size() > 1 }.keySet()
        if (!dup.isEmpty()) {
            errors << "${path}: duplikaty nameXML wśród rodzeństwa: ${dup.join(', ')}"
        }

        kids.eachWithIndex { k, i ->
            def kName = text(k,'nameXML') ?: "idx_${i}"
            walkGroup(k, "${path}/${kName}")
        }
    }
}

// start from the root dataGroup
def dg = root.dataGroup
if (!dg || dg.size() == 0) {
    errors << "Brak root.dataGroup"
} else {
    walkGroup(dg, "/dataGroup")
}

if (!errors.isEmpty()) {
    System.err.println("TREE VALIDATION FAILED (${errors.size()}):")
    errors.each { System.err.println(" - ${it}") }
    System.exit(2)
}

println("TREE VALIDATION OK")
