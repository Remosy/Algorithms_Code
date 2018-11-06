package assignment2;

import java.util.List;

/**
 * An immutable class representing the act of permitting the person currently at
 * the front of a particular queue, and the the person currently at the front of
 * a different queue, to enter the event together as a pair.
 * 
 * A pair action keeps track of the indices (in the list of queues) of the two
 * queues that the pair come from.
 * 
 * The cost of a pair action is the absolute value of the difference of the
 * ratings of the people being coupled. E.g. pairing two people with the same
 * rating costs 0, pairing one person with rating 5, and one person of rating 6
 * costs Math.abs(5-6) = 1.
 */
public final class PairAction implements Action {

    // the index of the queue that one person in the pair comes from
    private int firstIndex;
    // the index of the queue where the other person in the pair comes from
    private int secondIndex;

    /**
     * Creates a new action in which the person currently at the front of queue
     * firstIndex, and the person currently at the front of queue secondIndex,
     * are made to enter the event together as a pair.
     * 
     * @require 0 <= firstIndex < Action.NUMBER_OF_QUEUES && 0 <= secondIndex <
     *          Action.NUMBER_OF_QUEUES && firstIndex != secondIndex
     * @ensure creates a new pair action that applies to the queues with the
     *         given indices.
     **/
    public PairAction(int firstIndex, int secondIndex) {
        if (firstIndex < 0 || firstIndex >= Action.NUMBER_OF_QUEUES
                || secondIndex < 0 || secondIndex >= Action.NUMBER_OF_QUEUES
                || firstIndex == secondIndex) {
            throw new IllegalArgumentException("Invalid queue indices.");
        }
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    /**
     * Returns the index of the queue (in the list of queues) where one person
     * in the pair comes from.
     */
    public int getFirstIndex() {
        return firstIndex;
    }

    /**
     * Returns the index of the queue (in the list of queues) where the other
     * person in the pair comes from.
     */
    public int getSecondIndex() {
        return secondIndex;
    }

    /**
     * A pair action can only be applied when there is at least one person left
     * in the current state of both of the queues that it is being applied to.
     */
    @Override
    public boolean canApply(List<List<Integer>> queues, List<Integer> front) {
        return ((front.get(firstIndex) < queues.get(firstIndex).size())
                && (front.get(secondIndex) < queues.get(secondIndex).size()));
    }

    @Override
    public int cost(List<List<Integer>> queues, List<Integer> front) {
        if (!this.canApply(queues, front)) {
            throw new IllegalArgumentException(
                    "Cannot apply this action to the current state of these queues.");
        }
        int result = Math.abs(queues.get(firstIndex).get(front.get(firstIndex))
                - queues.get(secondIndex).get(front.get(secondIndex)));
        return result;
    }

    @Override
    public void apply(List<List<Integer>> queues, List<Integer> front) {
        if (!this.canApply(queues, front)) {
            throw new IllegalArgumentException(
                    "Cannot apply this action to the current state of these queues.");
        }
        front.set(firstIndex, front.get(firstIndex) + 1);
        front.set(secondIndex, front.get(secondIndex) + 1);
    }

    @Override
    public String toString() {
        return "pair from queues " + firstIndex + " and " + secondIndex;
    }
}
