#!/usr/bin/env groovy
import groovy.xml.XmlSlurper

/*
Standalone XML validator (no Camunda)

Usage:
  groovy validate_japis_standalone.groovy --file path/to.xml
  groovy validate_japis_standalone.groovy --text "<japisDataModel>...</japisDataModel>"

Exit codes:
  0 - OK
  2 - Validation failed (business/structure errors)
  3 - Technical error (file missing / parse fail)
*/

// =========================
// CLI PARSING
// =========================
def argMap = [:]
for (int i = 0; i < args.length; i++) {
    def a = args[i]
    if (a == "--file" && i + 1 < args.length) argMap.file = args[++i]
    else if (a == "--text" && i + 1 < args.length) argMap.text = args[++i]
    else if (a in ["-h", "--help"]) argMap.help = true
}

if (argMap.help || (!argMap.file && !argMap.text)) {
    println """
Standalone Japis XML validator

Usage:
  groovy validate_japis_standalone.groovy --file path/to.xml
  groovy validate_japis_standalone.groovy --text "<japisDataModel>...</japisDataModel>"

Exit codes:
  0 OK
  2 Validation failed
  3 Technical error
"""
    System.exit(0)
}

// =========================
// STATE
// =========================
def errors = []
def root = null

// =========================
// LOAD XML
// =========================
try {
    def slurper = new XmlSlurper(false, false)

    if (argMap.text?.trim()) {
        root = slurper.parseText(argMap.text)
    } else {
        def f = new File(argMap.file)
        if (!f.exists() || !f.isFile()) {
            System.err.println("TECH ERROR: Nie widzę pliku: ${f.absolutePath}")
            System.exit(3)
        }
        root = slurper.parse(f)
    }
} catch (Exception e) {
    System.err.println("TECH ERROR: Nie udało się sparsować XML: ${e.class.simpleName}: ${e.message}")
    System.exit(3)
}

// =========================
// HELPERS
// =========================
def txt = { node, String name ->
    try { node?."${name}"?.text()?.trim() } catch (Exception ignore) { null }
}

def requiredText = { String path, String val ->
    if (!val?.trim()) errors << "Wymagane pole puste: ${path}"
}

def parseBool = { String path, String val ->
    if (!val?.trim()) return
    def v = val.trim()
    if (!(v in ["true", "false"])) errors << "${path} musi być boolean (true/false), jest: '${val}'"
}

def parseInt = { String path, String val ->
    if (!val?.trim()) return null
    try { Integer.parseInt(val.trim()) }
    catch (Exception e) { errors << "${path} musi być int, jest: '${val}'"; return null }
}

def parseLong = { String path, String val ->
    if (!val?.trim()) return null
    try { Long.parseLong(val.trim()) }
    catch (Exception e) { errors << "${path} musi być long, jest: '${val}'"; return null }
}

def boolVal = { String v -> (v?.trim() == "true") }

// =========================
// ROOT VALIDATION
// =========================
def rootName = root.name()
if (rootName != "japisDataModel") {
    errors << "Zły root: ${rootName} (oczekiwano japisDataModel)"
}

requiredText("code", txt(root, "code"))
requiredText("name", txt(root, "name"))
requiredText("currentVersion", txt(root, "currentVersion"))

parseBool("bothForms", txt(root, "bothForms"))
parseBool("isActive", txt(root, "isActive"))
parseBool("simple", txt(root, "simple"))
parseBool("withDatabaseStructure", txt(root, "withDatabaseStructure"))

parseLong("currentVersion", txt(root, "currentVersion"))

if (!root.dataGroup || root.dataGroup.size() == 0) {
    errors << "Brak węzła dataGroup w root"
}

