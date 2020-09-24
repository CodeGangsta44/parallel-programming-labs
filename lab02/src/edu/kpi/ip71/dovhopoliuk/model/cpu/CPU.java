package edu.kpi.ip71.dovhopoliuk.model.cpu;

import edu.kpi.ip71.dovhopoliuk.model.queue.CPUQueue;

import java.util.Optional;

public class CPU extends Thread {

    private final CPUQueue queue;
    private final int executionDuration;

    public CPU(final ThreadGroup group, final String name, final CPUQueue queue, final int executionDuration) {

        super(group, name);
        this.queue = queue;
        this.executionDuration = executionDuration;
    }

    @Override
    public void run() {

        while (!isInterrupted()) {
            Optional.of(queue.getProcess())
                    .filter(Optional::isPresent)
                    .orElseGet(this::waitForProcess)
                    .ifPresent(this::handleProcess);
        }
    }

    private Optional<Object> waitForProcess() {

        synchronized (queue) {
            waitQueue();
            return queue.getProcess();
        }
    }

    private void waitQueue() {

        System.out.println(getName() + " - " + "waiting queue.");
        try {
            queue.wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void handleProcess(final Object process) {

        System.out.println(getName() + " - " + "handling process.");
        try {
            Thread.sleep(executionDuration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
