""" File name:   health_agents.py
    Author:      Yangyang   Xu
    Date:        23/02/2018
    Description: This file contains agents which fight disease. It is used
                 in Exercise 4 of Assignment 0.
"""

import random
import disease_scenario as DisScn
import copy

class HealthAgent:
    """ A simple disease fighting agent. """

    def __init__(self, locations, conn):
        """ This contructor does nothing except save the locations and conn.
            Feel free to overwrite it when you extend this class if you want
            to do some initial computation.

            (HealthAgent, [str], { str : set([str]) }) -> None
        """
        self.locations = locations
        self.conn = conn

    def choose_move(self, location, valid_moves, disease, threshold, growth, spread):
        """ Using given information, return a valid move from valid_moves.
            Returning an inalid move will cause the system to stop.

            Changing any of the mutable parameters will have no effect on the operation
            of the system.

            This agent will locally move to the highest disease, of there is
            is no nearby disease, it will act randomly.

            (HealthAgent, str, [str], [str], { str : float }, float, float, float) -> str
        """
        max_disease = None
        max_move = None
        for move in valid_moves:
            if max_disease is None or disease[move] > max_disease:
                max_disease = disease[move]
                max_move = move

        if not max_disease:
            return random.choice(valid_moves)

        return max_move


# Make a new agent here called SmartHealthAgent, which extends HealthAgent and
# acts a bit more sensibly. Feel free to add other helper functions if needed.

class SmartHealthAgent(HealthAgent):
    def __init__(self, locations, conn):
        super().__init__(locations, conn)

    def choose_move(self, location, valid_moves, disease, threshold, growth, spread):

        """

        :param location:
        :param valid_moves:
        :param disease:
        :param threshold:
        :param growth:
        :param spread:
        :return:
            result of 5 cases:
            (1)It's not emergent, but can step ahead to see the most correct step that leads least disease in the end
            (2)When agent's at 0-disease location, but from (1) it suggested to stay, which is noneffective,
               also adjacent locations are in dangerous. ==> Go to a location with the largest disease
            (3)When agent's at 0-disease location, but agent's adjacent locations have 0 disease,
                it's not emergent. ==> use (1)
            (4)When agent's at 0-disease location, but from (1) it suggested another 0-disease location,
              also adjacent locations are 0-disease. To correct the move by choosing the location that connected with the
              most-disease location, as expect, after this round, the connected location will be healed on time

            For the SmartHealAgent, it handles more specific cases by applying more info. of the locations, it avoids to
            take more steps in random mode.
        """
        is_allZero = True       #Adjacent locations are 0-disease
        move_correction = None  #Move Correction of Case 4
        disease_correction = 0
        max_disease = None
        max_move = None

        simulator = DisScn.DiseaseScenario()
        simulator.location = location
        simulator.disease = disease
        simulator.threshold = threshold
        simulator.growth = growth
        simulator.spread = spread
        simulator.locations = self.locations
        simulator.conn = self.conn

        """Result of CASE 1 & CASE 4 & CASE 5"""
        for move in valid_moves:
            dis_move = disease[move]
            dis_sum = sum([disease[jj] for jj in self.conn[move]])
            if dis_move > 0:
                is_allZero = False
            if (move_correction is None or disease_correction < dis_sum)\
                and move != location:
                disease_correction = dis_sum
                move_correction = move
            if max_disease is None or dis_move > max_disease:
                max_disease = dis_move
                max_move = move

        """Result of CASE 2 & 3 """
        possible_cases = {}
        for xx in valid_moves:
            simulator1 = copy.deepcopy(simulator)
            simulator1.move(xx)
            simulator1.spread_disease()
            #possible_cases[xx] = sum(simulator1.disease.values())
            possible_cases[xx] = self.recusive_run(simulator1, sum(simulator.disease.values()))
        blacklist = sorted(valid_moves, key=possible_cases.get)

        """Classify CASE 1"""
        final_move = blacklist[0]

        """Classify CASE 2"""
        if disease[location] == 0 and blacklist[0] == location and is_allZero == False:
            final_move = max_move

        """Classify CASE 3"""
        if disease[location] == 0  \
            and blacklist[0] != location \
            and is_allZero == True:
            final_move = blacklist[0]

        """Classify CASE 4"""
        if disease[location] == 0 \
                and blacklist[0] != location \
                and disease[blacklist[0]] == 0 \
                and is_allZero == True:
                final_move = move_correction


        """Classify CASE 5"""
        if disease[location] == 0 \
                and blacklist[0] != location \
                and is_allZero == False \
                and disease[blacklist[0]] == 0 :
            final_move = max_move


        return final_move

    def recusive_run(self,the_simulator,last_disease):
        possible_cases= {}
        list = []
        list = the_simulator.valid_moves()

        for xx in list:
            simulator1 = copy.deepcopy(the_simulator)
            simulator1.move(xx)
            simulator1.spread_disease()
            possible_cases[xx] =  sum(simulator1.disease.values())


        the_simulator.location = sorted(list, key=possible_cases.get)[0]
        total_dis = last_disease + possible_cases[the_simulator.location]
        if last_disease <= total_dis:
            return possible_cases[the_simulator.location]
        else:
            self.recusive_run(the_simulator,total_dis)

