package edu.kpi.ip71.dovhopoliuk.problem.task01.data;

public class Record {

    private final String creator;
    private final String additionalInfo;

    public Record(final String creator, final String additionalInfo) {

        this.creator = creator;
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "Record{" +
                "creator='" + creator + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}
