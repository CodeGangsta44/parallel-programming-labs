package edu.kpi.ip71.dovhopoliuk.model.process;

import edu.kpi.ip71.dovhopoliuk.model.queue.CPUQueue;

public class CPUProcess extends Thread {

    private final CPUQueue queue;
    private final int lowerIntervalLimit;
    private final int upperIntervalLimit;
    private final int targetQuantityOfProcesses;
    private final int lowerDurationLimit;
    private final int upperDurationLimit;

    public CPUProcess(final ThreadGroup group, final String name, final CPUQueue queue, final int lowerIntervalLimit,
                      final int upperIntervalLimit, final int targetQuantityOfProcesses,
                      final int lowerDurationLimit, final int upperDurationLimit) {

        super(group, name);
        this.queue = queue;
        this.lowerIntervalLimit = lowerIntervalLimit;
        this.upperIntervalLimit = upperIntervalLimit;
        this.targetQuantityOfProcesses = targetQuantityOfProcesses;
        this.lowerDurationLimit = lowerDurationLimit;
        this.upperDurationLimit = upperDurationLimit;
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
            Thread.sleep(getNextInterval(lowerIntervalLimit, upperIntervalLimit));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private void generateNewProcess() {

        queue.putProcess(new Task(getNextInterval(lowerDurationLimit, upperDurationLimit)));
    }

    private int getNextInterval(final int lower, final int upper) {

        return lower + (int) (Math.random() * upper);
    }
}
