package edu.kpi.ip71.dovhopoliuk.util.impl;

import edu.kpi.ip71.dovhopoliuk.util.data.Element;
import edu.kpi.ip71.dovhopoliuk.util.data.ElementType;
import org.junit.Test;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

public class DefaultParallelArrayUtilsTest {

    private static final int[] ARRAY = {5, 7, 4, 0, 8};

    private static final Predicate<Integer> predicate = element -> element % 2 == 0;
    private static final int PREDICATE_RESULT = 3;

    private static final List<Element> MIN_MAX_RESULT = List.of(new Element(ElementType.MIN, 3, 0),
            new Element(ElementType.MAX, 4, 8));

    private static final int XOR_RESULT = 14;

    private final DefaultParallelArrayUtils unit = new DefaultParallelArrayUtils();

    @Test
    public void shouldCountByPredicate() {

        assertEquals(PREDICATE_RESULT, unit.countElementsByPredicate(ARRAY, predicate));
    }

    @Test
    public void shouldFindMinMax() {

        final List<Element> result = unit.findMinMax(ARRAY);

        assertEquals(MIN_MAX_RESULT.size(), result.size());
        assertEquals(MIN_MAX_RESULT.get(0), result.get(0));
        assertEquals(MIN_MAX_RESULT.get(1), result.get(1));
    }

    @Test
    public void shouldCalculateCheckSum() {

        assertEquals(XOR_RESULT, unit.calculateCheckSum(ARRAY));
    }
}