package edu.kpi.ip71.dovhopoliuk.model.queue;

import java.util.Optional;

public interface CPUQueue {

    void putProcess(final Object object);

    Optional<Object> getProcess();

    int getMaxQueueSize();

    int getCurrentQueueSize();
}
