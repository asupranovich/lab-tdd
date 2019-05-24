package itechart.tdd.calc;

import java.util.Arrays;

/**
 *
 * @author a.supranovich
 */
public class Summator {

    public double sum(double... summands) {

        if (summands.length == 0) {
            throw new IllegalArgumentException("No summands!");
        }

        return Arrays.stream(summands).sum();
    }

}
