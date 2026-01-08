/*
masz listę wartości pochodzących z xforms
Zadanie:
napisz closure classify, która 
- przyjmuje jedną wartość
- zwaraca String zgodnie z rgułami:

1. null -> "EMPTY"
2. liczba (NUMBER) -> "NUMBER"
3. String pusty lub whitespaces -> "BLANK_STRING"
4. String niepusty -> "TEXT"
5. inny typ -> "OTHER"

użyj closure do przetworzenia listy inputs

- nie używaj pętli for...

 */"

// Dane wejściowe (np. z formularza / XForms / BPMN)
def inputs = [12, "OK", null, 3.14, "", false, "123"]

// Closure klasyfikująca pojedynczą wartość
def classify = { x ->
    switch (x) {
        case null:
            "EMPTY"
        case Number:
            "NUMBER"
        case String:
            x.trim().isEmpty() ? "BLANK_STRING" : "TEXT"
        default:
            "OTHER"
    }
}

// Przetworzenie listy danych
def result = inputs.collect(classify)

// Prezentacja wyniku
println(result)

// Kontrola poprawności
assert result == [
        "NUMBER",
        "TEXT",
        "EMPTY",
        "NUMBER",
        "BLANK_STRING",
        "OTHER",
        "TEXT"
]

