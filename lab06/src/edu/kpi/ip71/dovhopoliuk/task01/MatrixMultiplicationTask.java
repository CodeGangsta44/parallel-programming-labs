package edu.kpi.ip71.dovhopoliuk.task01;

import edu.kpi.ip71.dovhopoliuk.multiplicator.RowMultiplicator;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MatrixMultiplicationTask extends RecursiveTask<Double[]> {

    private static final int ROWS_THRESHOLD = 1;
    private static final int WIDTH_FOR_ITERATION = 2;

    private final Double[][] matrix;
    private final Double[] vector;

    public MatrixMultiplicationTask(final Double[][] matrix, final Double[] vector) {

        this.matrix = matrix;
        this.vector = vector;
    }

    @Override
    protected Double[] compute() {

        if (matrix.length > ROWS_THRESHOLD) {

            return invokeAll(createSubtasks())
                    .stream()
                    .flatMap(task -> Arrays.stream(task.join()))
                    .toArray(Double[]::new);

        } else {

            return Arrays.stream(matrix)
                    .map(row -> RowMultiplicator.multiply(row, vector))
                    .toArray(Double[]::new);
        }
    }

    private List<RecursiveTask<Double[]>> createSubtasks() {

        int chunkSize = matrix.length / WIDTH_FOR_ITERATION + (matrix.length % WIDTH_FOR_ITERATION == 0 ? 0 : 1);

        return IntStream.range(0, WIDTH_FOR_ITERATION)
                .mapToObj(index -> new MatrixMultiplicationTask(getSubMatrix(index, chunkSize), vector))
                .collect(Collectors.toList());
    }

    private Double[][] getSubMatrix(final int index, final int chunkSize) {

        final int lowerBound = index * chunkSize;
        final int upperBound = Math.min(lowerBound + chunkSize, matrix.length);

        return Arrays.copyOfRange(matrix, lowerBound, upperBound);
    }

}
