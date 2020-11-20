package edu.kpi.ip71.dovhopoliuk.example.impl;

import edu.kpi.ip71.dovhopoliuk.example.Example;
import edu.kpi.ip71.dovhopoliuk.task.impl.DefaultTask;
import edu.kpi.ip71.dovhopoliuk.utils.GenerationUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;


public class AsyncComputationsExample implements Example {

    private static final int LENGTH_OF_LISTS = 100;
    private static final int GENERATION_BOUND = 100;

    @Override
    public void executeExample() {

        final List<Integer> firstList = GenerationUtils.generate(LENGTH_OF_LISTS, GENERATION_BOUND);
        final List<Integer> secondList = GenerationUtils.generate(LENGTH_OF_LISTS, GENERATION_BOUND);
        final List<Integer> thirdList = GenerationUtils.generate(LENGTH_OF_LISTS, GENERATION_BOUND);

        displayInitialLists(firstList, secondList, thirdList);

        CompletableFuture<List<Integer>> result = new DefaultTask().solveTask(firstList, secondList, thirdList);

        getAndDisplayResult(result);
    }

    @SafeVarargs
    private void displayInitialLists(final List<Integer>... lists) {

        IntStream.range(0, lists.length)
                .forEach(index -> System.out.println("List #" + index + " = " + lists[index].toString()));
    }

    private void getAndDisplayResult(final CompletableFuture<List<Integer>> result) {

        System.out.println("Finished: " + result.isDone());

        System.out.println("Result = " + getResultFromFuture(result).toString());

        System.out.println("Finished: " + result.isDone());
    }

    private List<Integer> getResultFromFuture(final CompletableFuture<List<Integer>> future) {

        List<Integer> result = null;

        try {

            result = future.get();

        } catch (InterruptedException | ExecutionException e) {

            e.printStackTrace();
        }

        return result;
    }
}
