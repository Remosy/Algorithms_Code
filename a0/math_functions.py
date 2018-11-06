""" File name:   math_functions.py
    Author:      Yangyang Xu
    Date:        23/02/2018
    Description: This file defines a set of variables and simple functions.

                 It should be implemented for Exercise 1 of Assignment 0.

                 See the assignment notes for a description of its contents.
"""
import math

ln_e = math.e

twenty_radians = math.radians(20)


def quotient_ceil(numerator, denominator):
    try:
        result = int(math.ceil(numerator/denominator))
        return result
    except ZeroDivisionError:
        print("ZeroDivisionError")


def quotient_floor(numerator, denominator):
    try:
        result = int(math.floor(numerator/denominator))
        return result
    except ZeroDivisionError:
        print("ZeroDivisionError")


def manhattan(x1, y1, x2, y2):
    return abs(x1 - x2) + abs(y1 - y2)
