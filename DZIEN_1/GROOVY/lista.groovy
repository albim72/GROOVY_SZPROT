def s = "Groovy is the best language"
def lower = s.toLowerCase()
def n = lower.size()

def evensSquared = [3,6,3,7,32,7,9,0,22,5,21,21,12].findAll{it%2==0}.collect{it*it}

println(lower)
println(n)
println(evensSquared)

def m = [a:1,b:7,c:3,d:0,g:34] //mapa - słownik

def keysGt1 = m.findAll {it.value>1}.collect {it.key}

println(keysGt1)
