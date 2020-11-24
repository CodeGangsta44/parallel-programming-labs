package edu.kpi.ip71.dovhopoliuk.task01.example;

import edu.kpi.ip71.dovhopoliuk.example.Example;
import edu.kpi.ip71.dovhopoliuk.task01.MatrixMultiplicationTask;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MatrixMultiplicationExample implements Example {

    private final int SIZE = 100;
    private final Random RANDOM = new Random();
    private final int INTEGER_ZERO = 0;

    @Override
    public void executeExample() {

        final Double[][] matrix = generateMatrix();
        final Double[] vector = generateRow();

        final ForkJoinPool pool = ForkJoinPool.commonPool();

        final RecursiveTask<Double[]> task = new MatrixMultiplicationTask(matrix, vector);

        final Double[] result = pool.invoke(task);

        System.out.println(Arrays.stream(result).collect(Collectors.toList()));
    }

    private Double[][] generateMatrix() {

        return IntStream.range(INTEGER_ZERO, SIZE)
                .mapToObj(index -> generateRow())
                .toArray(Double[][]::new);
    }

    private Double[] generateRow() {

        return IntStream.range(INTEGER_ZERO, SIZE)
                .mapToObj(index -> RANDOM.nextGaussian())
                .toArray(Double[]::new);
    }
}
