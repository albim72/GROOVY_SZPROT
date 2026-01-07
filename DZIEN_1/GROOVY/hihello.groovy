def name = "Anna"

println("Pierwszy skrypt Groovy!")
println("Hello, $name!")

String sayHi(who) {"Hi, $who!"}

println(sayHi("Jack"))

assert sayHi("Leon") == "Hi, Leon!"
