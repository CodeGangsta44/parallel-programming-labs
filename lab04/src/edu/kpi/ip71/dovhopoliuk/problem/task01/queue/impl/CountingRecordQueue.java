package edu.kpi.ip71.dovhopoliuk.problem.task01.queue.impl;

import edu.kpi.ip71.dovhopoliuk.problem.task01.data.Record;
import edu.kpi.ip71.dovhopoliuk.problem.task01.queue.RecordQueue;

import java.util.ArrayDeque;
import java.util.Queue;

public class CountingRecordQueue implements RecordQueue {

    private final Queue<Record> queue;

    private int submittedTasksQuantity;
    private int processedTasksQuantity;

    public CountingRecordQueue() {

        this.queue = new ArrayDeque<>();
        submittedTasksQuantity = 0;
        processedTasksQuantity = 0;
    }

    @Override
    public void submitRecord(final Record record) {

        submittedTasksQuantity++;

        queue.offer(record);
    }

    @Override
    public Record getRecord() {

        processedTasksQuantity++;

        return queue.poll();
    }

    public int getSubmittedTasksQuantity() {

        return submittedTasksQuantity;
    }

    public int getProcessedTasksQuantity() {

        return processedTasksQuantity;
    }
}
