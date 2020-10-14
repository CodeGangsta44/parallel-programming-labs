package edu.kpi.ip71.dovhopoliuk.problem.task03;

import edu.kpi.ip71.dovhopoliuk.problem.task03.fork.Fork;
import edu.kpi.ip71.dovhopoliuk.problem.task03.philosopher.Philosopher;
import edu.kpi.ip71.dovhopoliuk.solution.ProblemSolution;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Common.INTEGER_ZERO;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.DiningPhilosophers.FORK_NAME_PREFIX;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.DiningPhilosophers.PHILOSOPHERS_THREAD_GROUP_NAME;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.DiningPhilosophers.PHILOSOPHERS_THREAD_NAME_PREFIX;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.DiningPhilosophers.START_MESSAGE;

public class DiningPhilosophersProblemSolution implements ProblemSolution {

    private static final int PHILOSOPHERS_QUANTITY = 5;

    @Override
    public void solve() {

        final ThreadGroup philosophersThreadGroup = new ThreadGroup(PHILOSOPHERS_THREAD_GROUP_NAME);

        final List<Fork> forks = createForks();

        final List<Thread> philosophers = createPhilosophers(philosophersThreadGroup, forks);

        logStart();

        philosophers.forEach(Thread::start);

        stopSolution(philosophersThreadGroup);
    }

    private List<Fork> createForks() {

        return IntStream.range(INTEGER_ZERO, PHILOSOPHERS_QUANTITY)

                .mapToObj(index -> new Fork(FORK_NAME_PREFIX + index))

                .collect(Collectors.toList());
    }

    private List<Thread> createPhilosophers(final ThreadGroup group, final List<Fork> forks) {

        return IntStream.range(INTEGER_ZERO, PHILOSOPHERS_QUANTITY)

                .mapToObj(index -> new Philosopher(group, PHILOSOPHERS_THREAD_NAME_PREFIX + index, index, forks))

                .collect(Collectors.toList());
    }

    private void stopSolution(final ThreadGroup philosophersThreadGroup) {

        try {

            Thread.sleep(1000);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        philosophersThreadGroup.interrupt();
    }

    private void logStart() {

        System.out.println(START_MESSAGE);
    }
}
