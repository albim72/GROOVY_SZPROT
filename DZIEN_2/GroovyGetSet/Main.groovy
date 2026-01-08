def ps = new OsobaS()
ps.age = 40
println(ps.getAge())

println("_____________________")
def np = new OsobaC()
np.name = "Karol"
np.age = 32

println("${np.name} -> ${np.age}")

println(OsobaC.methods*.name.sort().unique())
println(OsobaC.metaClass.hasProperty(np,"age"))

np.setAge(67)
println("${np.name} -> ${np.age}")

