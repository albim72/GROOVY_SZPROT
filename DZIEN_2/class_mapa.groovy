#!/usr/bin/env groovy

/**
 * FINAL v2
 * - odporna na typy (String / Number / null)
 * - oparta o MAPĘ reguł (czytelna, rozszerzalna)
 * - zero trim() na liczbach
 */

// ===============================
// DANE WEJŚCIOWE (demo)
// ===============================
def inputs = [
        "  abc123  ",
        123,
        "   ",
        null,
        -7,
        "XYZ",
        3.14,
        "  other ",
        "12-34",
        "PESEL",
        true
]

// ===============================
// NORMALIZACJA
// ===============================
def normalize = { Object x ->
    x == null ? null : x.toString().trim().toLowerCase()
}

// ===============================
// MAPA REGUŁ KLASYFIKACJI
// Kolejność MA znaczenie
// ===============================
Map<String, Closure<Boolean>> rules = [

        EMPTY : { v -> v == null || v == "" },

        NUMBER : { v ->
            v ==~ /-?\d+(\.\d+)?/
        },

        ALNUM : { v ->
            v ==~ /[a-z]+\d+/
        },

        TEXT : { v ->
            v ==~ /[a-z]+/
        },

        OTHER : { v -> true }   // fallback – MUSI być na końcu
]

// ===============================
// KLASYFIKATOR
// ===============================
def classify = { Object x ->
    def value = normalize(x)

    rules.find { label, rule ->
        rule(value)
    }?.key
}

// ===============================
// PRZETWARZANIE LISTY
// ===============================
def result = inputs.collect { v ->
    [
            input : v,
            class : classify(v)
    ]
}

// ===============================
// PREZENTACJA WYNIKU
// ===============================
println "RESULT:"
result.each {
    println String.format("  %-10s -> %s", it.input, it.class)
}
