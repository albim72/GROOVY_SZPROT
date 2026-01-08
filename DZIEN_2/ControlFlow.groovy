def fizzBuzz = {
    n ->
        (1..n).collect{
            if(it%15 == 0) "FizzBuzz"
            else if (it%3==0) "Fizz"
            else if (it%5==0) "Buzz"
            else it
        }
}

println(fizzBuzz(16))
println(fizzBuzz(45))

def typeOf = {
    x ->
        switch (x) {
            case Number: return "NUM"
            case String: return "STR"
            default: return "OTHER"
        }
}

println(typeOf(12.554))
println(typeOf("ok"))
println(typeOf(true))
