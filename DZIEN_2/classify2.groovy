#!/usr/bin/env groovy

/**
 * Final: klasyfikator odporny na typy (String/Number/null).
 * Nigdy nie woła trim() na liczbie, zawsze normalizuje przez toString().
 */

// Dane testowe (możesz usunąć w produkcji)
def inputs = [
        "  abc123  ",
        123,
        "   ",
        null,
        -7,
        "XYZ",
        3.14,
        "  other "
]

// 1) Normalizacja: wszystko -> String? -> trim -> lower
def normalize = { Object x ->
    x == null ? null : x.toString().trim().toLowerCase()
}

// 2) Klasyfikacja
def classify = { Object x ->
    def key = normalize(x)

    if (!key) return "EMPTY"        // null albo pusty po trim

    // Przykładowa logika: dopasowania regex / warunki
    if (key ==~ /-?\d+(\.\d+)?/)    return "NUMBER"   // liczby int/float w tekście lub z Number.toString()
    if (key ==~ /[a-z]+\d+/)        return "ALNUM"
    if (key ==~ /[a-z]+/)           return "TEXT"

    return "OTHER"
}

// 3) Przetworzenie listy danych
def result = inputs.collect(classify)

// 4) Prezentacja wyniku
println "inputs : ${inputs}"
println "result : ${result}"
