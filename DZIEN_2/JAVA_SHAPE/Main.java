public class Main {
    public static void main(String[] args) {
        Shape s1 = new Circle("Koło A",3.74);
        Shape s2 = new Rectangle("Prostokąt D",4.5,6.7);

        System.out.println(s1.info());
        System.out.println(s2.info());
    }
}
