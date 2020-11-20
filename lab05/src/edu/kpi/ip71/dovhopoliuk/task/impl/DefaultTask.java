package edu.kpi.ip71.dovhopoliuk.task.impl;

import edu.kpi.ip71.dovhopoliuk.task.Task;
import edu.kpi.ip71.dovhopoliuk.utils.ListUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static edu.kpi.ip71.dovhopoliuk.utils.ListUtils.filterByRangeOfMax;
import static edu.kpi.ip71.dovhopoliuk.utils.ListUtils.filterEven;
import static edu.kpi.ip71.dovhopoliuk.utils.ListUtils.multiply;

public class DefaultTask implements Task {

    private static final int FIRST_LIST_MULTIPLIER = 2;
    private static final double THIRD_LIST_LOWER_BOUND_MULTIPLIER = 0.4;
    private static final double THIRD_LIST_UPPER_BOUND_MULTIPLIER = 0.6;

    @Override
    public CompletableFuture<List<Integer>> solveTask(final List<Integer> firstList, final List<Integer> secondList, final List<Integer> thirdList) {

        CompletableFuture<List<Integer>> first = CompletableFuture.supplyAsync(() -> multiply(firstList, FIRST_LIST_MULTIPLIER));
        CompletableFuture<List<Integer>> second = CompletableFuture.supplyAsync(() -> filterEven(secondList));
        CompletableFuture<List<Integer>> third = CompletableFuture.supplyAsync(() -> filterByRangeOfMax(thirdList,
                THIRD_LIST_LOWER_BOUND_MULTIPLIER, THIRD_LIST_UPPER_BOUND_MULTIPLIER));

        return second.thenCombine(third, ListUtils::intersect)
                .thenCombine(first, ListUtils::subtraction)
                .thenApply(ListUtils::sort);
    }
}
