package edu.kpi.ip71.dovhopoliuk.problem.task04.barber;

import edu.kpi.ip71.dovhopoliuk.problem.task04.util.LockUtils;
import edu.kpi.ip71.dovhopoliuk.problem.task04.waiting.room.WaitingRoom;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.Common.INTEGER_ZERO;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.SleepingBarber.Barber.CUTTING_MESSAGE_PATTERN;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.SleepingBarber.Barber.CUT_MESSAGE_PATTERN;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.SleepingBarber.Barber.FELL_ASLEEP_MESSAGE_PATTERN;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.SleepingBarber.Barber.TOOK_CLIENT_MESSAGE_PATTERN;

public class Barber extends Thread {

    private final ReentrantLock client;
    private final ReentrantLock seats;
    private final ReentrantLock service;

    final Condition clientReady;
    final Condition serviceFinished;

    private final WaitingRoom waitingRoom;

    public Barber(final ThreadGroup group, final String name,
                  final ReentrantLock client, final ReentrantLock seats, final ReentrantLock service,
                  final Condition clientReady, final Condition serviceFinished,
                  final WaitingRoom waitingRoom) {

        super(group, name);

        this.client = client;
        this.seats = seats;
        this.service = service;

        this.clientReady = clientReady;
        this.serviceFinished = serviceFinished;

        this.waitingRoom = waitingRoom;
    }

    @Override
    public void run() {

        while (!isInterrupted()) {

            getClient();

            if (isInterrupted()) break;

            cut();

            finishService();
        }

    }

    private void getClient() {

        seats.lock();

        if (waitingRoom.getNumberOfBusyPlaces() == INTEGER_ZERO) {

            logFallAsleep();
            waitForClient();

            if (isInterrupted()) return;
        }

        takeClient();
    }

    private void waitForClient() {

        client.lock();
        seats.unlock();

        LockUtils.wait(client, clientReady);
    }

    private void takeClient() {

        lockSeatsIfNeeded();

        waitingRoom.releasePlace();
        seats.unlock();

        logTookClient();
    }

    private void lockSeatsIfNeeded() {

        if (!seats.isHeldByCurrentThread()) {

            seats.lock();
        }
    }

    private void finishService() {

        logCut();

        LockUtils.signalWithLock(service, serviceFinished);
    }

    private void cut() {

        logCutting();
    }

    private void logFallAsleep() {

        System.out.printf(FELL_ASLEEP_MESSAGE_PATTERN, getName());
    }

    private void logTookClient() {

        System.out.printf(TOOK_CLIENT_MESSAGE_PATTERN, getName());
    }

    private void logCutting() {

        System.out.printf(CUTTING_MESSAGE_PATTERN, getName());
    }

    private void logCut() {

        System.out.printf(CUT_MESSAGE_PATTERN, getName());
    }
}
