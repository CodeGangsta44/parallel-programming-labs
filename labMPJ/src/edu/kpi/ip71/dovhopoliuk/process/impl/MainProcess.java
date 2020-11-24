package edu.kpi.ip71.dovhopoliuk.process.impl;

import edu.kpi.ip71.dovhopoliuk.process.Process;
import mpi.MPI;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Common.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Common.INTEGER_ZERO;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Settings.MAIN_RANK;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Settings.QUANTITY_OF_PROCESSES;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Settings.QUANTITY_OF_WORKERS;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Settings.SIZE;

public class MainProcess implements Process {

    private static final Random RANDOM = new Random();

    @Override
    public void execute() {

        final double[][] matrix = generateMatrix();
        final double[] vector = generateRow();

        delegateToWorkers(matrix, vector);

        double[] result = receiveResult();

        System.out.println(Arrays.stream(result).boxed().collect(Collectors.toList()));
    }

    private void delegateToWorkers(final double[][] matrix, final double[] vector) {

        sendMatrix(matrix);
        sendVector(vector);
    }

    private void sendMatrix(final double[][] matrix) {

        double[][] receiveBuffer = new double[INTEGER_ZERO][INTEGER_ZERO];

        int[] sendCounts = getCounts();
        int[] displs = getDispls();

        MPI.COMM_WORLD.Scatterv(matrix, INTEGER_ZERO, sendCounts, displs, MPI.OBJECT, receiveBuffer, INTEGER_ZERO, INTEGER_ZERO, MPI.OBJECT, MAIN_RANK);
    }

    private void sendVector(final double[] vector) {

        MPI.COMM_WORLD.Bcast(vector, INTEGER_ZERO, SIZE, MPI.DOUBLE, MAIN_RANK);
    }

    private double[] receiveResult() {

        double[] result = new double[SIZE];
        double[] sendBuffer = new double[INTEGER_ZERO];

        int[] receiveCounts = getCounts();
        int[] displs = getDispls();

        MPI.COMM_WORLD.Gatherv(sendBuffer, INTEGER_ZERO, INTEGER_ZERO, MPI.DOUBLE, result, INTEGER_ZERO, receiveCounts, displs, MPI.DOUBLE, MAIN_RANK);

        return result;
    }

    private int[] getCounts() {

        final int[] sendCounts = new int[QUANTITY_OF_PROCESSES];
        sendCounts[MAIN_RANK] = INTEGER_ZERO;

        IntStream.range(INTEGER_ONE, QUANTITY_OF_PROCESSES)
                .forEach(index -> sendCounts[index] = SIZE / QUANTITY_OF_WORKERS);

        return sendCounts;
    }

    private int[] getDispls() {

        final int[] displs = new int[QUANTITY_OF_PROCESSES];

        displs[MAIN_RANK] = INTEGER_ZERO;

        IntStream.range(INTEGER_ONE, QUANTITY_OF_PROCESSES)
                .forEach(index -> displs[index] = (index - INTEGER_ONE) * (SIZE / QUANTITY_OF_WORKERS));

        return displs;
    }

    private double[][] generateMatrix() {

        return IntStream.range(INTEGER_ZERO, SIZE)
                .mapToObj(index -> generateRow())
                .toArray(double[][]::new);
    }

    private double[] generateRow() {

        return IntStream.range(INTEGER_ZERO, SIZE)
                .mapToDouble(index -> RANDOM.nextGaussian())
                .toArray();
    }
}
