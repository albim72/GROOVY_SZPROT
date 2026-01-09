#!/usr/bin/env groovy
import groovy.xml.XmlSlurper

File file = (args && args[0]) ? new File(args[0]) : new File("WRP0001.xml")

def errors = []
if (!file.exists()) errors << "Nie widzę pliku: ${file.absolutePath}"

def root = null
if (errors.isEmpty()) {
    try {
        root = new XmlSlurper(false, false).parse(file)
    } catch (Exception e) {
        errors << "Nie udało się sparsować XML: ${e.class.simpleName}: ${e.message}"
    }
}

// =========================
// HELPERS
// =========================
def text = { node, String name ->
    try { node?."${name}"?.text()?.trim() } catch (Exception ignore) { null }
}

def asInt = { String path, String v ->
    if (!v?.trim()) return null
    try { Integer.parseInt(v.trim()) }
    catch (Exception e) { errors << "${path} musi być int, jest: '${v}'"; return null }
}

def asBool = { String path, String v ->
    if (!v?.trim()) return
    def t = v.trim()
    if (!(t in ["true", "false"])) errors << "${path} musi być boolean (true/false), jest: '${v}'"
}

// =========================
// RECURSIVE WALK
// =========================
def walkGroup
walkGroup = { node, String path ->
    // required fields for group
    def nx = text(node, "nameXML")
    def nm = text(node, "name")
    if (!nx) errors << "${path}: brak nameXML"
    if (!nm) errors << "${path}: brak name"

    // group booleans if present
    asBool("${path}.groupEnabled", text(node, "groupEnabled") ?: "")
    asBool("${path}.groupVisibility", text(node, "groupVisibility") ?: "")
    asBool("${path}.root", text(node, "root") ?: "")

    // multiplicity rules
    def min = asInt("${path}.multiplicityMin", text(node, "multiplicityMin") ?: "")
    def max = asInt("${path}.multiplicityMax", text(node, "multiplicityMax") ?: "")
    if (min != null && max != null && min > max) {
        errors << "${path}: multiplicityMin (${min}) > multiplicityMax (${max})"
    }

    // position
    asInt("${path}.position", text(node, "position") ?: "")

    // data items inside this group
    def itemsContainer = node.dataItem
    itemsContainer?.dataItem?.eachWithIndex { it, idx ->
        def ip = "${path}.dataItem[${idx}]"
        def ix = text(it, "nameXML")
        if (!ix) errors << "${ip}: brak nameXML"

        asBool("${ip}.required", text(it, "required") ?: "")
        asBool("${ip}.readOnlyMode", text(it, "readOnlyMode") ?: "")
        asBool("${ip}.encrypted", text(it, "encrypted") ?: "")
        asBool("${ip}.criteria", text(it, "criteria") ?: "")

        asInt("${ip}.position", text(it, "position") ?: "")

        def minL = asInt("${ip}.minLength", text(it, "minLength") ?: "")
        def maxL = asInt("${ip}.maxLength", text(it, "maxLength") ?: "")
        if (minL != null && maxL != null && minL > maxL) {
            errors << "${ip}: minLength (${minL}) > maxLength (${maxL})"
        }

        def type = text(it, "type")
        if (!type) errors << "${ip}: brak type"
    }

    // child groups
    def kids = node.childDataGroups?.childDataGroups
    if (kids && kids.size() > 0) {
        // detect duplicate nameXML among siblings
        def names = kids.collect { text(it, "nameXML") ?: "" }
        def duplicates = names.findAll { it }.groupBy { it }.findAll { k, v -> v.size() > 1 }.keySet()
        duplicates.each { d ->
            errors << "${path}: duplikat childDataGroups.nameXML = '${d}'"
        }

        kids.eachWithIndex { k, i ->
            def kName = text(k, "nameXML") ?: "child#${i}"
            walkGroup(k, "${path}/${kName}")
        }
    }
}

// =========================
// START
// =========================
if (errors.isEmpty() && root != null) {
    if (!root.dataGroup || root.dataGroup.size() == 0) {
        errors << "Brak root.dataGroup, nie można walidować drzewa"
    } else {
        walkGroup(root.dataGroup, "/dataGroup")
    }
}

// =========================
// OUTPUT
// =========================
if (!errors.isEmpty()) {
    System.err.println("TREE VALIDATION FAILED (${errors.size()}):")
    errors.each { System.err.println(" - ${it}") }
    System.exit(2)
}

println("TREE VALIDATION OK")
