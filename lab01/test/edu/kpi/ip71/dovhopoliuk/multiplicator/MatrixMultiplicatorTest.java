package edu.kpi.ip71.dovhopoliuk.multiplicator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class MatrixMultiplicatorTest {
    private static final double[][] MATRIX = {{1.0, 3.0, -2.5},
            {0.0, -4.0, 9.9},
            {1.3, 0.56, 5.0}};
    private static final double[] VECTOR = {0.0, 2.0, 0.0};
    private static final int THREADS_QUANTITY = 2;

    private static final double[] EXPECTED_RESULT = {6.0, -8.0, 1.12};
    private static final double DELTA = 0.0;

    private MatrixMultiplicator unit;

    @Before
    public void setUp() {
        unit = new MatrixMultiplicator(MATRIX, VECTOR, THREADS_QUANTITY);
    }

    @Test
    public void shouldMultiplyMatrixOnVectorSerially() {
        assertArrayEquals(EXPECTED_RESULT, unit.multiplySerially(), DELTA);
    }

    @Test
    public void shouldMultiplyMatrixOnVectorParallelly() {
        assertArrayEquals(EXPECTED_RESULT, unit.multiplyParallelly(), DELTA);
    }
}