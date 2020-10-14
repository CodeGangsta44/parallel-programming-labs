package edu.kpi.ip71.dovhopoliuk.problem.task01.queue.impl;

import edu.kpi.ip71.dovhopoliuk.problem.task01.data.Record;
import edu.kpi.ip71.dovhopoliuk.problem.task01.queue.RecordQueue;

import java.util.ArrayDeque;
import java.util.Queue;

public class DefaultRecordQueue implements RecordQueue {

    private final Queue<Record> queue;

    public DefaultRecordQueue() {

        this.queue = new ArrayDeque<>();
    }

    @Override
    public void submitRecord(final Record record) {

        queue.offer(record);
    }

    @Override
    public Record getRecord() {

        return queue.poll();
    }
}
