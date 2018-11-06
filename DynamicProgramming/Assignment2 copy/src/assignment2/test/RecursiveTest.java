package assignment2.test;

import assignment2.*;
import java.util.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Basic tests for the {@link Recursive} implementation class.
 * 
 * We will use a more comprehensive test suite to test your code, so you should
 * add your own tests to this test suite to help you to debug your
 * implementation.
 */
public class RecursiveTest {

    /**
     * A basic test of the Recursive.optimalCostRecursive method.
     */
    @Test
    public void basicCostTest() {
        // initialise the queues used for testing
        List<List<Integer>> queues = new ArrayList<>();
        for (int i = 0; i < Action.NUMBER_OF_QUEUES; i++) {
            queues.add(new ArrayList<Integer>());
        }
        //queues.get(0).addAll(Arrays.asList(19,20,20));

        queues.get(0).addAll(Arrays.asList(10,10));
        queues.get(1).addAll(Arrays.asList(10,10));
        queues.get(2).addAll(Arrays.asList(10,10));

        //queues.get(0).addAll(Arrays.asList(4, 1));
        //queues.get(1).addAll(Arrays.asList(6, 10, 20, 10));
        //queues.get(2).addAll(Arrays.asList(20));

        // compare expected to actual results
        int expectedOptimalCost = 14;
        //int expectedOptimalCost = 6;
        //int expectedOptimalCost = 2;
        int actualOptimalCost = Recursive.optimalCostRecursive(queues);
        Assert.assertEquals(expectedOptimalCost, actualOptimalCost);
    }

}
