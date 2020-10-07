package edu.kpi.ip71.dovhopoliuk.util.impl;

import edu.kpi.ip71.dovhopoliuk.util.ParallelArrayUtils;
import edu.kpi.ip71.dovhopoliuk.util.data.Element;
import edu.kpi.ip71.dovhopoliuk.util.data.ElementType;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class DefaultParallelArrayUtils implements ParallelArrayUtils {
    private static final int INTEGER_ZERO = 0;

    @Override
    public int countElementsByPredicate(final int[] array, final Predicate<Integer> predicate) {

        final AtomicInteger result = new AtomicInteger(INTEGER_ZERO);

        IntStream.of(array)
                .parallel()
                .filter(predicate::test)
                .forEach(element -> processElement(element, result, (curr, old) -> old + 1));

        return result.get();
    }

    @Override
    public List<Element> findMinMax(final int[] array) {

        final AtomicInteger minIndex = new AtomicInteger(INTEGER_ZERO);
        final AtomicInteger maxIndex = new AtomicInteger(INTEGER_ZERO);

        IntStream.range(INTEGER_ZERO, array.length)
                .parallel()
                .forEach(index -> updateMinMax(array, index, minIndex, maxIndex));

        return List.of(new Element(ElementType.MIN, minIndex.get(), array[minIndex.get()]),
                new Element(ElementType.MAX, maxIndex.get(), array[maxIndex.get()]));
    }

    @Override
    public int calculateCheckSum(final int[] array) {

        final AtomicInteger result = new AtomicInteger(INTEGER_ZERO);

        IntStream.of(array)
                .parallel()
                .forEach(element -> processElement(element, result, (curr, old) -> old ^ curr));

        return result.get();
    }

    private void processElement(final int element, final AtomicInteger result, final BiFunction<Integer, Integer, Integer> function) {

        int oldValue;
        int newValue;

        do {
            oldValue = result.get();
            newValue = function.apply(element, oldValue);
        } while (!result.compareAndSet(oldValue, newValue));
    }

    private void updateMinMax(final int[] array, final int currentIndex, final AtomicInteger minIndex, final AtomicInteger maxIndex) {

        updateIndex(array, currentIndex, minIndex, (curr, old) -> curr < old);
        updateIndex(array, currentIndex, maxIndex, (curr, old) -> curr > old);
    }

    private void updateIndex(final int[] array, final int currentIndex, final AtomicInteger result, final BiPredicate<Integer, Integer> predicate) {

        int currentValue = array[currentIndex];

        int oldIndex;
        do {
            oldIndex = result.get();
        } while (predicate.test(currentValue, array[oldIndex]) && !result.compareAndSet(oldIndex, currentIndex));
    }
}
