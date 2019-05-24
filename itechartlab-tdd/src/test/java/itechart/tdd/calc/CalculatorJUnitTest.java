package itechart.tdd.calc;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author a.supranovich
 */
public class CalculatorJUnitTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Summator summator;
    private static long time;

    @BeforeClass
    public static void logTestsStart() {
        System.out.println("Running CalculatorJUnitTest class tests...");
        time = System.currentTimeMillis();
    }

    @Before
    public void setUp() {
        summator = new Summator();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNoSummandsThenThrowException() {
        summator.sum();
    }

    @Test
    public void whenNoSummandsThenThrowException2() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("No summands!");
        summator.sum();
    }

    @Test
    public void whenOneSummandThenReturnIt() {
        double summand = 12.37;
        double result = summator.sum(summand);
        Assert.assertEquals(summand, result, 0);
    }

    @Test
    public void whenSeveralSummandsThenReturnSumAll() {
        double summand1 = 10.01;
        double summand2 = -5.01;
        double summand3 = 0.01;
        double result = summator.sum(summand1, summand2, summand3);
        Assert.assertEquals(5.01, result, 0);
    }

    @AfterClass
    public static void logTestsEnd() {
        long diff = System.currentTimeMillis() - time;
        System.out.println(String.format("CalculatorJUnitTest class tests took %d ms to run.", diff));
    }

}
