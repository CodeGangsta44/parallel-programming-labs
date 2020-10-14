package edu.kpi.ip71.dovhopoliuk.problem.task04.client;

import edu.kpi.ip71.dovhopoliuk.problem.task04.util.LockUtils;
import edu.kpi.ip71.dovhopoliuk.problem.task04.waiting.room.WaitingRoom;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static edu.kpi.ip71.dovhopoliuk.constants.Constants.SleepingBarber.Client.ENTERED_MESSAGE_PATTERN;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.SleepingBarber.Client.LEFT_MESSAGE_PATTERN;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.SleepingBarber.Client.SERVICED_MESSAGE_PATTERN;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.SleepingBarber.Client.TOOK_PLACE_MESSAGE_PATTERN;
import static edu.kpi.ip71.dovhopoliuk.constants.Constants.SleepingBarber.Client.WAKED_UP_BARBER_MESSAGE_PATTERN;

public class Client extends Thread {

    private final ReentrantLock client;
    private final ReentrantLock seats;
    private final ReentrantLock service;

    final Condition clientReady;
    final Condition serviceFinished;

    private final WaitingRoom waitingRoom;

    public Client(final ThreadGroup group, final String name,
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

        logEntered();

        if (takePlace()) {

            service.lock();

            wakeUpBarber();

            waitService();
        }

        logLeft();
    }

    private boolean takePlace() {

        boolean result = Boolean.FALSE;

        seats.lock();

        if (waitingRoom.getNumberOfBusyPlaces() < waitingRoom.getCapacity()) {

            logTookPlace();
            waitingRoom.takePlace();
            result = Boolean.TRUE;
        }

        seats.unlock();

        return result;

    }

    private void wakeUpBarber() {

        logWakedUpBarber();
        LockUtils.signalWithLock(client, clientReady);
    }

    private void waitService() {

        LockUtils.wait(service, serviceFinished);
        logServiced();
    }

    private void logEntered() {

        System.out.printf(ENTERED_MESSAGE_PATTERN, getName());
    }

    private void logTookPlace() {

        System.out.printf(TOOK_PLACE_MESSAGE_PATTERN, getName());
    }

    private void logWakedUpBarber() {

        System.out.printf(WAKED_UP_BARBER_MESSAGE_PATTERN, getName());
    }

    private void logServiced() {

        System.out.printf(SERVICED_MESSAGE_PATTERN, getName());
    }

    private void logLeft() {

        System.out.printf(LEFT_MESSAGE_PATTERN, getName());
    }
}
