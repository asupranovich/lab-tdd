package itechart.tdd.staticcalc;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.PrimitiveIterator.OfDouble;

public class PrintingSummator {

    private PrintStream output;

    public PrintingSummator(PrintStream output) {
        this.output = output;
    }

    public void sum(double... summands) {
        try {
            double result = Summator.sum(summands);
            String formula = getFormula(summands, result);
            output.println(formula);
        } catch (IllegalArgumentException e) {
            output.println("Error");
        }
    }

    private String getFormula(double[] summands, double result) {

        StringBuilder formulaBuilder = new StringBuilder();
        OfDouble iterator = Arrays.stream(summands).iterator();

        while (iterator.hasNext()) {
            double summand = iterator.nextDouble();
            formulaBuilder.append(wrapIfNegative(summand));

            if (iterator.hasNext()) {
                formulaBuilder.append(" + ");
            }
        }

        formulaBuilder.append(" = ");
        formulaBuilder.append(result);

        return formulaBuilder.toString();
    }

    private String wrapIfNegative(double value) {
        String template = value < 0 ? "(%s)" : "%s";
        return String.format(template, value);
    }

}
