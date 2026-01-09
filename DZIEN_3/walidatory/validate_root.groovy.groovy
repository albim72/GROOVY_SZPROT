#!/usr/bin/env groovy
import groovy.xml.XmlSlurper

// =========================
// CLI INPUT
// =========================
File file = (args && args[0]) ? new File(args[0]) : new File("WRP0001.xml")

def errors = []

if (!file.exists()) {
    errors << "Nie widzę pliku: ${file.absolutePath}"
} else if (!file.isFile()) {
    errors << "To nie jest plik: ${file.absolutePath}"
}

// =========================
// LOAD XML
// =========================
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

def parseLong = { String path, String val ->
    if (!val?.trim()) return null
    try { Long.parseLong(val.trim()) }
    catch (Exception e) { errors << "${path} musi być long, jest: '${val}'"; return null }
}

// =========================
// ROOT VALIDATION
// =========================
if (errors.isEmpty() && root != null) {
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
}

// =========================
// OUTPUT
// =========================
if (!errors.isEmpty()) {
    System.err.println("ROOT VALIDATION FAILED (${errors.size()}):")
    errors.each { System.err.println(" - ${it}") }
    System.exit(2)
}

println("ROOT VALIDATION OK")
