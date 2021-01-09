package HW36;

import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;

class Task2Test {

    @DisplayName("Parm Test")
    @ParameterizedTest
    @MethodSource("data")
    public void paramTestArray(int[] arrInit, int[] arrResult, String message) {
        Assertions.assertArrayEquals(arrResult, Task2.process(arrInit));
    }

    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}, new int[]{1, 7}, "Case 1"},
                {new int[]{7, 3, 5, 8, 2, 1}, new int[]{1, 3}, "Case 2"},
                {new int[]{4, 2, 9}, new int[]{2, 9}, "Case 3"}
        });
    }

    @Test
    @DisplayName("Exception Test")
    public void testException() {
        Task2.process(new int[]{1, 2, 3});
    }

}