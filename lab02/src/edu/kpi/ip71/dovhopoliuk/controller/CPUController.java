package edu.kpi.ip71.dovhopoliuk.controller;

import edu.kpi.ip71.dovhopoliuk.model.cpu.CPU;
import edu.kpi.ip71.dovhopoliuk.model.process.CPUProcess;
import edu.kpi.ip71.dovhopoliuk.model.queue.CPUQueue;
import edu.kpi.ip71.dovhopoliuk.model.queue.impl.DefaultCPUQueue;

import java.util.stream.IntStream;

public class CPUController {

    private final static String CPU_CORE_THREAD_GROUP_NAME = "CPU";
    private final static String CPU_CORE_THREAD_NAME_PREFIX = "Core #";

    private final static String PROCESS_THREAD_GROUP_NAME = "PROCESS";
    private final static String PROCESS_THREAD_NAME_PREFIX = "Process generator #";

    private final int intervalLowerLimit;
    private final int intervalUpperLimit;
    private final int targetQuantityOfTasks;
    private final int executionDuration;
    private final int quantityOfCores;
    private final int quantityOfProcesses;

    public CPUController(final int intervalLowerLimit, final int intervalUpperLimit, final int targetQuantityOfTasks,
                         final int executionDuration, final int quantityOfCores, final int quantityOfProcesses) {

        this.intervalLowerLimit = intervalLowerLimit;
        this.intervalUpperLimit = intervalUpperLimit;
        this.targetQuantityOfTasks = targetQuantityOfTasks;
        this.executionDuration = executionDuration;
        this.quantityOfCores = quantityOfCores;
        this.quantityOfProcesses = quantityOfProcesses;
    }

    public void executeSimulation() {

        final CPUQueue queue = new DefaultCPUQueue();

        final ThreadGroup coreGroup = new ThreadGroup(CPU_CORE_THREAD_GROUP_NAME);
        final Thread[] cores = createCores(queue, coreGroup);

        final ThreadGroup processGroup = new ThreadGroup(PROCESS_THREAD_GROUP_NAME);
        final Thread[] processes = createProcesses(queue, processGroup);

        startThreads(cores);
        startThreads(processes);

        while (true) {
            if (processGroup.activeCount() <= 0 && queue.getCurrentQueueSize() <= 0) {
                processGroup.interrupt();
                coreGroup.interrupt();
                break;
            }
        }

        System.out.println("Max queue size: " + queue.getMaxQueueSize());
    }

    private Thread[] createCores(final CPUQueue queue, final ThreadGroup group) {

        return IntStream.range(0, quantityOfCores)
                .mapToObj(index -> new CPU(group, CPU_CORE_THREAD_NAME_PREFIX + index, queue, executionDuration))
                .toArray(Thread[]::new);
    }

    private Thread[] createProcesses(final CPUQueue queue, final ThreadGroup group) {

        return IntStream.range(0, quantityOfProcesses)
                .mapToObj(index -> new CPUProcess(group, PROCESS_THREAD_NAME_PREFIX + index,
                        queue, intervalLowerLimit, intervalUpperLimit, targetQuantityOfTasks))
                .toArray(Thread[]::new);
    }

    private void startThreads(final Thread[] threads) {
        for (Thread thread : threads) {
            thread.start();
        }
    }
}
