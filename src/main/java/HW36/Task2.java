package HW36;

import java.util.Arrays;

public class Task2 {
    public static void main(String[] args) {
        final int[] result = process(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7});
        System.out.println(Arrays.toString(result));
    }

    public static int[] process(int[] arrInit) {
        if (arrInit.length == 0) {
            System.out.println("Массив пустой");
            return arrInit;
        }

        for (int i = arrInit.length - 1; i >= 0; i--) {

            if (arrInit[i] == 4) {
                int resultLength = arrInit.length - (i + 1);
                int[] arrResult = new int[resultLength];
                System.arraycopy(arrInit, (i + 1), arrResult, 0, resultLength);
                return arrResult;
            }
        }
        throw new RuntimeException("В массиве нет ни одной 4-ки после которой были бы числа");
    }
}