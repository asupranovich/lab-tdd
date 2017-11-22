package itechart.tdd.staticcalc;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.verify;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;

import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Summator.class)
public class PrintingSummatorPowerMockTest {

	@Mock
	private PrintStream output;
	
	private PrintingSummator printingSummator;
	
	@Before
	public void setUp() {
		printingSummator = new PrintingSummator(output);
	}
	
	@Test
	public void whenNoSummandsThenPrintError() {

		mockStatic(Summator.class);
		expect(Summator.sum()).andThrow(new IllegalArgumentException("No summands!"));
		
		output.println("Error");
		expectLastCall();

		replayAll();

		printingSummator.sum();

		verify(output);
	}

	@Test
	public void whenOneSummandsThenPrintEquity() {

		double summand = 5.0;
		mockStatic(Summator.class);
		expect(Summator.sum(summand)).andReturn(summand);

		output.println("5.0 = 5.0");
		expectLastCall();

		replayAll();

		printingSummator.sum(summand);

		verify(output);
	}

	@Test
	public void whenSeveralSummadsThenPrintFormula() {

		double summand1 = 7.0;
		double summand2 = 3.0;
		mockStatic(Summator.class);
		expect(Summator.sum(summand1, summand2)).andReturn(10.0);

		output.println("7.0 + 3.0 = 10.0");
		expectLastCall();

		replayAll();

		printingSummator.sum(summand1, summand2);

		verify(output);
	}

	@Test
	public void whenNegativeSummandThenWrapItInBrackets() {

		double summand1 = 7.0;
		double summand2 = -3.0;
		mockStatic(Summator.class);
		expect(Summator.sum(summand1, summand2)).andReturn(4.0);

		output.println("7.0 + (-3.0) = 4.0");
		expectLastCall();

		replayAll();

		printingSummator.sum(summand1, summand2);

		verify(output);
	}

}
