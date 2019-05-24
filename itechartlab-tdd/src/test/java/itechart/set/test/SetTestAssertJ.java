package itechart.set.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class SetTestAssertJ {

    @Test
    public void testSetHasSize3AndContainsABC() {

        Set<String> set = new HashSet<>();
        set.add("A");
        set.add("B");
        set.add("C");

        assertThat(set).hasSize(3).containsOnly("A", "B", "C");
    }

}
