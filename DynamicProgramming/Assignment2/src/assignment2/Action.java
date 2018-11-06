package assignment2;

import java.util.List;

/**
 * An interface describing an action that can be applied to the current state of
 * the queues of people waiting to get into a gig.
 * 
 * There are Action.NUMBER_OF_QUEUES queues, each identified by an index 0 to
 * Action.NUMBER_OF_QUEUES - 1, inclusive.
 */
public interface Action {

    int NUMBER_OF_QUEUES = 3;

    /**
     * Returns true if an only if this action can be applied to the current
     * state of the given queues.
     * 
     * For each i in the indices of queues, the ith queue is defined to
     * currently contain the elements in queues.get(i) from index front.get(i)
     * to the end of queues.get(i).
     * 
     * @require queues != null && !queues.contains(null) && front !=null &&
     *          !front.contains(null) && queues.size() = front.size() =
     *          NUMBER_OF_QUEUES && for all indices i in queues, 0 <=
     *          front.get(i) <= queues.get(i).size()
     * 
     * @ensure Returns true when this action can be applied to the current state
     *         of the given queues.
     */
    boolean canApply(List<List<Integer>> queues, List<Integer> front);

    /**
     * Returns the cost of this action when applied to the current state of the
     * given queues.
     * 
     * For each i in the indices of queues, the ith queue is defined to
     * currently contain the elements in queues.get(i) from index front.get(i)
     * to the end of queues.get(i).
     * 
     * @require queues != null && !queues.contains(null) && front !=null &&
     *          !front.contains(null) && queues.size() = front.size() =
     *          NUMBER_OF_QUEUES && for all indices i in queues, 0 <=
     *          front.get(i) <= queues.get(i).size() && this.canApply(queues,
     *          front) is true
     * 
     * @ensure This method returns the cost of this action when applied to the
     *         current state of the given queues.
     */
    int cost(List<List<Integer>> queues, List<Integer> front);

    /**
     * Updates the current state of the given queues, to how they would be after
     * this action has been applied.
     * 
     * Parameter queues is not modified at all by this method, but the indices
     * in parameter front may be updated to reflect the removal of people from
     * the queues caused by this action.
     * 
     * For each i in the indices of queues, the ith queue is defined to
     * currently contain the elements in queues.get(i) from index front.get(i)
     * to the end of queues.get(i).
     * 
     * @require queues != null && !queues.contains(null) && front !=null &&
     *          !front.contains(null) && queues.size() = front.size() =
     *          NUMBER_OF_QUEUES && for all indices i in queues, 0 <=
     *          front.get(i) <= queues.get(i).size() && this.canApply(queues,
     *          front) is true
     * 
     * @ensure Updates the current state of the given queues, to how they would
     *         be after this action has been applied.
     */
    void apply(List<List<Integer>> queues, List<Integer> front);

}
