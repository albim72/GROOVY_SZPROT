def maybe = null
def sizeSafe = maybe?.size()

println(sizeSafe)

a = 78
println(a)

def l1 = [1,2,3]; def l2=[1,2,3]; def l3 = l1
def eq = (l1==l2)

println(eq)

def eq3 = (l2==l3)

println(eq3)

def same = (l2.is(l3))
println(same)

def cmp = 50<=>20
println(cmp)

def rx = ("abcgfg56435" =~ /\d+/).find()
println(rx)





