def inc = {it+1}
def res1 = [1,2,3,4].collect(inc)

println(res1)
println(inc(56))

def absRef = Math.&abs
def res2 = [-2,-6,-34,-15].collect(absRef)

println(res2)

def applyTwice = {x,f -> f(f(x))}
def res3 = applyTwice(3,inc)

println(res3)
