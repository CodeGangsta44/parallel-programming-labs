package edu.kpi.ip71.dovhopoliuk.multiplicator;

import java.util.Arrays;

public class RunnableRowMultiplicator extends Thread {
    private final double[][] rows;
    private final double[] vector;
    private double[] result;

    public RunnableRowMultiplicator(final double[][] rows, final double[] vector) {
        this.rows = rows;
        this.vector = vector;
    }

    @Override
    public void run() {

        result = Arrays.stream(rows)
                .mapToDouble(row -> RowMultiplicator.multiply(row, vector))
                .toArray();
    }

    public double[] getResult() {
        return result;
    }
}
