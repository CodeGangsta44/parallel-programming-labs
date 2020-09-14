package edu.kpi.ip71.dovhopoliuk;

import edu.kpi.ip71.dovhopoliuk.benchmark.MatrixMultiplicationBenchmark;

public class Main {

    private final static int SIZE = 5000;
    private final static int THREAD_QUANTITY = 6;
    private final static int ITERATIONS = 100;

    public static void main(final String[] args) {

        new MatrixMultiplicationBenchmark(SIZE, THREAD_QUANTITY, ITERATIONS).doBenchmark();
    }
}
