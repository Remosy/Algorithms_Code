"""
    Enter your details below:

    Name:Yangyang Xu
    Student ID: U6325688
    Email:u6325688@anu.edu.au
"""

import util, sys
from search_strategies import SearchNode
from frontiers import Stack


def solve(problem) :
    depth_limit = 0
    while True:
        #print("CutOff")
        s0 = problem.get_initial_state()
        init_node = SearchNode(state=s0, parent=None, depth=0)
        queue = Stack()

        queue.push(init_node)

        while not queue.is_empty():
            current_node = queue.pop()
            #print("depth = "+str(current_node.depth))
            #print("Frontier Left = "+ str(queue.__str__()))
            if problem.goal_test(current_node.state) == True:
                result = []
                result_node = current_node
                while result_node.parent != None:
                    result.append(result_node.action)
                    result_node = result_node.parent
                result.reverse()  # From root to endNode
                return result

            if current_node.depth >= depth_limit:
                continue
            else:
                options = problem.get_successors(current_node.state)
                for opt in options:
                    state_tester = opt[0]
                    if check_parent(state_tester, current_node) == True:
                        node = SearchNode(state=state_tester, action=opt[1], parent=current_node, depth=current_node.depth+1)
                        queue.push(node)
        depth_limit += 1


def check_parent(node_state,parent):
    #print(node_state)
    while parent!= None:
        #print(parent.state)
        if parent.state == node_state:
            #print("Wrong")
            return False
        else:
            #print("UpdateWrong")
            parent = parent.parent
    #print("-----------------")
    return True