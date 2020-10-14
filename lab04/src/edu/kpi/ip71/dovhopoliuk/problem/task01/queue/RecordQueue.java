package edu.kpi.ip71.dovhopoliuk.problem.task01.queue;

import edu.kpi.ip71.dovhopoliuk.problem.task01.data.Record;

public interface RecordQueue {

    void submitRecord(final Record record);

    Record getRecord();
}
