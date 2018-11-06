""" File name:   dfa.py
    Author:      Yangyang Xu
    Date:        23/03/2018
    Description: This file defines a function which reads in
                 a DFA described in a file and builds an appropriate datastructure.

                 There is also another function which takes this DFA and a word
                 and returns if the word is accepted by the DFA.

                 It should be implemented for Exercise 3 of Assignment 0.

                 See the assignment notes for a description of its contents.
"""
def load_dfa(path_to_dfa_file):
    """ This function reads the DFA in the specified file and returns a
        data structure representing it. It is up to you to choose an appropriate
        data structure. The returned DFA will be used by your accepts_word
        function. Consider using a tuple to hold the parts of your DFA, one of which
        might be a dictionary containing the edges.

        We suggest that you return a tuple containing the names of the start
        and accepting states, and a dictionary which represents the edges in
        the DFA.

        (str) -> Object
    """
    initial_State = ""
    accept_States = []
    graph = {}

    file = open(path_to_dfa_file, "r")
    ct = 0
    for xx in file:
        if ct == 0:
            initial_State = xx.strip().split(" ")[1]
            ct += 1
            continue
        elif ct == 1:
            accept_States = [x for x in xx[10:].strip().split(" ")]
            ct += 1
            continue
        else:
            info = xx.strip().split(" ")
            node1, node2, edge_info = info[1],info[2],info[3]
            if node1 not in graph.keys():
                graph[node1] = [(node2, edge_info)]
                continue
            else:
                graph[node1].append((node2, edge_info))
    file.close()
    return (initial_State,accept_States,graph)


def accepts_word(dfa, word):
    """ This function takes in a DFA (that is produced by your load_dfa function)
        and then returns True if the DFA accepts the given word, and False
        otherwise.

        (Object, str) -> bool
    """

    state = dfa[0]  #initial State

    for ii in range(0, len(word)+1):

        if (ii == len(word)) and (state in dfa[1]):
            return True
        elif ii == len(word) and (state not in dfa[1]):
            return False
        elif state in dfa[2].keys():
            edge_list = dfa[2][state]
            for jj in range(0, len(edge_list)):
                if word[ii] == edge_list[jj][1]:
                   state = edge_list[jj][0]     #Transfer State
                   break
        else:
            return False

