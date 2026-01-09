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

// helpers
def txt = { node, String name ->
    try { node?."${name}"?.text()?.trim() } catch (Exception ignore) { null }
}

def boolVal = { String v -> (v?.trim() == "true") }

def parseLong = { String path, String val ->
    if (!val?.trim()) return null
    try { Long.parseLong(val.trim()) }
    catch (Exception e) { errors << "${path} musi być long, jest: '${val}'"; return null }
}

// =========================
// LOGIC RULES
// =========================
if (errors.isEmpty() && root != null) {
    boolean isActive = boolVal(txt(root, "isActive"))
    boolean simple = boolVal(txt(root, "simple"))
    boolean withDb = boolVal(txt(root, "withDatabaseStructure"))
    boolean bothForms = boolVal(txt(root, "bothForms"))

    def desc = txt(root, "description")
    def version = parseLong("currentVersion", txt(root, "currentVersion"))

    if (isActive && !desc) {
        errors << "isActive=true => description nie może być puste"
    }

    if (version == null || version <= 0) {
        errors << "currentVersion musi być > 0"
    }

    if (simple && withDb) {
        errors << "Niespójność: simple=true nie może współistnieć z withDatabaseStructure=true"
    }

    def dgNameXml = root?.dataGroup?.nameXML?.text()?.trim()
    if (bothForms && !dgNameXml) {
        errors << "bothForms=true => root.dataGroup.nameXML powinno istnieć"
    }
}

// =========================
// OUTPUT
// =========================
if (!errors.isEmpty()) {
    System.err.println("❌ LOGIC VALIDATION FAILED (${errors.size()}):")
    errors.each { System.err.println(" - ${it}") }
    System.exit(2)
}

println("✅ LOGIC VALIDATION OK")
