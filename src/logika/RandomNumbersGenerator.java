package logika;

import java.util.Random;

/**
 * Created by Kamil on 2015-04-20.
 */
public class RandomNumbersGenerator {
    public static Random generator = new Random();

    public static Double generateDouble(Double min, Double max) {
        return min + (max - min) * generator.nextDouble();
    }

    public static Integer generateInteger(Integer min, Integer max) {
        return generator.nextInt(max - min + 1) + min;
    }

    public static boolean generateBoolean() {
        return generator.nextBoolean();
    }
}
