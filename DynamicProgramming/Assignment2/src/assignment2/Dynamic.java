package assignment2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dynamic {
    static Action action_map[][][];
    /**
     * Returns the minimum cost of any sequence of actions (where each action
     * may only be of type RejectAction, PairAction or SequentialPairAction)
     * that can be applied (in the order in which they appear in the action
     * sequence) to the given queues, so that all of the queues become empty.
     * (See handout for details.)
     * <p>
     * This method must be implemented using an efficient bottom-up dynamic
     * programming solution to the problem (not memoised) under the assumption
     * that Action.NUMBER_OF_QUEUES == 3.
     *
     * @require queues != null && !queues.contains(null) && queues.size() =
     * Action.NUMBER_OF_QUEUES
     * @ensure Returns the minimum cost of any sequence of actions that can be
     * applied to the given queues so that they become empty.
     */
    public static int optimalCostDynamic(List<List<Integer>> queues) {
        int q1 =queues.get(0).size() + 1;
        int q2 = queues.get(1).size() + 1;
        int q3 = queues.get(2).size() + 1;
        int[][][] results = new int[q1][q2][q3];
        List<Integer> front;
        for (int i = 0; i < queues.get(0).size() + 1; i++) {
            for (int j = 0; j < queues.get(1).size() + 1; j++) {
                for (int k = 0; k < queues.get(2).size() + 1; k++) {
                    if (i == 0 && j == 0 && k == 0) {
                        results[i][j][k] = 0;
                        continue;
                    }
                    front = new ArrayList<>(Arrays.asList(i, j, k));
                    int min_result = Integer.MAX_VALUE;
/**************************************
 ******************   Queues **********
 **************************************/
                    for (int a1 = 0; a1 < queues.size(); a1++) {
                        SequentialPairAction sequentialPairAction
                                = new SequentialPairAction(a1);
                        RejectAction rejectAction = new RejectAction(a1);
/************************************
 **   Sequence
 ***********************************/
                        List<Integer> S_front = new ArrayList<>();
                        for (Integer num : front) {
                            S_front.add(num);
                        }
                        S_front.set(a1, S_front.get(a1) - 2);
                        if (S_front.get(a1) > -1) {
                            int cost = sequentialPairAction.cost(queues, S_front);
                            int old_cost = results[S_front.get(0)][S_front.get(1)][S_front.get(2)];
                            cost += old_cost;
                            sequentialPairAction.apply(queues, S_front);
                            if (S_front.equals(front) && cost < min_result) {
                                min_result = cost;
                            }
                        }
/************************************
 **   Reject
 ***********************************/
                        List<Integer> R_front = new ArrayList<>();
                        for (Integer num : front) {
                            R_front.add(num);
                        }
                        R_front.set(a1, R_front.get(a1) - 1);
                        if (R_front.get(a1) > -1) {
                            int cost = rejectAction.cost(queues, R_front);
                            int old_cost = results[R_front.get(0)][R_front.get(1)][R_front.get(2)];
                            cost += old_cost;
                            rejectAction.apply(queues, R_front);
                            if (R_front.equals(front) && cost < min_result) {
                                min_result = cost;
                            }
                        }
/************************************
 **   Pair
 ***********************************/
                        for (int a2 = a1 + 1; a2 < queues.size(); a2++) {
                            PairAction pairAction = new PairAction(a1, a2);
                            List<Integer> P_front = new ArrayList<>();
                            for (Integer num : front) {
                                P_front.add(num);
                            }
                            P_front.set(a1, P_front.get(a1) - 1);
                            P_front.set(a2, P_front.get(a2) - 1);
                            if (P_front.get(a1) > -1 && P_front.get(a2) > -1) {
                                int cost = pairAction.cost(queues, P_front);
                                int old_cost = results[P_front.get(0)][P_front.get(1)][P_front.get(2)];
                                cost += old_cost;
                                pairAction.apply(queues, P_front);
                                if (P_front.equals(front) && cost < min_result) {
                                    min_result = cost;
                                }
                            }
                        }
                    }
/**********************************************************
 **   Store Minimum result
 **************************************************************/
                    results[i][j][k] = min_result;
                }
            }
        }
        return results[queues.get(0).size()][queues.get(1).size()][queues.get(2).size()];
    }

    /**
     * Returns a sequence of actions (where each action may only be of type
     * RejectAction, PairAction or SequentialPairAction) that can be applied (in
     * the order in which they appear in the action sequence) to the given
     * queues, so that all of the queues become empty, that has a cost less than
     * or equal to any other such sequence of actions that can be used to empty
     * the queues. (See handout for details.)
     * <p>
     * This method must be implemented using an efficient bottom-up dynamic
     * programming solution to the problem (not memoised) under the assumption
     * that Action.NUMBER_OF_QUEUES == 3.
     *
     * @require queues != null && !queues.contains(null) && queues.size() =
     * Action.NUMBER_OF_QUEUES
     * @ensure Returns an optimal sequence of actions that can be applied to the
     * given queues so that they become empty.
     */
    public static List<Action> optimalActionsDynamic(
            List<List<Integer>> queues) {
        int q1 =queues.get(0).size() + 1;
        int q2 = queues.get(1).size() + 1;
        int q3 = queues.get(2).size() + 1;
        int[][][] results = new int[q1][q2][q3];
        List<Action> result_action = new ArrayList<>();
        action_map = new Action[q1][q2][q3];
        List<Integer> front = new ArrayList<>();
        for (int i = 0; i < queues.get(0).size() + 1; i++) {
            for (int j = 0; j < queues.get(1).size() + 1; j++) {
                for (int k = 0; k < queues.get(2).size() + 1; k++) {
                    if (i == 0 && j == 0 && k == 0) {
                        results[i][j][k] = 0;
                        continue;
                    }
                    front = new ArrayList<>(Arrays.asList(i, j, k));
                    int min_result = Integer.MAX_VALUE;
                    Action tmp_action[] = new Action[1];
/***************************************************
 ******************   Queues ***********************
 ***************************************************/
                    for (int a1 = 0; a1 < queues.size(); a1++) {
                        SequentialPairAction sequentialPairAction
                                = new SequentialPairAction(a1);
                        RejectAction rejectAction = new RejectAction(a1);
/************************************
 **   Sequence
 ***********************************/
                        List<Integer> S_front = new ArrayList<>();
                        for (Integer num : front) {
                            S_front.add(num);
                        }
                        S_front.set(a1, S_front.get(a1) - 2);
                        if (S_front.get(a1) > -1) {
                            int ss1 = S_front.get(0);
                            int ss2 = S_front.get(1);
                            int ss3 = S_front.get(2);
                            int cost=
                                    sequentialPairAction.cost(queues, S_front);
                            int old_cost = results[ss1][ss2][ss3];
                            cost += old_cost;
                            sequentialPairAction.apply(queues, S_front);
                            if (S_front.equals(front) && cost < min_result) {
                                min_result = cost;
                                tmp_action[0] = sequentialPairAction;
                            }
                        }
/************************************
 **   Reject
 ***********************************/
                        List<Integer> R_front = new ArrayList<>();
                        for (Integer num : front) {
                            R_front.add(num);
                        }
                        R_front.set(a1, R_front.get(a1) - 1);
                        if (R_front.get(a1) > -1) {
                            int r1 = R_front.get(0);
                            int r2 = R_front.get(1);
                            int r3 = R_front.get(2);
                            int cost = rejectAction.cost(queues, R_front);
                            int old_cost = results[r1][r2][r3];
                            cost += old_cost;
                            rejectAction.apply(queues, R_front);
                            if (R_front.equals(front) && cost < min_result) {
                                min_result = cost;
                                tmp_action[0] = rejectAction;
                            }
                        }
/************************************
 **   Pair
 ***********************************/
                        for (int a2 = a1 + 1; a2 < queues.size(); a2++) {
                            PairAction pairAction = new PairAction(a1, a2);
                            List<Integer> P_front = new ArrayList<>();
                            for (Integer num : front) {
                                P_front.add(num);
                            }
                            P_front.set(a1, P_front.get(a1) - 1);
                            P_front.set(a2, P_front.get(a2) - 1);
                            if (P_front.get(a1) > -1 && P_front.get(a2) > -1){
                                int p1 = P_front.get(0);
                                int p2 = P_front.get(1);
                                int p3 = P_front.get(2);
                                int cost = pairAction.cost(queues, P_front);
                                int old_cost = results[p1][p2][p3];
                                cost += old_cost;
                                pairAction.apply(queues, P_front);
                                if (P_front.equals(front)&&cost < min_result){
                                    min_result = cost;
                                    tmp_action[0] = pairAction;
                                }
                            }
                        }
                    }
/**********************************************************
 **   Store Minimum result
 **************************************************************/
                    results[i][j][k] = min_result;
                    action_map[i][j][k] = tmp_action[0];
                }
            }
        }
/**********************************************************
 **   Recovery Actions
 **************************************************************/
        recoveryAction(result_action,front);
        Collections.reverse(result_action);
        return result_action;
    }

    public static void recoveryAction(List<Action> result_action,
                                      List<Integer> front) {
        Action action =
                action_map[front.get(0)][front.get(1)][front.get(2)];
        if (action==null)return;
        result_action.add(action);
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(action.toString());
        if(action instanceof SequentialPairAction){
            int move = 0;
            while (m.find()) {
                move = Integer.parseInt(m.group());
            }
            front.set(move,front.get(move)-2);
        }else if(action instanceof RejectAction){
            int move = 0;
            while (m.find()) {
                move = Integer.parseInt(m.group());
            }
            front.set(move,front.get(move)-1);
        }else{
            int[] move = new int[2];
            int pos = 0;
            while (m.find()) {
                move[pos] = Integer.parseInt(m.group());
                pos++;
            }
            front.set(move[0],front.get(move[0])-1);
            front.set(move[1],front.get(move[1])-1);
        }
        recoveryAction(result_action,front);
    }

}