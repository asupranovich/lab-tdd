package itechart.tdd.staticcalc;

import java.util.Arrays;

public class Summator {

    public static double sum(double... summands) {

        if (summands.length == 0) {
            throw new IllegalArgumentException("No summands!");
        }

        return Arrays.stream(summands).sum();
    }

}
