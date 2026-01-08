#!/usr/bin/env groovy
import groovy.xml.XmlSlurper

File file = args ? new File(args[0]) : new File("WRP0001.xml")
assert file.exists(): "Nie widzę pliku: ${file.absolutePath}"

def root = new XmlSlurper(false, false).parse(file)
assert root.name() == 'japisDataModel' : "Zły root: ${root.name()} (oczekiwano japisDataModel)"

def errors = []

// --- helpers
def requiredText = { String path, String val ->
    if (!val?.trim()) errors << "Wymagane pole puste: ${path}"
}

def parseBool = { String path, String val ->
    if (!(val in ['true','false'])) errors << "Pole ${path} musi być boolean (true/false), jest: '${val}'"
}

def parseLong = { String path, String val ->
    try { Long.parseLong(val) } catch (Exception e) { errors << "Pole ${path} musi być liczbą całkowitą, jest: '${val}'" }
}

// --- required fields
requiredText('code', root.code.text())
requiredText('name', root.name.text())
requiredText('currentVersion', root.currentVersion.text())

// --- type checks
parseBool('bothForms', root.bothForms.text())
parseBool('isActive', root.isActive.text())
parseBool('simple', root.simple.text())
parseBool('withDatabaseStructure', root.withDatabaseStructure.text())
parseLong('currentVersion', root.currentVersion.text())

// --- sanity checks
def code = root.code.text().trim()
def name = root.name.text().trim()
if (code && name && code != name) {
    errors << "Niespójność: code='${code}' != name='${name}'"
}

// dataGroup must exist
if (!root.dataGroup || root.dataGroup.size() == 0) {
    errors << "Brak węzła dataGroup w root"
}

if (!errors.isEmpty()) {
    System.err.println("ROOT VALIDATION FAILED (${errors.size()}):")
    errors.each { System.err.println(" - ${it}") }
    System.exit(2)
}

println("ROOT VALIDATION OK")
