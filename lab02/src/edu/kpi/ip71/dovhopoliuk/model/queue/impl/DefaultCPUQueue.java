package edu.kpi.ip71.dovhopoliuk.model.queue.impl;

import edu.kpi.ip71.dovhopoliuk.model.queue.CPUQueue;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

public class DefaultCPUQueue implements CPUQueue {

    private final Queue<Object> queue = new ArrayDeque<>();
    private int maxQueueSize = 0;

    @Override
    public synchronized void putProcess(final Object object) {

        System.out.println("PUT into queue");

        queue.offer(object);
        inspectMaxQueueSize(queue.size());
        notifyAll();
    }

    @Override
    public synchronized Optional<Object> getProcess() {

        System.out.println("GET from queue");

        return Optional.ofNullable(queue.poll());
    }

    @Override
    public int getMaxQueueSize() {

        return maxQueueSize;
    }

    @Override
    public int getCurrentQueueSize() {

        return queue.size();
    }

    private void inspectMaxQueueSize(final int currentQueueSize) {

        if (currentQueueSize > maxQueueSize) {
            maxQueueSize = currentQueueSize;
        }
    }
}
