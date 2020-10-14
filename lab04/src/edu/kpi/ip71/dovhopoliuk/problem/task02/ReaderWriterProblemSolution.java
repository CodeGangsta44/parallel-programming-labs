package edu.kpi.ip71.dovhopoliuk.problem.task02;

import edu.kpi.ip71.dovhopoliuk.problem.task02.reader.Reader;
import edu.kpi.ip71.dovhopoliuk.problem.task02.reader.counter.ReaderCounter;
import edu.kpi.ip71.dovhopoliuk.problem.task02.reader.counter.impl.DefaultReaderCounter;
import edu.kpi.ip71.dovhopoliuk.problem.task02.storage.InfoStorage;
import edu.kpi.ip71.dovhopoliuk.problem.task02.storage.impl.DefaultInfoStorage;
import edu.kpi.ip71.dovhopoliuk.problem.task02.writer.Writer;
import edu.kpi.ip71.dovhopoliuk.solution.ProblemSolution;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Common.INTEGER_ZERO;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ReaderWriter.START_MESSAGE;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ReaderWriter.READERS_THREAD_GROUP_NAME;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ReaderWriter.READERS_THREAD_NAME_PREFIX;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ReaderWriter.WRITERS_THREAD_GROUP_NAME;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ReaderWriter.WRITERS_THREAD_NAME_PREFIX;

public class ReaderWriterProblemSolution implements ProblemSolution {

    private static final int WRITERS_QUANTITY = 1;
    private static final int READERS_QUANTITY = 2;
    private static final int QUANTITY_OF_MESSAGES_PER_WRITER = 10;

    @Override
    public void solve() {

        final ThreadGroup writersThreadGroup = new ThreadGroup(WRITERS_THREAD_GROUP_NAME);
        final ThreadGroup readerThreadGroup = new ThreadGroup(READERS_THREAD_GROUP_NAME);

        final InfoStorage storage = new DefaultInfoStorage();
        final ReaderCounter readerCounter = new DefaultReaderCounter();

        final List<Thread> producers = createWriters(writersThreadGroup, storage);
        final List<Thread> consumers = createReaders(readerThreadGroup, storage, readerCounter);

        logStart();

        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);

        stopSolution(producers, readerThreadGroup);
    }

    private List<Thread> createWriters(final ThreadGroup group, final InfoStorage storage) {

        return IntStream.range(INTEGER_ZERO, WRITERS_QUANTITY)

                .mapToObj(index -> new Writer(group, WRITERS_THREAD_NAME_PREFIX + index, QUANTITY_OF_MESSAGES_PER_WRITER,
                        storage))

                .collect(Collectors.toList());
    }

    private List<Thread> createReaders(final ThreadGroup group, final InfoStorage storage,
                                       final ReaderCounter readerCounter) {

        return IntStream.range(INTEGER_ZERO, READERS_QUANTITY)

                .mapToObj(index -> new Reader(group, READERS_THREAD_NAME_PREFIX + index, readerCounter, storage))

                .collect(Collectors.toList());
    }

    private void stopSolution(final List<Thread> writers, final ThreadGroup readersGroup) {

        stopWriters(writers);

        readersGroup.interrupt();
    }

    private void stopWriters(final List<Thread> writers) {

        try {

            for (final Thread writer : writers) {

                writer.join();
            }

        } catch (final InterruptedException e) {

            e.printStackTrace();
        }
    }

    private void logStart() {

        System.out.println(START_MESSAGE);
    }
}
