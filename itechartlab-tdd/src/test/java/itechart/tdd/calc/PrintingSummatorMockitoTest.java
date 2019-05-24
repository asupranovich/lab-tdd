package itechart.tdd.calc;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PrintingSummatorMockitoTest {

    @Mock
    private PrintStream output;

    @Mock
    private Summator summator;

    @InjectMocks
    private PrintingSummator printingSummator = new PrintingSummator();

    @Test
    public void whenNoSummandsThenPrintError() {

//		doThrow(new IllegalArgumentException("No summands!")).when(summator).sum();
        when(summator.sum()).thenThrow(new IllegalArgumentException("No summands!"));

        printingSummator.sum();

        verify(summator, times(1)).sum();
        verify(output, only()).println("Error");
    }

    @Test
    public void whenOneSummandsThenPrintEquity() {

        double summand = 5.0;
        when(summator.sum(summand)).thenReturn(summand);

        printingSummator.sum(summand);

        verify(summator, only()).sum(summand);
        verify(output, only()).println("5.0 = 5.0");
    }

    @Test
    public void whenSeveralSummadsThenPrintFormula() {

        double summand1 = 7.0;
        double summand2 = 3.0;
        Mockito.when(summator.sum(summand1, summand2)).thenReturn(10.0);

        printingSummator.sum(summand1, summand2);

        verify(summator).sum(summand1, summand2);
        verify(output).println("7.0 + 3.0 = 10.0");
    }

    @Test
    public void whenNegativeSummandThenWrapItInBrackets() {

        double summand1 = 7.0;
        double summand2 = -3.0;
        when(summator.sum(summand1, summand2)).thenReturn(4.0);

        printingSummator.sum(summand1, summand2);

        verify(summator).sum(summand1, summand2);
        verify(output).println("7.0 + (-3.0) = 4.0");
    }

}
