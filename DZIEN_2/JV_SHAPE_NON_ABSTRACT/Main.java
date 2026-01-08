public class Main {
    public static void main(String[] args) {
        Circle s1 = new Circle("Koło A",3.74);
        Rectangle s2 = new Rectangle("Prostokąt D",4.5,6.7);
        Triangle s3 = new Triangle("Trójkąt Y",6.7,8);


        System.out.println(s1.info());
        System.out.println(s2.info());
        System.out.println(s3.info());
    }
}
