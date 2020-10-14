package edu.kpi.ip71.dovhopoliuk.problem.task02.reader.counter.impl;

import edu.kpi.ip71.dovhopoliuk.problem.task02.reader.counter.ReaderCounter;

public class DefaultReaderCounter implements ReaderCounter {
    private int quantityOfActiveReaders;

    public DefaultReaderCounter() {

        this.quantityOfActiveReaders = 0;
    }

    @Override
    public int increment() {

        return ++quantityOfActiveReaders;
    }

    @Override
    public int decrement() {

        return --quantityOfActiveReaders;
    }
}
