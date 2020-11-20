package edu.kpi.ip71.dovhopoliuk.utils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenerationUtils {

    private static final int INTEGER_ZERO = 0;
    private static final Random RANDOM = new Random();

    public static List<Integer> generate(final int quantityOfElementsToGenerate, final int bound) {

        return IntStream.range(INTEGER_ZERO, quantityOfElementsToGenerate)
                .mapToObj(value -> RANDOM.nextInt(bound))
                .collect(Collectors.toList());
    }
}
