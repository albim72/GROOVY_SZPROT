def div = {
    a,b ->
        if (b==0) throw new IllegalArgumentException("b nie może być zerem!")
        a/b
}

def errMsg = null

try {
    t = div(10,0)
    println(t)
}
catch (IllegalArgumentException e){
    errMsg = e.message
    println(errMsg)
}
finally {
    println("wykonano!")
}

assert errMsg == "b nie może być zerem!"
