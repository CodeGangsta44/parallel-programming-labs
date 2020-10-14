package edu.kpi.ip71.dovhopoliuk.problem.task04;

import edu.kpi.ip71.dovhopoliuk.problem.task04.barber.Barber;
import edu.kpi.ip71.dovhopoliuk.problem.task04.client.Client;
import edu.kpi.ip71.dovhopoliuk.problem.task04.waiting.room.WaitingRoom;
import edu.kpi.ip71.dovhopoliuk.problem.task04.waiting.room.impl.DefaultWaitingRoom;
import edu.kpi.ip71.dovhopoliuk.solution.ProblemSolution;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Common.INTEGER_ZERO;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.SleepingBarber.START_MESSAGE;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.SleepingBarber.BARBERS_THREAD_GROUP_NAME;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.SleepingBarber.BARBERS_THREAD_NAME_PREFIX;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.SleepingBarber.CLIENTS_THREAD_GROUP_NAME;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.SleepingBarber.CLIENTS_THREAD_NAME_PREFIX;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.SleepingBarber.MAX_QUEUE_SIZE_MESSAGE;

public class SleepingBarberProblemSolution implements ProblemSolution {

    private static final int BARBERS_QUANTITY = 1;
    private static final int CLIENTS_QUANTITY = 5;
    private static final int QUANTITY_OF_SEATS = 2;

    @Override
    public void solve() {

        final ThreadGroup barbersThreadGroup = new ThreadGroup(BARBERS_THREAD_GROUP_NAME);
        final ThreadGroup clientsThreadGroup = new ThreadGroup(CLIENTS_THREAD_GROUP_NAME);

        final ReentrantLock client = new ReentrantLock();
        final ReentrantLock seats = new ReentrantLock();
        final ReentrantLock service = new ReentrantLock();

        final DefaultWaitingRoom waitingRoom = new DefaultWaitingRoom(QUANTITY_OF_SEATS);

        final Condition clientReady = client.newCondition();
        final Condition serviceFinished = service.newCondition();

        final List<Thread> barbers = createBarbers(barbersThreadGroup, client, seats, service, clientReady, serviceFinished, waitingRoom);
        final List<Thread> clients = createClients(clientsThreadGroup, client, seats, service, clientReady, serviceFinished, waitingRoom);

        logStart();

        barbers.forEach(Thread::start);
        clients.forEach(Thread::start);

        stopSolution(clients, barbersThreadGroup);

        logResults(waitingRoom);
    }

    private List<Thread> createBarbers(final ThreadGroup group, final ReentrantLock client, final ReentrantLock seats,
                                       final ReentrantLock service, final Condition clientReady, final Condition serviceFinished,
                                       final WaitingRoom waitingRoom) {

        return IntStream.range(INTEGER_ZERO, BARBERS_QUANTITY)

                .mapToObj(index -> new Barber(group, BARBERS_THREAD_NAME_PREFIX + index,
                        client, seats, service, clientReady, serviceFinished, waitingRoom))

                .collect(Collectors.toList());
    }

    private List<Thread> createClients(final ThreadGroup group, final ReentrantLock client, final ReentrantLock seats,
                                       final ReentrantLock service, final Condition clientReady, final Condition serviceFinished,
                                       final WaitingRoom waitingRoom) {

        return IntStream.range(INTEGER_ZERO, CLIENTS_QUANTITY)

                .mapToObj(index -> new Client(group, CLIENTS_THREAD_NAME_PREFIX + index,
                        client, seats, service, clientReady, serviceFinished, waitingRoom))

                .collect(Collectors.toList());
    }

    private void stopSolution(final List<Thread> clients, final ThreadGroup barbersThreadGroup) {

        waitForClients(clients);

        barbersThreadGroup.interrupt();
    }

    private void waitForClients(final List<Thread> clients) {

        for (final Thread client : clients) {

            try {

                client.join();

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }

    private void logStart() {

        System.out.println(START_MESSAGE);
    }

    private void logResults(final DefaultWaitingRoom waitingRoom) {

        System.out.printf(MAX_QUEUE_SIZE_MESSAGE, waitingRoom.getMax());
    }
}
