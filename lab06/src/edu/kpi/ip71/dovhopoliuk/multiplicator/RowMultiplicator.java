package edu.kpi.ip71.dovhopoliuk.multiplicator;

import java.util.stream.IntStream;

public class RowMultiplicator {

    public static Double multiply(final Double[] row, final Double[] vector) {

        return IntStream.range(0, row.length)
                .mapToDouble(index -> row[index] * vector[index])
                .sum();
    }
}