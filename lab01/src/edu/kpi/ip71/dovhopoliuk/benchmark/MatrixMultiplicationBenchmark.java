package edu.kpi.ip71.dovhopoliuk.benchmark;

import edu.kpi.ip71.dovhopoliuk.multiplicator.MatrixMultiplicator;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class MatrixMultiplicationBenchmark {

    private final int size;
    private final int threadQuantity;
    private final int iterations;
    private final Random random = new Random();

    public MatrixMultiplicationBenchmark(final int size, final int threadQuantity, final int iterations) {
        this.size = size;
        this.threadQuantity = threadQuantity;
        this.iterations = iterations;
    }

    public void doBenchmark() {

        System.out.println("IP-71, Roman Dovhopoliuk, Variant #9\n");
        System.out.println("Starting benchmark with following parameters:");
        System.out.println("Size of matrix and vector: " + size);
        System.out.println("Thread quantity: " + threadQuantity);
        System.out.println("Iterations: " + iterations + '\n');

        System.out.println("Generating matrix and vector...");

        final double[][] matrix = generateMatrix();
        final double[] vector = generateRow();

        System.out.println("Matrix and vector generated \n");


        final MatrixMultiplicator multiplicator = new MatrixMultiplicator(matrix, vector, threadQuantity);

        final long serialDuration = doRun(multiplicator::multiplySerially, "serial");

        System.out.println();

        final long parallelDuration = doRun(multiplicator::multiplyParallelly, "parallel");

        final double accelerationCoefficient = (double) serialDuration / parallelDuration;
        final double efficiencyCoefficient = accelerationCoefficient / threadQuantity;

        System.out.println();

        System.out.println("Acceleration coefficient: " + accelerationCoefficient);
        System.out.println("Efficiency coefficient: " + efficiencyCoefficient);
    }

    private long doRun(final Supplier<double[]> method, final String methodName) {

        System.out.println("Started " + methodName + " run:");

        final long startTime = System.nanoTime();

        for (int i = 0; i < iterations; i++) {
            method.get();
        }

        final long duration = (System.nanoTime() - startTime) / iterations;

        System.out.println("Average execution time: " + duration + "ns");

        return duration;
    }

    private double[][] generateMatrix() {

        return IntStream.range(0, size)
                .mapToObj(index -> generateRow())
                .toArray(double[][]::new);
    }

    private double[] generateRow() {

        return IntStream.range(0, size)
                .mapToDouble(index -> random.nextGaussian())
                .toArray();
    }
}
