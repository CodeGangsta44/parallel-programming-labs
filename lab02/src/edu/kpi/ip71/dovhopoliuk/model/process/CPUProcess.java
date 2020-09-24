package edu.kpi.ip71.dovhopoliuk.model.process;

import edu.kpi.ip71.dovhopoliuk.model.queue.CPUQueue;

public class CPUProcess extends Thread {

    private final CPUQueue queue;
    private final int lowerLimit;
    private final int upperLimit;
    private final int targetQuantityOfProcesses;

    public CPUProcess(final ThreadGroup group, final String name, final CPUQueue queue, final int lowerLimit,
                      final int upperLimit, final int targetQuantityOfProcesses) {

        super(group, name);
        this.queue = queue;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.targetQuantityOfProcesses = targetQuantityOfProcesses;
    }

    @Override
    public void run() {

        startProcessGeneration();
    }

    private void startProcessGeneration() {

        for (int i = 0; i < targetQuantityOfProcesses && !isInterrupted(); i++) {
            handleInterval();
            generateNewProcess();
        }
    }

    private void handleInterval() {

        try {
            Thread.sleep(getNextInterval());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private void generateNewProcess() {

        queue.putProcess(new Object());
    }

    private int getNextInterval() {

        return lowerLimit + (int) (Math.random() * upperLimit);
    }
}
