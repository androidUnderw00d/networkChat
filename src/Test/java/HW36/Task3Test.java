package HW36;

import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;

class Task3Test {

    @Test
    @DisplayName("Long wait")
    public void longWait() {
        Assertions.assertTimeout(Duration.ofSeconds(1), () -> {
            int[] data = new int[900000];
            data[0] = 1;
            data[1] = 1;
            for (int i = 2; i < data.length; i++) {
                final double sin = Math.sin(i);
                data[i] = sin > 0.5 ? 1 : 4;
            }
            Assertions.assertTrue(Task3.process(data), "Ошибка");
        });
    }

    @Test
    @DisplayName("Contains 1")
    public void contains1() {
        Assertions.assertFalse(Task3.process(new int[]{1,4,4,4,4}), "В массиве есть 1");
        System.out.println("Массив только из 4");
    }

    @Test
    @DisplayName("Contains 4")
    public void contains4() {
        Assertions.assertFalse(Task3.process(new int[]{1,1,1,1,1}), "В массиве есть 4");
        System.out.println("Массив только из 1");
    }
}