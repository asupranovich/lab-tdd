package itechart.tdd.calc;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.io.PrintStream;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EasyMockRunner.class)
public class PrintingSummatorEasyMockTest {

    @Mock(type = MockType.DEFAULT)
    private PrintStream output;

    @Mock(type = MockType.DEFAULT)
    private Summator summator;

    @TestSubject
    private PrintingSummator printingSummator = new PrintingSummator();

    @Test
    public void whenNoSummandsThenPrintError() {

        expect(summator.sum()).andThrow(new IllegalArgumentException("No summands!"));

        output.println("Error");
        expectLastCall();

        replay(summator, output);

        printingSummator.sum();

        verify(summator, output);
    }

    @Test
    public void whenOneSummandsThenPrintEquity() {

        double summand = 5.0;
        expect(summator.sum(summand)).andReturn(summand);

        output.println("5.0 = 5.0");
        expectLastCall();

        replay(summator, output);

        printingSummator.sum(summand);

        verify(summator, output);
    }

    @Test
    public void whenSeveralSummadsThenPrintFormula() {

        double summand1 = 7.0;
        double summand2 = 3.0;
        expect(summator.sum(summand1, summand2)).andReturn(10.0);

        output.println("7.0 + 3.0 = 10.0");
        expectLastCall();

        replay(summator, output);

        printingSummator.sum(summand1, summand2);

        verify(summator, output);

    }

    @Test
    public void whenNegativeSummandThenWrapItInBrackets() {

        double summand1 = 7.0;
        double summand2 = -3.0;
        expect(summator.sum(summand1, summand2)).andReturn(4.0);

        output.println("7.0 + (-3.0) = 4.0");
        expectLastCall();

        replay(summator, output);

        printingSummator.sum(summand1, summand2);

        verify(summator, output);
    }

}
