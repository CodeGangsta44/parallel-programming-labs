package edu.kpi.ip71.dovhopoliuk.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListUtils {

    public static List<Integer> intersect(final List<Integer> list1, final List<Integer> list2) {

        return Stream.of(list1.stream().filter(list2::contains), list2.stream().filter(list1::contains))
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }

    public static List<Integer> subtraction(final List<Integer> list1, final List<Integer> list2) {

        return list1.stream()
                .filter(value -> !list2.contains(value))
                .collect(Collectors.toList());
    }

    public static List<Integer> sort(final List<Integer> input) {

        return input.stream()
                .sorted(Comparator.comparingInt(i -> i))
                .collect(Collectors.toList());
    }


    public static List<Integer> multiply(final List<Integer> input, final int multiplier) {

        return input.stream()
                .map(value -> value * multiplier)
                .collect(Collectors.toList());
    }

    public static List<Integer> filterEven(final List<Integer> input) {

        return input.stream()
                .filter(value -> value % 2 == 0)
                .collect(Collectors.toList());
    }

    public static List<Integer> filterByRangeOfMax(final List<Integer> input, final double lowerBoundMultiplier, final double upperBoundMultiplier) {

        final OptionalInt maxValueOptional = input.stream()
                .mapToInt(Integer::intValue)
                .max();

        List<Integer> result;

        if (maxValueOptional.isPresent()) {

            final int maxValue = maxValueOptional.getAsInt();
            final long lowerBound = Math.round(lowerBoundMultiplier * maxValue);
            final long upperBound = Math.round(upperBoundMultiplier * maxValue);

            result = input.stream()
                    .filter(value -> value >= lowerBound && value <= upperBound)
                    .collect(Collectors.toList());
        } else {

            result = Collections.emptyList();
        }

        return result;
    }
}
