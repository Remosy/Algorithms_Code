"""
    Enter your details below:

    Name:Yangyang Xu
    Student ID: U6325688
    Email: u6325688@anu.edu.au
"""

import util
from search_strategies import SearchNode
from frontiers import Queue


def solve(problem):
    """ Return a list of directions. See handout for more details. """

    s0 = problem.get_initial_state()
    init_node = SearchNode(state=s0,parent=None)
    queue = Queue()
    queue.push(init_node)
    explored_nodes = set([])

    while queue.is_empty() == False:
         current_node = queue.pop()
         explored_nodes.add(current_node.state)

         if problem.goal_test(current_node.state) == True:
             result = []
             result_node = current_node
             """BackTack to Root to get Action Path"""
             while result_node.parent != None:
                  result.append(result_node.action)
                  result_node = result_node.parent
             result.reverse() #From root to endNode
             return result
         else:
             options = problem.get_successors(current_node.state)
             for opt in options:
                 if opt[0] not in explored_nodes:
                     queue.push(SearchNode(state=opt[0],action=opt[1], parent=current_node))


