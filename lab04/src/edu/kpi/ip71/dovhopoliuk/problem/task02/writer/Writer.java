package edu.kpi.ip71.dovhopoliuk.problem.task02.writer;

import edu.kpi.ip71.dovhopoliuk.problem.task02.storage.InfoStorage;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Common.INTERRUPTED_MESSAGE_PATTERN;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ReaderWriter.Writer.FINISHED_MESSAGE_PATTERN;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ReaderWriter.Writer.MESSAGE_SUBMITTED_PATTERN;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ReaderWriter.Writer.MESSAGE_TEMPLATE;

public class Writer extends Thread {

    private final int quantityOfMessagesToWrite;
    private final InfoStorage storage;

    private int quantityOfWroteMessages;

    public Writer(final ThreadGroup group, final String name,
                  final int quantityOfMessagesToWrite, final InfoStorage storage) {

        super(group, name);

        this.quantityOfMessagesToWrite = quantityOfMessagesToWrite;
        this.storage = storage;
        this.quantityOfWroteMessages = 0;
    }

    @Override
    public void run() {

        while (!isInterrupted() && quantityOfWroteMessages < quantityOfMessagesToWrite) {

            quantityOfWroteMessages++;
            writeMessage(generateMessage());
        }

        logFinished();
    }

    private void writeMessage(final String message) {

        synchronized (storage) {

            storage.writeInfo(message);

            storage.notifyAll();

            System.out.printf(MESSAGE_SUBMITTED_PATTERN, getName(), message);

            waitForStorage();
        }
    }

    private void waitForStorage() {

        try {

            storage.wait();

        } catch (InterruptedException e) {

            interrupt();
            System.out.printf(INTERRUPTED_MESSAGE_PATTERN, getName());
        }
    }

    private String generateMessage() {

        return String.format(MESSAGE_TEMPLATE, quantityOfWroteMessages, quantityOfMessagesToWrite, getName());
    }

    private void logFinished() {

        System.out.printf(FINISHED_MESSAGE_PATTERN, getName());
    }
}
