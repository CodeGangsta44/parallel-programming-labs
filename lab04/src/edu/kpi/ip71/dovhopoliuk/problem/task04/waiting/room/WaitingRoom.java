package edu.kpi.ip71.dovhopoliuk.problem.task04.waiting.room;

public interface WaitingRoom {

    int getCapacity();

    int getNumberOfBusyPlaces();

    void takePlace();

    void releasePlace();
}
