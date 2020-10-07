package edu.kpi.ip71.dovhopoliuk.util;

import edu.kpi.ip71.dovhopoliuk.util.data.Element;

import java.util.List;
import java.util.function.Predicate;

public interface ParallelArrayUtils {

    int countElementsByPredicate(final int[] array, final Predicate<Integer> predicate);

    List<Element> findMinMax(final int[] array);

    int calculateCheckSum(final int[] array);
}
