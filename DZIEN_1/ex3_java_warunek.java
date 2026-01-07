public class Main {

    static boolean isLeap(int y){
        return (y%400==0) || (y%4==0 && y%100!=0);
    }

    static String grade(int points){
        if (points >= 90) return "A";
        if (points >= 80) return "B";
        if (points >= 70) return "C";
        if (points >= 60) return "D";
        return "F";

    }
    public static void main(String[] args) {
        System.out.println("2000 leap? " + isLeap(2000));
        System.out.println("1900 leap? " + isLeap(1900));
        System.out.println("2026 leap? " + isLeap(2026));

        System.out.println("85 --> " + grade(85));
        System.out.println("71 --> " + grade(71));
        System.out.println("8 --> " + grade(8));
    }
}
