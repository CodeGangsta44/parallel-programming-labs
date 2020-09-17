package edu.kpi.ip71.dovhopoliuk;

import edu.kpi.ip71.dovhopoliuk.benchmark.MatrixMultiplicationBenchmark;

public class Main {

    public static void main(final String[] args) {

        System.out.println("IP-71, Roman Dovhopoliuk, Variant #9\n");

        new MatrixMultiplicationBenchmark(100, 3, 1000).doBenchmark();
        new MatrixMultiplicationBenchmark(100, 6, 1000).doBenchmark();
        new MatrixMultiplicationBenchmark(100, 12, 1000).doBenchmark();

        new MatrixMultiplicationBenchmark(1000, 3, 1000).doBenchmark();
        new MatrixMultiplicationBenchmark(1000, 6, 1000).doBenchmark();
        new MatrixMultiplicationBenchmark(1000, 12, 1000).doBenchmark();


        new MatrixMultiplicationBenchmark(5000, 3, 200).doBenchmark();
        new MatrixMultiplicationBenchmark(5000, 6, 200).doBenchmark();
        new MatrixMultiplicationBenchmark(5000, 12, 200).doBenchmark();
    }
}
