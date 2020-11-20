package edu.kpi.ip71.dovhopoliuk.task;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Task {

    CompletableFuture<List<Integer>> solveTask(final List<Integer> firstList, final List<Integer> secondList, final List<Integer> thirdList);
}
