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


def inputs = [12,"OK",null,3.14,false,"123"]
