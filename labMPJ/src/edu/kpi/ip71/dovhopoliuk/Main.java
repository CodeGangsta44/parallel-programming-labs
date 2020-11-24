package edu.kpi.ip71.dovhopoliuk;

import edu.kpi.ip71.dovhopoliuk.process.impl.MainProcess;
import edu.kpi.ip71.dovhopoliuk.process.impl.WorkerProcess;
import mpi.MPI;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Settings.MAIN_RANK;

public class Main {

    public static void main(final String... args) {

        MPI.Init(args);


        if (MPI.COMM_WORLD.Rank() == MAIN_RANK) {

            executeMainProcess();

        } else {

            executeWorkerProcess();
        }

        MPI.Finalize();
    }

    public static void executeMainProcess() {

        new MainProcess().execute();
    }

    public static void executeWorkerProcess() {

        new WorkerProcess().execute();
    }
}
