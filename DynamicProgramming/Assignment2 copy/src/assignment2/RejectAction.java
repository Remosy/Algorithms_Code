package assignment2;

import java.util.List;

/**
 * An immutable class representing the act of rejecting the person currently at
 * the front of a particular queue (i.e. denying them access to the gig and
 * removing them from that queue).
 * 
 * A reject action has the index of the queue (in the list of queues) that the
 * action is applied to.
 * 
 * The cost of a reject action is RejectAction.REJECT_COST.
 */
public final class RejectAction implements Action {

    // the cost of a reject action
    public final static int REJECT_COST = 5;

    // the index of the queue that this reject action is applied to
    private int index;

    /**
     * Creates a new reject action for the queue with the given index.
     * 
     * @require 0 <= index < Action.NUMBER_OF_QUEUES
     * @ensure creates a new reject action with the given index
     **/
    public RejectAction(int index) {
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
     * A RejectAction on a particular queue, can only be applied if there is at
     * least one person left in the current state of that queue.
     */
    @Override
    public boolean canApply(List<List<Integer>> queues, List<Integer> front) {
        return (front.get(index) < queues.get(index).size());
    }

    @Override
    public int cost(List<List<Integer>> queues, List<Integer> front) {
        if (!this.canApply(queues, front)) {
            throw new IllegalArgumentException(
                    "Cannot apply this action to the current state of these queues.");
        }
        return REJECT_COST;
    }

    /**
     * Applies this reject action, by removing the person in the queue that it
     * is applied to, and denying them entry to the gig.
     */
    @Override
    public void apply(List<List<Integer>> queues, List<Integer> front) {
        if (!this.canApply(queues, front)) {
            throw new IllegalArgumentException(
                    "Cannot apply this action to the current state of these queues.");
        }
        front.set(index, front.get(index) + 1);
    }

    @Override
    public String toString() {
        return "reject from queue " + index;
    }

}
