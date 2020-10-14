package edu.kpi.ip71.dovhopoliuk.problem.task02.storage.impl;

import edu.kpi.ip71.dovhopoliuk.problem.task02.storage.InfoStorage;

import java.util.Optional;

public class DefaultInfoStorage implements InfoStorage {

    private String info;

    @Override
    public void writeInfo(final String info) {

        this.info = info;
    }

    @Override
    public Optional<String> readInfo() {

        return Optional.ofNullable(info);
    }
}
