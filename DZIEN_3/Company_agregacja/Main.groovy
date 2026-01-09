def e1 = new Employee("Anna","Analityk biznesowy")
def e2 = new Employee("Jacek","Dyrektor techniczny")
def e3 = new Employee("Kinga","Księgowa")
def e4 = new Employee("Karol","Programista Java")

def companyA = new Company("MB Systems")
companyA.addEmployee(e1)
companyA.addEmployee(e2)
companyA.addEmployee(e3)
companyA.addEmployee(e4)

println(companyA.info())

println("____________________")

def companyB = new Company("InfoMax")
companyB.addEmployee(e3)


println(companyB.info())

println("____________________________")
println("Procownik istniej niezależnie od firmy...")

println(e3.info())
