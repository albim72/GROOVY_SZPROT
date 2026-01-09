def xmlPath = args?.lenght ? args[0]: "customers.xml"

println("===== CUSTOMER XML VALIDATOR ======")
println("Input file: ${xmlPath}")

println("___________________________________")

def result = CustomerXmlValidator.validateFile(xmlPath)

if (result.ok) {
    println("VALIDATION PASSED!")
    println("File is correct!")
}
else {
    println("VALIDATION FAILED")
    println("Errors:\n" +
            result.errors.each {err->
                println(" - ${err}")
            })
    System.exit(1)
}

println("_________________________________________")
println("END")
