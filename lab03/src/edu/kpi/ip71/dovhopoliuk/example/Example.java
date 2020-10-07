package edu.kpi.ip71.dovhopoliuk.example;

import edu.kpi.ip71.dovhopoliuk.util.ParallelArrayUtils;
import edu.kpi.ip71.dovhopoliuk.util.impl.DefaultParallelArrayUtils;

import java.util.Random;
import java.util.stream.IntStream;

public class Example {

    private final static int INTEGER_ZERO = 0;

    private final ParallelArrayUtils utils;
    private final Random random;
    private final int arraySize;
    private int[] array;



    public Example(int arraySize) {

        this.utils = new DefaultParallelArrayUtils();
        this.random = new Random();
        this.arraySize = arraySize;
    }

    public void executeExample() {

        System.out.println("Starting initialization...");
        init();
        System.out.println("Initialization finished");

        System.out.println();

        System.out.print("Count elements by predicate 'number -> number % 3 == 0', result: ");
        System.out.println(utils.countElementsByPredicate(array, number -> number % 3 == 0));

        System.out.println();

        System.out.println("Find min and max elements, result: ");
        utils.findMinMax(array)
                .forEach(System.out::println);

        System.out.println();

        System.out.print("Calculate check sum, result: ");
        System.out.println(utils.calculateCheckSum(array));

        System.out.println();

        System.out.println("Example execution finished.");
    }

    private void init() {

        array = IntStream.range(INTEGER_ZERO, arraySize)
                .map(index -> random.nextInt())
                .toArray();
    }
}
