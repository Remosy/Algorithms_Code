package assignment2.test;

import assignment2.*;
import java.util.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Basic tests for the {@link Dynamic} implementation class.
 * 
 * We will use a more comprehensive test suite to test your code, so you should
 * add your own tests to this test suite to help you to debug your
 * implementation.
 */
public class DynamicTest {

    /**
     * A basic test of the Dynamic.optimalCostDynamic method
     */
    @Test
    public void basicCostTest() {
        // initialise the queues used for testing
        List<List<Integer>> queues = new ArrayList<>();
        for (int i = 0; i < Action.NUMBER_OF_QUEUES; i++) {
            queues.add(new ArrayList<Integer>());
        }

        //queues.get(0).addAll(Arrays.asList(19,20,20));
        //queues.get(0).addAll(Arrays.asList(10,10,50));
        //queues.get(1).addAll(Arrays.asList(50,100,100));
        //queues.get(2).addAll(Arrays.asList(20));
        //queues.get(0).addAll(Arrays.asList(4, 1));
        //queues.get(1).addAll(Arrays.asList(6, 10, 20, 10));
        //queues.get(2).addAll(Arrays.asList(20));

        // compare expected to actual results
        //int expectedOptimalCost = 14;
        int expectedOptimalCost = 0;
        int actualOptimalCost = Dynamic.optimalCostDynamic(queues);
        Assert.assertEquals(expectedOptimalCost, actualOptimalCost);
    }

    /**
     * A basic test of the Dynamic.optimalActionsDynamic method.
     */
    @Test
    public void basicActionsTest() {
        // initialise the queues used for testing
        List<List<Integer>> queues = new ArrayList<>();
        for (int i = 0; i < Action.NUMBER_OF_QUEUES; i++) {
            queues.add(new ArrayList<Integer>());
        }
        //queues.get(0).addAll(Arrays.asList(4, 1));
        //queues.get(1).addAll(Arrays.asList(6, 10, 20, 10));
        //queues.get(2).addAll(Arrays.asList(20));
        queues.get(0).addAll(Arrays.asList(19,20,20));
        // compare expected to actual results
        int expectedOptimalCost = 6;
        List<Action> actualOptimalActions = Dynamic
                .optimalActionsDynamic(queues);
        System.out.println(actualOptimalActions);
        checkOptimalActions(queues, actualOptimalActions, expectedOptimalCost);
    }

    /*------------helper methods------------/
    
    /**
     * Checks that the sequence of actions given has the expected optimal cost,
     * and that they are a valid sequence of actions that can be applied to the
     * queues to make them empty.
     */
    private void checkOptimalActions(List<List<Integer>> queues,
            List<Action> actualOptimalActions, int expectedOptimalCost) {
        // the actual cost of the list of actions
        int actualOptimalCost = 0;
        // keeps track of the current state of the queues as actions are applied
        List<Integer> front = new ArrayList<>(Arrays.asList(0, 0, 0));

        /*
         * Apply each action (in order) to the queues, checking the validity of
         * each action, and accumulating the actual cost of the actions.
         */
        for (Action action : actualOptimalActions) {
            // check that the action can be applied
            Assert.assertTrue(action.canApply(queues, front));
            // check that the action is of the right type
            Assert.assertTrue(action instanceof RejectAction
                    || action instanceof PairAction
                    || action instanceof SequentialPairAction);
            actualOptimalCost += action.cost(queues, front);
            action.apply(queues, front);
        }

        // check that the queues are empty after applying the actions
        Assert.assertTrue(front.get(0) == queues.get(0).size());
        Assert.assertTrue(front.get(1) == queues.get(1).size());
        Assert.assertTrue(front.get(2) == queues.get(2).size());

        // check that the actualOptimalCost equals the expected one
        Assert.assertEquals(expectedOptimalCost, actualOptimalCost);
    }

}
