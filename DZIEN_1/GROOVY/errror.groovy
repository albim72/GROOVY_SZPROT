class InvalidEmailException extends RuntimeException {
    InvalidEmailException(String msg) {super(msg)}
}

def validateEmail = {email ->
    if(!(email instanceof String) || !email.contains("@"))
        throw new InvalidEmailException("Niewłaściwy format email...")
    true
}

def caught = null

try {
    validateEmail("fsd@fdf.pl")
}
catch (InvalidEmailException e) {
    caught = e.message
    println(caught)
}
