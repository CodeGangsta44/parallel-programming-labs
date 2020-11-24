package edu.kpi.ip71.dovhopoliuk.process.impl;

import edu.kpi.ip71.dovhopoliuk.multiplicator.RowMultiplicator;
import edu.kpi.ip71.dovhopoliuk.process.Process;
import mpi.MPI;

import java.util.Arrays;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Common.INTEGER_ZERO;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Settings.MAIN_RANK;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Settings.QUANTITY_OF_WORKERS;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Settings.SIZE;

public class WorkerProcess implements Process {

    @Override
    public void execute() {

        final double[][] matrix = receiveMatrix();
        final double[] vector = receiveVector();

        final double[] results = Arrays.stream(matrix)
                .mapToDouble(row -> RowMultiplicator.multiply(row, vector))
                .toArray();

        sendResults(results);
    }

    private double[][] receiveMatrix() {

        double[][] sendBuffer = new double[INTEGER_ZERO][INTEGER_ZERO];

        double[][] matrix = new double[SIZE / QUANTITY_OF_WORKERS][SIZE];

        int[] sendCounts = new int[INTEGER_ZERO];
        int[] displs = new int[INTEGER_ZERO];

        MPI.COMM_WORLD.Scatterv(sendBuffer, INTEGER_ZERO, sendCounts, displs, MPI.OBJECT, matrix, INTEGER_ZERO, SIZE / QUANTITY_OF_WORKERS, MPI.OBJECT, MAIN_RANK);

        return matrix;
    }

    private double[] receiveVector() {

        double[] vector = new double[SIZE];

        MPI.COMM_WORLD.Bcast(vector, INTEGER_ZERO, SIZE, MPI.DOUBLE, MAIN_RANK);

        return vector;
    }

    private void sendResults(final double[] results) {

        double[] receiveBuffer = new double[INTEGER_ZERO];

        int[] receiveCounts = new int[INTEGER_ZERO];
        int[] displs = new int[INTEGER_ZERO];

        MPI.COMM_WORLD.Gatherv(results, INTEGER_ZERO, SIZE / QUANTITY_OF_WORKERS, MPI.DOUBLE, receiveBuffer, INTEGER_ZERO, receiveCounts, displs, MPI.DOUBLE, MAIN_RANK);
    }
}
