"""
    Enter your details below:

    Name:Yangyang Xu
    Student ID: U6325688
    Email: u6325688@anu.edu.au
"""

import util
from search_strategies import SearchNode
from frontiers import PriorityQueue

def solve(problem, heuristic) :
    """ Return a list of directions. See handout for more details. """
    s0 = problem.get_initial_state()
    init_node = SearchNode(state=s0, parent=None,path_cost=0)
    queue = PriorityQueue()
    init_TotalCost = init_node.path_cost+heuristic(s0,problem)
    queue.push(init_node,init_TotalCost)
    cost_record = {} #Used to update total cost(g+h) of a node
    cost_record[s0]=init_TotalCost

    while not queue.is_empty():
         current_node = queue.pop()
         current_state = current_node.state

         if problem.goal_test(current_state) == True:
             result = []
             result_node = current_node
             """BackTack to Root to get Action Path"""
             while result_node.parent != None:
                 result.append(result_node.action)
                 result_node = result_node.parent
             result.reverse()  # From root to endNode
             return result
         else:
             options = problem.get_successors(current_state)
             for opt in options:
                 is_explored = False
                 node = SearchNode(state=opt[0],action=opt[1], parent=current_node, path_cost=current_node.path_cost+opt[2])
                 this_state = node.state
                 parent_node = current_node.parent
                 total_cost = node.path_cost+heuristic(node.state,problem)
                 while parent_node != None:
                     #print(is_explored)
                     if parent_node.state == this_state:
                         is_explored = True
                         break
                     else:
                         parent_node = parent_node.parent

                 if is_explored == False:
                     #queue.push(node, total_cost)
                     if this_state not in cost_record:
                         #print("not in cost state")
                         #print(this_state)
                         cost_record[this_state] = total_cost
                         queue.push(node,total_cost)
                         #print(queue)

                     # f(n) = g(n) + h(n)
                     # g(n):Path cost to so far
                     # h(n):1norm/2norm distance cost
                     # Choose the one with lowest cost to explore
                     # If a node was added to a frontier, still need to be update by lowest cost later
                     #    because, it decides when a node is explored and if a sub-optimal node is explored
                     if total_cost >= cost_record[this_state]:
                         continue
                     else:
                        #print("------------------------")
                        cost_record[this_state] = total_cost
                        #node.parent
                        #queue.change_priority(node, total_cost)
                        #got error when use queue.change_priority()
                        queue.push(node,total_cost)