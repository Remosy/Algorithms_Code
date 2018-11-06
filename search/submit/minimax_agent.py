# minimax_agent.py
# --------------
# COMP3620/6320 Artificial Intelligence
# The Australian National University
# For full attributions, see attributions.txt on Wattle at the end of the course

"""
    Enter your details below:

    Name: Yangyang Xu
    Student ID: U6325688
    Email: u6325688@anu.edu
"""

from agents import Agent
import util, sys,operator
from search_strategies import SearchNode
from actions import Directions

from search_problems import AdversarialSearchProblem

class MinimaxAgent(Agent):
    """ The agent you will implement to compete with the black bird to try and
        save as many yellow birds as possible. """

    def __init__(self, max_player, depth="2"):
        """ Make a new Adversarial agent with the optional depth argument.
            (MinimaxAgent, str) -> None
        """
        self.max_player = max_player
        self.depth = int(depth)


    def evaluation(self, problem, state):
        """
            (MinimaxAgent, AdversarialSearchProblem,
                (int, (int, int), (int, int), ((int, int)), number, number))
                    -> number
        """
        player, red_pos, black_pos, yellow_birds, score, yb_score = state

        # -------------------------------------------------------------------------------
        # To satisfy: return maximum score the red agent can attain from the given state
        # Features:
        #   score
        #   Reach goal faster than black agent. red_pos-yb_pos < black_pos-yb_pos
        #   The distance betwwen Black Agent and Red Agent
        # -------------------------------------------------------------------------------

        red_dist = problem.maze_distance(red_pos, black_pos)
        black_dist = problem.maze_distance(red_pos, black_pos)
        red_score = 0
        black_score = 0
        btw_Reb_black = problem.maze_distance(red_pos, black_pos)

        if problem.terminal_test(state):
            return score

        for goal in yellow_birds:
            temp_red = problem.maze_distance(red_pos, goal)
            temp_black = problem.maze_distance(black_pos, goal)

            #---------------------------------------------------------
            # Assume once the agents close to a goal, the goal can be eaten
            #---------------------------------------------------------
            if temp_red < temp_black:
                red_score += yb_score

            elif temp_black < temp_red:
                black_score += yb_score

            # ---------------------------------------------------------
            # Find the smallest distance to next goal for both agents
            # ---------------------------------------------------------
            if temp_red < red_dist:
                red_dist = temp_red
                red_score -= red_dist
            elif temp_black < black_dist:
                black_dist = temp_black
                black_score -=black_dist

        #-------------------------------------------------------------------
        # score = total_value_of_yellow_birds_captured_by_red_agent
        #           - total_value_of_yellow_birds_captured_by_black_agent + capture_valu
        #
        # capture_valu = score - total_value_of_yellow_birds_captured_by_red_agent
        #           + total_value_of_yellow_birds_captured_by_black_agent
        # higher score can increse the score in dense maze, btw_Red_black can improve sensitivity of evaluation slightly
        #--------------------------------------------------------------------
        evaluation = 10*score - black_score + red_score + btw_Reb_black

        return evaluation


    def maximize(self, problem, state, current_depth, alpha=float('-inf'), beta=float('inf')):
        """ This method should return a pair (max_utility, max_action).
            The alpha and beta parameters can be ignored if you are
            implementing minimax without alpha-beta pruning.

             (MinimaxAgent, AdversarialSearchProblem,
                 (int, (int, int), (int, int), ((int, int)), number, number)
                     -> (number, str)
        """

        #util.raise_not_defined() # Remove this line once you finished your implementation
        if current_depth == self.depth:
            return int(self.evaluation(problem,state)), Directions.STOP
            #return int(problem.utility(state)), Directions.STOP
        if problem.terminal_test(state):
            return int(problem.utility(state)), Directions.STOP
        result_action = ""
        result_value = -sys.maxsize

       # current_node = SearchNode(state=state,depth=current_depth)
        options = problem.get_successors(state)
        new_depth = current_depth+1

        for opt in options:
            rival_value = self.minimize(problem, opt[0],new_depth, alpha, beta)
            if rival_value > result_value:
                result_action = opt[1]
                result_value = rival_value

            """alpha–beta pruning"""
            if result_value >= beta:
                return result_value, result_action
            alpha = max(alpha, result_value)

        return (result_value,result_action)


    def minimize(self, problem, state, current_depth, alpha=float('-inf'), beta=float('inf')):
        """ This function should just return the minimum utility.
            The alpha and beta parameters can be ignored if you are
            implementing minimax without alpha-beta pruning.

            (MinimaxAgent, AdversarialSearchProblem,
                 (int, (int, int), (int, int), ((int, int)), number, number)
                     -> number
        """

        if current_depth == self.depth:
            return int(self.evaluation(problem, state))
            #return int(problem.utility(state)), Directions.STOP
        if problem.terminal_test(state):
            return int(problem.utility(state))
        result_value = sys.maxsize

        #current_node = SearchNode(state=state,depth=current_depth)
        options = problem.get_successors(state)
        next_depth = current_depth + 1
        for opt in options:
            rival_value = self.maximize(problem,opt[0],next_depth,alpha, beta)[0] #Cuz it returns a tuple
            if rival_value < result_value:
               result_value = rival_value

            """alpha–beta pruning"""
            if result_value <= alpha:
                return result_value
            beta = min(beta, result_value)

        return result_value


    def get_action(self, game_state):
        """ This method is called by the system to solicit an action from
            MinimaxAgent. It is passed in a State object.

            Like with all of the other search problems, we have abstracted
            away the details of the game state by producing a SearchProblem.
            You will use the states of this AdversarialSearchProblem to
            implement your minimax procedure. The details you need to know
            are explained at the top of this file.
        """
        # We tell the search problem what the current state is and which player
        # is the maximizing player (i.e. who's turn it is now).
        problem = AdversarialSearchProblem(game_state, self.max_player)
        state = problem.get_initial_state()
        utility, max_action = self.maximize(problem, state, 0)
        print("At Root: Utility:", utility, "Action:", max_action, "Expanded:", problem._expanded)
        return max_action

    def calculateHeuristic(self, pos, problem, queue):
        total_dist = 0
        start = pos

        while len(queue) > 0:
            dists = {}
            for goal in queue:
                current_pos = tuple((start, goal))
                current_dist = problem.distance[current_pos]
                dists[current_pos] = current_dist
            dists = sorted(dists.items(), key=operator.itemgetter(1))  # Sort by distance (small -> big)
            min_pos = dists[0]  # Pick the smallest one as next start point
            start = min_pos[0][1]
            total_dist += min_pos[1]  # add up each step minimum distance
            queue.remove(start)  # predent finished this goal

        # h*(n) = heuristic_value
        # to satisfy h(n) <= h*(n), after testing, when the ratio is 0.945, the cost is same as handout,
        #                          and less #node expanded
        return total_dist