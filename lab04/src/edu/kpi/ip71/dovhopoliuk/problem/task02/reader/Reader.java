package edu.kpi.ip71.dovhopoliuk.problem.task02.reader;

import edu.kpi.ip71.dovhopoliuk.problem.task02.reader.counter.ReaderCounter;
import edu.kpi.ip71.dovhopoliuk.problem.task02.storage.InfoStorage;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Common.INTEGER_ONE;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Common.INTEGER_ZERO;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Common.INTERRUPTED_MESSAGE_PATTERN;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ReaderWriter.Reader.MESSAGE_RECEIVED_PATTERN;

public class Reader extends Thread {

    private final ReaderCounter readerCounter;
    private final InfoStorage storage;

    public Reader(final ThreadGroup group, final String name, final ReaderCounter readerCounter, final InfoStorage storage) {

        super(group, name);

        this.readerCounter = readerCounter;
        this.storage = storage;
    }

    @Override
    public void run() {

        while (!isInterrupted()) {

            readMessage();
        }

        logStopping();
    }

    private void readMessage() {

        enterStep();

        storage.readInfo().ifPresent(this::processMessage);

        leaveStep();
    }

    private void enterStep() {

        synchronized (readerCounter) {

            if (readerCounter.increment() == INTEGER_ONE) {

                synchronized (storage) {

                    wait(storage);
                }
            }
        }
    }

    private void leaveStep() {

        synchronized (readerCounter) {

            if (readerCounter.decrement() == INTEGER_ZERO) {

                synchronized (storage) {

                    storage.notifyAll();
                    readerCounter.notifyAll();
                }

            } else {

                wait(readerCounter);
            }
        }
    }

    private void wait(final Object object) {

        try {

            object.wait();

        } catch (final InterruptedException e) {

            interrupt();
        }
    }

    private void processMessage(final String message) {

        if (!isInterrupted()) {

            System.out.printf(MESSAGE_RECEIVED_PATTERN, getName(), message);
        }
    }

    private void logStopping() {

        System.out.printf(INTERRUPTED_MESSAGE_PATTERN, getName());
    }
}
