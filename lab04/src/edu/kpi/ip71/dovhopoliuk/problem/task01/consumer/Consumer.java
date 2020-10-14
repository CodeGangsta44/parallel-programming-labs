package edu.kpi.ip71.dovhopoliuk.problem.task01.consumer;

import edu.kpi.ip71.dovhopoliuk.problem.task01.data.Record;
import edu.kpi.ip71.dovhopoliuk.problem.task01.queue.RecordQueue;

import java.util.concurrent.Semaphore;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Common.INTERRUPTED_MESSAGE_PATTERN;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ProducerConsumer.Consumer.RECORD_RECEIVED_MESSAGE_PATTERN;

public class Consumer extends Thread {

    private final RecordQueue queue;
    private final Semaphore accessSemaphore;
    private final Semaphore emptySemaphore;
    private final Semaphore fullSemaphore;

    public Consumer(final ThreadGroup group, final String name, final RecordQueue queue,
                    final Semaphore accessSemaphore, final Semaphore emptySemaphore, final Semaphore fullSemaphore) {

        super(group, name);

        this.queue = queue;
        this.accessSemaphore = accessSemaphore;
        this.emptySemaphore = emptySemaphore;
        this.fullSemaphore = fullSemaphore;
    }

    @Override
    public void run() {

        while (!isInterrupted()) {

            processRecord();
        }
    }

    private void processRecordInner() throws InterruptedException {

        emptySemaphore.acquire();
        accessSemaphore.acquire();

        final Record record = queue.getRecord();

        accessSemaphore.release();
        fullSemaphore.release();

        logRecord(record);
    }

    private void processRecord() {

        try {

            processRecordInner();

        } catch (final InterruptedException e) {

            interrupt();
            System.out.printf(INTERRUPTED_MESSAGE_PATTERN, getName());
        }
    }

    private void logRecord(final Record record) {

        System.out.printf(RECORD_RECEIVED_MESSAGE_PATTERN, getName(), record.toString());
    }
}
