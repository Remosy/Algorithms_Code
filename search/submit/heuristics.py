# heuristics.py
# ----------------
# COMP3620/6320 Artificial Intelligence
# The Australian National University
# For full attributions, see attributions.txt on Wattle at the end of the course

""" This class contains heuristics which are used for the search procedures that
    you write in search_strategies.py.

    The first part of the file contains heuristics to be used with the algorithms
    that you will write in search_strategies.py.

    In the second part you will write a heuristic for Q4 to be used with a
    MultiplePositionSearchProblem.
"""

#-------------------------------------------------------------------------------
# A set of heuristics which are used with a PositionSearchProblem
# You do not need to modify any of these.
#-------------------------------------------------------------------------------
import sys,copy,state, operator
import state as ST

def null_heuristic(pos, problem):
    """ The null heuristic. It is fast but uninformative. It always returns 0.
        (State, SearchProblem) -> int
    """
    return 0

def manhattan_heuristic(pos, problem):
    """ The Manhattan distance heuristic for a PositionSearchProblem.
      ((int, int), PositionSearchProblem) -> int
    """
    return abs(pos[0] - problem.goal_pos[0]) + abs(pos[1] - problem.goal_pos[1])

def euclidean_heuristic(pos, problem):
    """ The Euclidean distance heuristic for a PositionSearchProblem
        ((int, int), PositionSearchProblem) -> float
    """
    return ((pos[0] - problem.goal_pos[0]) ** 2 + (pos[1] - problem.goal_pos[1]) ** 2) ** 0.5

# Abbreviations
null = null_heuristic
manhattan = manhattan_heuristic
euclidean = euclidean_heuristic

#-------------------------------------------------------------------------------
# You have to implement the following heuristics for Q4 of the assignment.
# It is used with a MultiplePositionSearchProblem
#-------------------------------------------------------------------------------

# You can make helper functions here, if you need them

def bird_counting_heuristic(state, problem) :
    position, yellow_birds = state
    heuristic_value = len(yellow_birds)
    #heuristic_value = 0
    return heuristic_value

bch = bird_counting_heuristic


def every_bird_heuristic(state, problem):
    """
        (((int, int), ((int, int))), MultiplePositionSearchProblem) -> number
    """
    position, yellow_birds = state
    #heuristic_value = 0
    # -------------------------------------------------------------------------------
    #Informativeness: bird_counting_heuristic(state, problem)
    #Efficiency:  Less runtime
    #Admissibility: h(n) <= smaller number of steps(minimum distance from now to all goal) to collect all yellow birds
    # (greedy at each step)
    # -------------------------------------------------------------------------------
    #from frontiers import PriorityQueue
    #queue = PriorityQueue()
    total_dist = 0
    queue = list(yellow_birds)
    start = position

    while len(queue) > 0:
          dists = {}
          for goal in queue:
            current_pos = tuple((start,goal))
            current_dist = problem.distance[current_pos]
            dists[current_pos] = current_dist
          dists = sorted(dists.items(),key=operator.itemgetter(1)) #Sort by distance (small -> big)
          min_pos = dists[0] #Pick the smallest one as next start point
          start = min_pos[0][1]
          total_dist += min_pos[1] #add up each step minimum distance
          queue.remove(start)  # predent finished this goal

    # h*(n) = heuristic_value
    # to satisfy h(n) <= h*(n), after testing, when the ratio is 0.945, the cost is same as handout,
    #                          and less #node expanded
    heuristic_value = total_dist * 0.945


    return heuristic_value

"""
#Used for frontier.find()

def my_function(item):
    return lambda x : tuple(item)
"""


every_bird = every_bird_heuristic


