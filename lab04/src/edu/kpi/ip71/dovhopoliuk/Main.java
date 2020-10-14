package edu.kpi.ip71.dovhopoliuk;

import edu.kpi.ip71.dovhopoliuk.problem.task01.ProducerConsumerProblemSolution;
import edu.kpi.ip71.dovhopoliuk.problem.task02.ReaderWriterProblemSolution;
import edu.kpi.ip71.dovhopoliuk.problem.task03.DiningPhilosophersProblemSolution;
import edu.kpi.ip71.dovhopoliuk.problem.task04.SleepingBarberProblemSolution;

public class Main {

    public static void main(final String... args) {

        System.out.println("IP-71, Roman Dovhopoliuk, Variant #9\n");

//        new ProducerConsumerProblemSolution().solve();
//        new ReaderWriterProblemSolution().solve();
//        new DiningPhilosophersProblemSolution().solve();
        new SleepingBarberProblemSolution().solve();
    }
}
