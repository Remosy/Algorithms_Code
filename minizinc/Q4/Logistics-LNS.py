""" File name:   Logistics-LNS.py
    Author:      Yangyang Xu
    Date:        3 May 2018
    Description: This file should implement a Large Neighborhood Search (LNS)
                 for the Logistics Problem for Q4 of Assignment 2.
                 See the assignment notes for a description of its contents.
"""




import argparse
from subprocess import Popen, PIPE, STDOUT
import numpy as np
import random

# Complete the file with your LNS solution

def getSolution(file_name):
   ##----------------------------------------
   #  OPEN FILE: start-solution-x-x.csv
   # -----------------------------------------
   f = open(file_name)
   line1 = f.readline().split(",")
   num_trucks = line1[0]
   num_customer = line1[1].strip(' ')
   start_cost = line1[2].strip(' \n')
   solution_list = dict() #save customer and truck
   for xx in f.readlines()[1:]:
       line = xx.split(",")
       _truck = int(line[0].strip(' '))
       _customer = int(line[2].strip(' '))
       if _truck in solution_list.keys():
          solution_list[_truck].add(_customer)
       else:
          solution_list[_truck] = set([_customer])
   print(solution_list)
   f.close()

   return [num_trucks, num_customer, start_cost], solution_list

def outPutData(new_keep):
   ##----------------------------------------
   #  WRITE FILE: kepp.dzn
   ## -----------------------------------------
   f = open("keep.dzn","w")
   f.write("schedule = array2d(trucks, times, [")
   context = str(new_keep).strip()
   context = context.replace("  "," ")
   context = context.replace('[', '')
   context = context.replace(']', '')
   context = context.replace("'", '')
   context = context.replace("-1", "_")
   context = context.split(" ")
   list = []
   for c in context:
      c = c.strip("\n")
      if c != '':
         list.append(c)

   list = str(list)
   list = list.replace('[', '')
   list = list.replace(']', '')
   list = list.replace("'", '')
   f.write(list)
   f.write("]);")
   f.close()

def repair_desrtoyData(num_trucks,num_customer,solution_list):
   new_keep = np.zeros(shape=[num_trucks, num_customer+2])
   new_keep = new_keep.astype(int)
   print(new_keep.shape)

   #Destroy partial values
   for xx in range(num_trucks):
       for yy in range(num_customer+2):
           new_keep[xx, yy] = -1
           if xx > num_customer+2: #Reduce truck cell when the No. trucks > No. customers
               new_keep[xx,yy] = -1
           if random.randint(0,10) == 3: #Randomly choose
            new_keep[xx,yy] = -1

   # Asign solution value to schedule
   for truck in solution_list.keys():
       customer_set = solution_list.get(truck)
       for customer in customer_set:
           print("--" + str(truck) + "," + str(customer) + "--")
           new_keep[truck - 1][customer] = customer



   return new_keep



if __name__ =='__main__':
   parser = argparse.ArgumentParser()
   parser.add_argument('problem_filename', help='problem file')
   parser.add_argument('start_solution_filename', help='file describing the solution to improve')
   args = parser.parse_args()
   start_solution_filename = args.start_solution_filename
   problem_filename = args.problem_filename
   namelist = str(start_solution_filename).replace(".csv","").split("-")
   best_solution_filename = 'best-solution-' + namelist[2] + '-' + namelist[3] + '.csv'

   epoch = 0
   while epoch < 5:
      the_solution_file = ""
      if epoch == 0:
         the_solution_file = start_solution_filename
      else:
         the_solution_file = best_solution_filename
      print("Epoch = "+str(epoch)+": "+the_solution_file)

      indicator, start_solution = getSolution(the_solution_file)
      new_keep = repair_desrtoyData(int(indicator[0]),int(indicator[1]), start_solution)
      outPutData(new_keep)


      terminal_input = "minizinc Logistics-Q4.mzn"\
                       +problem_filename\
                       +" keep.dzn --soln-sep \"\" --search-complete-msg \"\" > " \
                       + best_solution_filename
      terminal = Popen(terminal_input, shell=True, stdin=PIPE, stdout=PIPE, stderr=STDOUT)
      terminal_output = terminal.stdout.read()

      epoch += 1