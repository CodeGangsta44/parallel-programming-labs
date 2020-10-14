package edu.kpi.ip71.dovhopoliuk.problem.task01.producer;

import edu.kpi.ip71.dovhopoliuk.problem.task01.data.Record;
import edu.kpi.ip71.dovhopoliuk.problem.task01.queue.RecordQueue;

import java.util.concurrent.Semaphore;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Common.INTERRUPTED_MESSAGE_PATTERN;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ProducerConsumer.Producer.ADDITIONAL_INFO_PATTERN;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ProducerConsumer.Producer.RECORD_SUBMITTED_MESSAGE_PATTERN;

public class Producer extends Thread {

    private final int quantityOfRecordsToProduce;
    private final RecordQueue queue;
    private final Semaphore accessSemaphore;
    private final Semaphore emptySemaphore;
    private final Semaphore fullSemaphore;

    private int quantityOfProducedRecords;

    public Producer(final ThreadGroup group, final String name, final int quantityOfRecordsToProduce, final RecordQueue queue,
                    final Semaphore accessSemaphore, final Semaphore emptySemaphore, final Semaphore fullSemaphore) {

        super(group, name);

        this.quantityOfRecordsToProduce = quantityOfRecordsToProduce;
        this.queue = queue;
        this.accessSemaphore = accessSemaphore;
        this.emptySemaphore = emptySemaphore;
        this.fullSemaphore = fullSemaphore;

        this.quantityOfProducedRecords = 0;
    }

    @Override
    public void run() {

        while (!isInterrupted() && quantityOfProducedRecords < quantityOfRecordsToProduce) {

            submitRecord();
        }
    }

    private void submitRecordInner() throws InterruptedException {

        final Record record = generateRecord();

        fullSemaphore.acquire();
        accessSemaphore.acquire();

        queue.submitRecord(record);

        accessSemaphore.release();
        emptySemaphore.release();

        logRecord(record);
    }

    private void submitRecord() {

        try {

            submitRecordInner();

        } catch (final InterruptedException e) {

            System.out.printf(INTERRUPTED_MESSAGE_PATTERN, getName());
        }
    }

    private Record generateRecord() {

        quantityOfProducedRecords++;
        return new Record(getName(), generateAdditionalInfo());
    }

    private String generateAdditionalInfo() {

        return String.format(ADDITIONAL_INFO_PATTERN, quantityOfProducedRecords, quantityOfRecordsToProduce, getName());
    }

    private void logRecord(final Record record) {

        System.out.printf(RECORD_SUBMITTED_MESSAGE_PATTERN, getName(), record.toString());
    }
}
