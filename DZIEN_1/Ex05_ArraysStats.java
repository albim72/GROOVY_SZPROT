import java.util.Arrays;

public class Ex05_ArraysStats {
    // Cel: tablice, min/max/avg, pętle for-each
    static int min(int[] a) {
        int m = a[0];
        for (int x : a) m = Math.min(m, x);
        return m;
    }
    static int max(int[] a) {
        int m = a[0];
        for (int x : a) m = Math.max(m, x);
        return m;
    }
    static double avg(int[] a) {
        long sum = 0;
        for (int x : a) sum += x;
        return a.length == 0 ? 0 : (double) sum / a.length;
    }
    public static void main(String[] args) {
        int[] data = {5, 2, 9, 1, 5, 6};
        System.out.println("Data: " + Arrays.toString(data));
        System.out.println("min=" + min(data) + ", max=" + max(data) + ", avg=" + avg(data));
    }
}
