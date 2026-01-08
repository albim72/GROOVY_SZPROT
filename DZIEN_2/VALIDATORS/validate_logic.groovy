#!/usr/bin/env groovy
import groovy.xml.XmlSlurper

File file = args ? new File(args[0]) : new File("WRP0001.xml")
assert file.exists(): "Nie widzę pliku: ${file.absolutePath}"

def root = new XmlSlurper(false, false).parse(file)

def errors = []

def bool = { String v -> (v?.trim() == 'true') }
def nonEmpty = { String v -> v?.trim() }

boolean isActive = bool(root.isActive.text())
boolean simple = bool(root.simple.text())
boolean withDb = bool(root.withDatabaseStructure.text())
boolean bothForms = bool(root.bothForms.text())

long version
try { version = Long.parseLong(root.currentVersion.text().trim()) }
catch (Exception e) { version = -1; errors << "currentVersion nie jest liczbą" }

def desc = root.description.text()

// 1) Aktywny model powinien mieć opis
if (isActive && !nonEmpty(desc)) {
    errors << "isActive=true => description nie może być puste"
}

// 2) Wersja musi mieć sens
if (version <= 0) {
    errors << "currentVersion musi być > 0"
}

// 3) Flagi simple vs withDatabaseStructure
if (simple && withDb) {
    errors << "simple=true nie może współistnieć z withDatabaseStructure=true"
}

// 4) bothForms=true: minimalny warunek
def dg = root.dataGroup
def dgNameXml = dg?.nameXML?.text()?.trim()
if (bothForms && !dgNameXml) {
    errors << "bothForms=true => root.dataGroup.nameXML powinno istnieć"
}

if (!errors.isEmpty()) {
    System.err.println("LOGIC VALIDATION FAILED (${errors.size()}):")
    errors.each { System.err.println(" - ${it}") }
    System.exit(2)
}

println("LOGIC VALIDATION OK")
