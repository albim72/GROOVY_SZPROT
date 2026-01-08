def shapes = [
        new Cicrle("Circle A",8.45),
        new Rectangle("Rectangle B",9.3,6.2),
        new Rectangle("Rectangle U",1.3,3.2)
]

shapes.each {println(it.info())}