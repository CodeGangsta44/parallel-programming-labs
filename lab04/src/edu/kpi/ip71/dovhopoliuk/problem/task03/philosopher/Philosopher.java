package edu.kpi.ip71.dovhopoliuk.problem.task03.philosopher;

import edu.kpi.ip71.dovhopoliuk.problem.task03.fork.Fork;

import java.util.List;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.DiningPhilosophers.Philosopher.EAT_MESSAGE_PATTERN;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.DiningPhilosophers.Philosopher.THINK_MESSAGE_PATTERN;

public class Philosopher extends Thread {

    private final List<Fork> forks;
    private final int firstForkIndex;
    private final int secondForkIndex;

    public Philosopher(final ThreadGroup group, final String name,
                       final int id, final List<Fork> forks) {

        super(group, name);

        this.forks = forks;

        final int rightForkIndex = (id + 1) % forks.size();

        this.firstForkIndex = Math.min(id, rightForkIndex);
        this.secondForkIndex = Math.max(id, rightForkIndex);

    }

    @Override
    public void run() {

        while (!isInterrupted()) {

            think();

            synchronized (forks.get(firstForkIndex)) {

                synchronized (forks.get(secondForkIndex)) {

                    eat();
                }
            }
        }
    }

    public void eat() {

        System.out.printf(EAT_MESSAGE_PATTERN, getName(), firstForkIndex, secondForkIndex);

        try {

            Thread.sleep(10);

        } catch (InterruptedException e) {

            interrupt();
        }
    }

    public void think() {

        System.out.printf(THINK_MESSAGE_PATTERN, getName());
    }
}
