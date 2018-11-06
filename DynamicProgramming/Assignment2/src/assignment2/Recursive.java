package assignment2;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.*;

public class Recursive {

    /**
     * Returns the minimum cost of any sequence of actions (where each action
     * may only be of type RejectAction, PairAction or SequentialPairAction)
     * that can be applied (in the order in which they appear in the action
     * sequence) to the given queues, so that all of the queues become empty.
     * (See handout for details.)
     * 
     * This method must be implemented using a recursive programming solution to
     * the problem. It is expected to have a worst-case running time that is
     * exponential. You may assume that Action.NUMBER_OF_QUEUES == 3.
     * 
     * @require queues != null && !queues.contains(null) && queues.size() =
     *          Action.NUMBER_OF_QUEUES
     * 
     * @ensure Returns the minimum cost of any sequence of actions that can be
     *         applied to the given queues so that they become empty.
     */
    public static int optimalCostRecursive(List<List<Integer>> queues) {
        // IMPLEMENT THIS METHOD BY IMPLEMENTING THE PRIVATE METHOD IN THIS
        // CLASS THAT HAS THE SAME NAME
        return optimalCostRecursive(queues,
                new ArrayList<Integer>(Arrays.asList(0, 0, 0)));
    }

    /**
     * Returns the minimum cost of any sequence of actions (where each action
     * may only be of type RejectAction, PairAction or SequentialPairAction)
     * that can be applied (in the order in which they appear in the action
     * sequence) to the current state of the given queues, so that all of the
     * queues become empty. (See handout for details.)
     * 
     * For each i in the indices of queues, the ith queue is defined to
     * currently contain the elements in queues.get(i) from index front.get(i)
     * to the end of queues.get(i).
     * 
     * This method must be implemented using a recursive programming solution to
     * the problem. It is expected to have a worst-case running time that is
     * exponential. You may assume that Action.NUMBER_OF_QUEUES == 3.
     * 
     * @require queues != null && !queues.contains(null) && front !=null &&
     *          !front.contains(null) && queues.size() = front.size() =
     *          Action.NUMBER_OF_QUEUES && for all indices i in queues, 0 <=
     *          front.get(i) <= queues.get(i).size()
     * 
     * @ensure Returns the minimum cost of any sequence of actions that can be
     *         applied to the given queues (in their current state) so that they
     *         become empty.
     */
    private static int optimalCostRecursive(List<List<Integer>> queues,
            List<Integer> front) {

        if (front.get(0)==queues.get(0).size()
                && front.get(1)==queues.get(1).size()
                && front.get(2)==queues.get(2).size()){
            return 0;
        }
        List<Integer> results = new ArrayList<>();
        List<Integer> old_front = new ArrayList<Integer>();
        for (Integer num : front) {
            old_front.add(num);
        }
        int result = 0;
        for (int i = 0; i < queues.size(); i++) {
            SequentialPairAction sequentialPairAction
                    = new SequentialPairAction(i);
            RejectAction rejectAction = new RejectAction(i);
/************************************
 **   Sequence
 ***********************************/
            front = new ArrayList<>();
            for (Integer num : old_front) {
                front.add(num);
            }
            if (sequentialPairAction.canApply(queues, old_front)) {
                int action_cost = 0;
                action_cost = sequentialPairAction.cost(queues, old_front);
                sequentialPairAction.apply(queues, front);
                result = optimalCostRecursive(queues, front) + action_cost;
                results.add(result);
            }
/************************************
 **   Reject
 ***********************************/
            front = new ArrayList<>();
            for (Integer num : old_front) {
                front.add(num);
            }
            if (rejectAction.canApply(queues, old_front)) {
                int action_cost = 0;
                action_cost = rejectAction.cost(queues, old_front);
                rejectAction.apply(queues, front);
                result = optimalCostRecursive(queues, front) + action_cost;
                results.add(result);
            }
/************************************
 **   Pair
 ***********************************/
            for (int j = i + 1; j < queues.size(); j++) {
                PairAction pairAction = new PairAction(i, j);
                front = new ArrayList<>();
                for (Integer num : old_front) {
                    front.add(num);
                }
                if (pairAction.canApply(queues, old_front)) {
                    int action_cost = 0;
                    action_cost = pairAction.cost(queues, old_front);
                    pairAction.apply(queues, front);
                    result = optimalCostRecursive(queues, front)
                            + action_cost;
                    results.add(result);
                }
            }
        }
        Collections.sort(results);
        return results.get(0);
    }

}
