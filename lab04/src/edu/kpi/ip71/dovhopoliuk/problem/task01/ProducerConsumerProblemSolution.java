package edu.kpi.ip71.dovhopoliuk.problem.task01;

import edu.kpi.ip71.dovhopoliuk.problem.task01.consumer.Consumer;
import edu.kpi.ip71.dovhopoliuk.problem.task01.producer.Producer;
import edu.kpi.ip71.dovhopoliuk.problem.task01.queue.RecordQueue;
import edu.kpi.ip71.dovhopoliuk.problem.task01.queue.impl.CountingRecordQueue;
import edu.kpi.ip71.dovhopoliuk.solution.ProblemSolution;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Common.INTEGER_ZERO;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ProducerConsumer.CONSUMERS_THREAD_GROUP_NAME;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ProducerConsumer.CONSUMERS_THREAD_NAME_PREFIX;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ProducerConsumer.PROCESSED_TASKS_QUANTITY_MESSAGE;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ProducerConsumer.PRODUCERS_THREAD_GROUP_NAME;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ProducerConsumer.PRODUCERS_THREAD_NAME_PREFIX;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ProducerConsumer.START_MESSAGE;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.ProducerConsumer.SUBMITTED_TASKS_QUANTITY_MESSAGE;

public class ProducerConsumerProblemSolution implements ProblemSolution {

    private static final int PRODUCERS_QUANTITY = 5;
    private static final int CONSUMERS_QUANTITY = 5;
    private static final int QUANTITY_OF_RECORDS_PER_PRODUCER = 100;

    private static final int ACCESS_SEMAPHORE_PERMITS = 1;
    private static final int EMPTY_SEMAPHORE_PERMITS = 0;
    private static final int FULL_SEMAPHORE_PERMITS = 20;

    @Override
    public void solve() {

        final ThreadGroup producersThreadGroup = new ThreadGroup(PRODUCERS_THREAD_GROUP_NAME);
        final ThreadGroup consumersThreadGroup = new ThreadGroup(CONSUMERS_THREAD_GROUP_NAME);

        final CountingRecordQueue queue = new CountingRecordQueue();

        final Semaphore accessSemaphore = new Semaphore(ACCESS_SEMAPHORE_PERMITS);
        final Semaphore emptySemaphore = new Semaphore(EMPTY_SEMAPHORE_PERMITS);
        final Semaphore fullSemaphore = new Semaphore(FULL_SEMAPHORE_PERMITS);

        final List<Thread> producers = createProducers(producersThreadGroup, queue, accessSemaphore, emptySemaphore, fullSemaphore);
        final List<Thread> consumers = createConsumers(consumersThreadGroup, queue, accessSemaphore, emptySemaphore, fullSemaphore);

        logStart();

        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);

        stopSolution(producers, consumersThreadGroup, fullSemaphore);

        logResults(queue);
    }

    private List<Thread> createProducers(final ThreadGroup group, final RecordQueue queue,
                                         final Semaphore accessSemaphore, final Semaphore emptySemaphore,
                                         final Semaphore fullSemaphore) {

        return IntStream.range(INTEGER_ZERO, PRODUCERS_QUANTITY)

                .mapToObj(index -> new Producer(group, PRODUCERS_THREAD_NAME_PREFIX + index,
                        QUANTITY_OF_RECORDS_PER_PRODUCER, queue, accessSemaphore, emptySemaphore, fullSemaphore))

                .collect(Collectors.toList());
    }

    private List<Thread> createConsumers(final ThreadGroup group, final RecordQueue queue,
                                         final Semaphore accessSemaphore, final Semaphore emptySemaphore,
                                         final Semaphore fullSemaphore) {

        return IntStream.range(INTEGER_ZERO, CONSUMERS_QUANTITY)

                .mapToObj(index -> new Consumer(group, CONSUMERS_THREAD_NAME_PREFIX + index, queue, accessSemaphore, emptySemaphore, fullSemaphore))

                .collect(Collectors.toList());
    }

    private void stopSolution(final List<Thread> producers, final ThreadGroup consumersThreadGroup, final Semaphore fullSemaphore) {

        stopProducers(producers);

        while (fullSemaphore.availablePermits() != FULL_SEMAPHORE_PERMITS) ;

        consumersThreadGroup.interrupt();
    }

    private void stopProducers(final List<Thread> producers) {

        try {

            for (final Thread producer : producers) {

                producer.join();
            }

        } catch (final InterruptedException e) {

            e.printStackTrace();
        }
    }

    private void logStart() {

        System.out.println(START_MESSAGE);
    }

    private void logResults(final CountingRecordQueue queue) {

        System.out.println(SUBMITTED_TASKS_QUANTITY_MESSAGE + queue.getSubmittedTasksQuantity());
        System.out.println(PROCESSED_TASKS_QUANTITY_MESSAGE + queue.getProcessedTasksQuantity());
    }
}
