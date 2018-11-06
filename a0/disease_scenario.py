""" File name:   disease_scenario.py
    Author:      Yangyang Xu
    Date:        23/02/2018
    Description: This file represents a scenario simulating the spread of an
                 infectious disease around Australia. It should be
                 implemented for Part 1 of Exercise 4 of Assignment 0.

                 See the lab notes for a description of its contents.
"""
import os.path


class DiseaseScenario:
    def __init__(self):
        self.threshold = 0.00             #float:
        self.growth = 0.00                #float:
        self.spread = 0.00                #float:
        self.location = ""                  #str: Start location
        self.locations = []               #[str]: All location
        self.disease = {}           #{str:float}:
        self.conn = {}         #{str:set([str])}:

    def read_scenario_file(self, path_to_scenario_file):
        try:
            """Check File Existence"""
            if os.path.exists(path_to_scenario_file) ==  False:
                return False

            file = open(path_to_scenario_file, "r")

            for xx in file:
                label = xx[0:3]

                if label == "con":
                    city1, city2 = xx[5:].strip().split(" ")
                    if city1 not in self.conn.keys():
                        self.conn[city1] = set([city2])
                    else:
                        self.conn[city1].add(city2)
                    if city2 not in self.conn.keys():
                        self.conn[city2] = set([city1])
                    else:
                        self.conn[city2].add(city1)
                elif label == "loc":
                    self.locations.append(xx[9:].strip())
                    continue
                elif label == "dis":
                    city, value= xx[7:].strip().split(" ")
                    self.disease[city] = float(value)
                    continue
                elif label == "thr":
                    self.threshold = float(xx.strip().split(" ")[1])
                    continue
                elif label == "gro":
                    self.growth = float(xx.strip().split(" ")[1])
                    continue
                elif label == "spr":
                    self.spread = float(xx.strip().split(" ")[1])
                    continue
                elif label == "sta":
                    self.location = xx.strip().split(" ")[1]
                    continue
                elif label == "dis":
                    self.start = xx.strip().split(" ")[1]
                    continue

            """Cross Check & Make Up conn{} & diseases{}"""
            for loc in self.locations:
                if loc not in self.conn.keys():
                    self.conn[loc] = set([])
                elif loc not in self.disease:
                    self.disease[loc] = float(0)
            file.close()
            return True
        except IOError:
            return True




    def valid_moves(self):
        move_loc = [loc for loc in self.conn[self.location]]
        move_loc.append(self.location)
        return move_loc

    def move(self, loc):
        if loc in self.valid_moves():
            self.location = loc
            self.disease[self.location] = 0.00
        else:
            raise ValueError("Invalid move")

    def spread_disease(self):
        old_disease = {}

        """Record the original disease before Growth & Spread"""
        for yy in self.disease.keys():
            old_disease[yy] = self.disease[yy]

        for spread_loc in self.locations:
            if spread_loc != self.location:
                """Growth"""
                current_disease = old_disease[spread_loc] * (1 + self.growth)  # Growth
                conn_list = self.conn[spread_loc]
                """Spread to connected locations"""
                for xx in conn_list:
                    conn_disease = old_disease[xx]
                    if conn_disease >= self.threshold:
                       current_disease += self.spread * conn_disease
                """Update disease"""
                self.disease[spread_loc] = current_disease