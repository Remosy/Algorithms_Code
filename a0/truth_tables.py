""" File name:   truth_tables.py
    Author:      Yangyang Xu
    Date:        23/02/2018
    Description: This file defines a number of functions which implement Boolean
                 expressions.

                 It also defines a function to generate and print truth tables
                 using these functions.

                 It should be implemented for Exercise 2 of Assignment 0.

                 See the assignment notes for a description of its contents.
"""


def boolean_fn1(a, b, c):
    """ Return the truth value of (a ∨ b) → (-a ∧ -b) """
    """ Simplify the ~(a ∨ b) ∨ (~a ∧ ~b) to: ~a ∧ ~b """
    return (not a) and (not b)


def boolean_fn2(a, b, c):
    """ Return the truth value of (a ∧ b) ∨ (-a ∧ -b) """
    return a and b or not a and not b


def boolean_fn3(a, b, c):
    """ Return the truth value of ((c → a) ∧ (a ∧ -b)) ∨ (-a ∧ b) """
    return (a and (not b)) or (b and (not a))


def draw_truth_table(boolean_fn):
    """ This function prints a truth table for the given boolean function.
        It is assumed that the supplied function has three arguments.

        ((bool, bool, bool) -> bool) -> None

        If your function is working correctly, your console output should look
        like this:

        >>> from truth_tables import *
        >>> draw_truth_table(boolean_fn1)
        a     b     c     res
        -----------------------
        True  True  True  False
        True  True  False False
        True  False True  False
        True  False False False
        False True  True  False
        False True  False False
        False False True  True
        False False False True
    """
    print("a     b     c     res")
    print("-----------------------")

    a_case = [True, True, True, True, False, False, False, False]
    b_case = [True, True, False, False, True, True, False, False]
    c_case = [True, False, True, False, True, False, True, False]

    order = 0

    while order < 8:
        a = a_case[order]
        b = b_case[order]
        c = c_case[order]
        order += 1
        bools = [a,b,c]
        d_bool = boolean_fn(a,b,c)
        bools.append(d_bool)
        line = ""
        res_ct = 0
        for bb in bools:
            res_ct += 1
            if res_ct != 4 and bb == True:
                bb_t = "True "

            if res_ct == 4 and bb == True: #Format result
                bb_t = "True"

            if bb == False:
                bb_t = "False"
            line = line+bb_t+" "
        line = line[:-1]
        print(line,end="\n")
