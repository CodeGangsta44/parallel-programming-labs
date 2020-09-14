package edu.kpi.ip71.dovhopoliuk.multiplicator;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class MatrixMultiplicator {
    private final double[][] matrix;
    private final double[] vector;
    private final int threads;

    public MatrixMultiplicator(final double[][] matrix, final double[] vector, final int threads) {

        this.matrix = matrix;
        this.vector = vector;
        this.threads = threads;
    }

    public double[] multiplySerially() {

        return Arrays.stream(matrix)
                .mapToDouble(doubles -> RowMultiplicator.multiply(doubles, vector))
                .toArray();
    }

    public double[] multiplyParallelly() {

        final int chunkSize = matrix.length / (threads - 1);

        final RunnableRowMultiplicator[] threadArray = IntStream.range(0, threads)
                .mapToObj(index -> getPart(matrix, index, chunkSize))
                .map(doubles -> new RunnableRowMultiplicator(doubles, vector))
                .toArray(RunnableRowMultiplicator[]::new);

        for (int i = 0; i < threads; i++) {
            threadArray[i].start();
        }

        for (int i = 0; i < threads; i++) {
            joinThread(threadArray[i]);
        }

        return Arrays.stream(threadArray)
                .map(RunnableRowMultiplicator::getResult)
                .flatMapToDouble(DoubleStream::of)
                .toArray();
    }

    private double[][] getPart(final double[][] matrix, final int index, final int chunkSize) {

        return Arrays.copyOfRange(matrix,
                index * chunkSize,
                Math.min((index + 1) * chunkSize, matrix.length));
    }

    private void joinThread(final Thread thread) {

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
