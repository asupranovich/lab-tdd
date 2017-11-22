package itechart.set.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class SetTestHamcrest {

	@Test
	public void testSetHasSize3AndContainsABC() {

		Set<String> set = new HashSet<>();
		set.add("A");
		set.add("B");
		set.add("C");

		assertThat(set, hasSize(3));
		assertThat(set, containsInAnyOrder("A", "B", "C"));
	}
}
