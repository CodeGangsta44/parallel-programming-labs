package edu.kpi.ip71.dovhopoliuk.problem.task02.storage;

import java.util.Optional;

public interface InfoStorage {

    void writeInfo(final String info);

    Optional<String> readInfo();
}
