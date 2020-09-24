package edu.kpi.ip71.dovhopoliuk.model.queue;

import edu.kpi.ip71.dovhopoliuk.model.process.Task;

import java.util.Optional;

public interface CPUQueue {

    void putProcess(final Task object);

    Optional<Task> getProcess();

    int getMaxQueueSize();

    int getCurrentQueueSize();
}
