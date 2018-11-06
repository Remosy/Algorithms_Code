#!/usr/bin/python
import random
# Open a file in write mode
fo = open("query2.txt", "w")

print ("Name of the file: ", fo.name)

count = 8
# Now read complete file from beginning.
for i in range(1,count+1):
   line = fo.write(str(random.randint(1,100))+' '+str(random.randint(1,100))+'\n');

# Close opend file
fo.close()
