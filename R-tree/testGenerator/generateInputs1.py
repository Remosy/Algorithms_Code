#!/usr/bin/python
import random
# Open a file in write mode
fo = open("data.txt", "w")

print ("Name of the file: ", fo.name)

count = 50 #1000000 #80% Vs 20%
# Write a line at the end of the file.
line = fo.write( str(count)+'\n' );

# Now read complete file from beginning.
for i in range(1,count+1):
   #line = fo.write('id_'+str(i)+' '+'x_'+str(i)+' '+'y_'+str(i)+'\n');
   line = fo.write(str(i)+' '+str(random.randint(1,100))+' '+str(random.randint(1,100))+'\n');

# Close opend file
fo.close()