// =========================
// LOGIC VALIDATION
// =========================
if (errors.isEmpty()) {
    boolean isActive  = boolVal(txt(root, "isActive"))
    boolean simple    = boolVal(txt(root, "simple"))
    boolean withDb    = boolVal(txt(root, "withDatabaseStructure"))
    boolean bothForms = boolVal(txt(root, "bothForms"))

    def desc = txt(root, "description")
    def version = parseLong("currentVersion", txt(root, "currentVersion"))

    if (isActive && !desc) errors << "isActive=true => description nie może być puste"
    if (version == null || version <= 0) errors << "currentVersion musi być > 0"
    if (simple && withDb) errors << "Niespójność: simple=true nie może współistnieć z withDatabaseStructure=true"

    def dgNameXml = root?.dataGroup?.nameXML?.text()?.trim()
    if (bothForms && !dgNameXml) errors << "bothForms=true => root.dataGroup.nameXML powinno istnieć"
}

// =========================
// TREE VALIDATION (recursive)
// =========================
if (errors.isEmpty()) {

    def walkGroup
    walkGroup = { node, String path ->

        def nx = txt(node, "nameXML")
        def nm = txt(node, "name")
        if (!nx) errors << "${path}: brak nameXML"
        if (!nm) errors << "${path}: brak name"

        parseBool("${path}.groupEnabled", txt(node, "groupEnabled") ?: "")
        parseBool("${path}.groupVisibility", txt(node, "groupVisibility") ?: "")
        parseBool("${path}.root", txt(node, "root") ?: "")

        def min = parseInt("${path}.multiplicityMin", txt(node, "multiplicityMin") ?: "")
        def max = parseInt("${path}.multiplicityMax", txt(node, "multiplicityMax") ?: "")
        if (min != null && max != null && min > max) {
            errors << "${path}: multiplicityMin (${min}) > multiplicityMax (${max})"
        }

        parseInt("${path}.position", txt(node, "position") ?: "")

        def itemsContainer = node.dataItem
        itemsContainer?.dataItem?.eachWithIndex { it, idx ->
            def ip = "${path}.dataItem[${idx}]"

            if (!txt(it, "nameXML")) errors << "${ip}: brak nameXML"

            parseBool("${ip}.required", txt(it, "required") ?: "")
            parseBool("${ip}.readOnlyMode", txt(it, "readOnlyMode") ?: "")
            parseBool("${ip}.encrypted", txt(it, "encrypted") ?: "")
            parseBool("${ip}.criteria", txt(it, "criteria") ?: "")

            parseInt("${ip}.position", txt(it, "position") ?: "")

            def minL = parseInt("${ip}.minLength", txt(it, "minLength") ?: "")
            def maxL = parseInt("${ip}.maxLength", txt(it, "maxLength") ?: "")
            if (minL != null && maxL != null && minL > maxL) {
                errors << "${ip}: minLength (${minL}) > maxLength (${maxL})"
            }

            if (!txt(it, "type")) errors << "${ip}: brak type"
        }

        def kids = node.childDataGroups?.childDataGroups
        if (kids && kids.size() > 0) {
            def names = kids.collect { txt(it, "nameXML") ?: "" }
            def duplicates = names.findAll { it }
                                 .groupBy { it }
                                 .findAll { k, v -> v.size() > 1 }
                                 .keySet()
            duplicates.each { d ->
                errors << "${path}: duplikat childDataGroups.nameXML = '${d}'"
            }

            kids.eachWithIndex { k, i ->
                def kName = txt(k, "nameXML") ?: "child#${i}"
                walkGroup(k, "${path}/${kName}")
            }
        }
    }

    if (!root.dataGroup || root.dataGroup.size() == 0) {
        errors << "Brak root.dataGroup, nie można walidować drzewa"
    } else {
        walkGroup(root.dataGroup, "/dataGroup")
    }
}

// =========================
// OUTPUT
// =========================
if (errors.isEmpty()) {
    println "VALIDATION OK"
    System.exit(0)
} else {
    System.err.println "VALIDATION FAILED (${errors.size()}):"
    errors.each { System.err.println " - ${it}" }
    System.exit(2)
}
