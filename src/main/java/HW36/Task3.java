package HW36;

public class Task3 {
    public static void main(String[] args) {
        final boolean result = process(new int[]{1, 1, 1, 1, 1});
        System.out.println(result);
    }

    public static boolean process(int[] arrInt) {
        boolean one = false;
        boolean four = false;
        for (int i: arrInt) {
            if(i != 1 && i!=4) throw new RuntimeException("invalid value");
            if(i == 1) one = true;
            if(i == 4) four = true;
        }
        return one && four;
    }
}
