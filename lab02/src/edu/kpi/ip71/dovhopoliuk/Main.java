package edu.kpi.ip71.dovhopoliuk;

import edu.kpi.ip71.dovhopoliuk.controller.CPUController;

public class Main {

    private final static int INTERVAL_LOWER_LIMIT = 10;
    private final static int INTERVAL_UPPER_LIMIT = 1000;
    private final static int TARGET_QUANTITY_OF_TASKS = 10;
    private final static int EXECUTION_DURATION = 5000;
    private final static int QUANTITY_OF_CORES = 2;
    private final static int QUANTITY_OF_PROCESSES = 1;

    public static void main(final String... args) {

        final CPUController controller = new CPUController(INTERVAL_LOWER_LIMIT, INTERVAL_UPPER_LIMIT, TARGET_QUANTITY_OF_TASKS,
                EXECUTION_DURATION, QUANTITY_OF_CORES, QUANTITY_OF_PROCESSES);

        controller.executeSimulation();
    }
}
