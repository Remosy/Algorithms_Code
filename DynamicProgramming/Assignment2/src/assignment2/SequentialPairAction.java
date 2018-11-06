package assignment2;

import java.util.List;

/**
 * An immutable class representing the act of pairing the two people currently
 * at the front of a particular queue, and permitting them entry to the gig.
 * 
 * A sequential pair action keeps track of the index of the queue (in the list
 * of queues) where the pair come from.
 * 
 * The cost of a sequential pair action is SequentialPair.EXCESS_SEQUENTIAL plus
 * the absolute value of the difference of the ratings of the people being
 * coupled.
 */
public final class SequentialPairAction implements Action {

    // the index of the queue that the pair come from
    private int index;

    // the excess cost applied when a couple is paired from the same queue (in
    // addition to the cost of the absolute value of the difference of the
    // rating of the people being coupled).
    public final static int EXCESS_SEQUENTIAL = 1;

    /**
     * Creates a new sequential pair action for the queue with the given index.
     * 
     * @require 0 <= index < Action.NUMBER_OF_QUEUES
     * @ensure creates a new sequential pair action with the given index.
     **/
    public SequentialPairAction(int index) {
        if (index < 0 || index >= Action.NUMBER_OF_QUEUES) {
            throw new IllegalArgumentException("Invalid queue index");
        }
        this.index = index;
    }

    /**
     * Returns the index of the queue (in the list of queues) that this action
     * applies to.
     **/
    public int getIndex() {
        return index;
    }

    /**
     * A sequential pair action can only be applied when there are at least two
     * people left in the current state of the queue that it is being applied
     * to.
     */
    @Override
    public boolean canApply(List<List<Integer>> queues, List<Integer> front) {
        // return true if there is are at least two people currently in the
        // queue with index index.
        return (front.get(index) + 1 < queues.get(index).size());
    }

    @Override
    public int cost(List<List<Integer>> queues, List<Integer> front) {
        if (!this.canApply(queues, front)) {
            throw new IllegalArgumentException(
                    "Cannot apply this action to the current state of these queues.");
        }
        int result = EXCESS_SEQUENTIAL
                + Math.abs(queues.get(index).get(front.get(index))
                        - queues.get(index).get(front.get(index) + 1));
        return result;
    }

    @Override
    public void apply(List<List<Integer>> queues, List<Integer> front) {
        if (!this.canApply(queues, front)) {
            throw new IllegalArgumentException(
                    "Cannot apply this action to the current state of these queues.");
        }
        front.set(index, front.get(index) + 2);
    }

    @Override
    public String toString() {
        return "sequentially pair from queue " + index;
    }

}
