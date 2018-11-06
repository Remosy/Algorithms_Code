%
%    Enter your details below:
%
%    Name: Yangyang Xu  
%    Student ID: u6325688
%    Email: u6325688@anu.edu.au
%

int: C;					  % Number of customers
int: T;                     		  % Number of trucks
int: G;                     		  % Number of goods types 
int: MAXCAP;                		  % Maximum truck capacity

set of int: trucks = 1..T;    	     	  % Set of trucks
set of int: customers = 0..C;        	  % Set of customers. Includes depot as customer 0
set of int: goods = 1..G;            	  % Set of goods types
int: chilled = 1;  int: ambient = 2; 	  % Good types
set of int: times = 0..C+1;            	  % Enough time steps to visit each customer once and depot twice if needed

array[trucks] of int: cap;	     	  % Capacity of trucks
array[trucks] of bool: refrig;	     	  % Whether or not trucks are refrigerated
array[goods,customers] of int: order;	  % Number of units of goods types ordered by customers
array[trucks] of int: centsPerKm;         % Cost of running trucks (in cents per km)
array[customers,customers] of int: D;  	  % Distances between customers (including the depot)


var int: tot_cost;	      	   	  % Total cost of the solution


% Insert your other variables and constraints here
set of int: result_times = 1..C+1; 
array[trucks] of var result_times: plan;
array[trucks, times]of var int: schedule;
array[customers, trucks, goods] of var int: result;

    % Truck: xx    Customer: yy    Goods: zz    Time: tt
    %[1]: Results satisfy customers' orders
    %[2]: Each truck No more than cap[truck]
    %[3]: Chilled food --> Refrigerator
    %[4]: Keep goods entries with positive values
    constraint forall (yy in customers)(
               sum (xx in trucks) (result[yy,xx,chilled])=order[chilled,yy] /\
               sum (xx in trucks) (result[yy,xx,ambient])=order[ambient,yy]
               );  
                            
    constraint forall (xx in trucks)(
	             sum (yy in customers, zz in goods) (result[yy,xx,zz]) <= cap[xx]
	             );
    
    constraint forall(xx in trucks, yy in customers)(
               if refrig[xx] = false 
               then result[yy,xx, chilled] = 0 
               else true endif
               ); 
  
    constraint forall (xx in trucks, yy in customers)(
               result[yy,xx,chilled] >= 0 /\
               result[yy,xx,ambient] >= 0 
               );   
    %----------------Time Schedule--------------
    %[1]: Link schedule with orders(Q1) by customer
    %[2]: By using "knapsak" to generate the customer limitation of each truck 
    %[3]: Change the schedule value within the limitation
    %[4]: Each truck --> distinct customer in schedule[]
   constraint forall(xx in trucks, yy in customers, tt in times)(
              if result[yy,xx,chilled] < 1 /\ 
                 result[yy,xx,ambient] < 1 /\ 
                 yy >= 1
              then schedule[xx, tt] != yy
              else true endif
              );
   
   constraint forall(xx in trucks)(
               plan[xx] = sum(yy in customers,zz in goods)(bool2int(result[yy,xx,zz]>0))
              );

   constraint forall(xx in trucks, tt in times)(
              if tt <= plan[xx] /\ tt != 0
              then schedule[xx, tt] > 0  /\ 
                   schedule[xx, tt] <= C 
              else schedule[xx, tt] = 0  
              endif
              );  
              
    constraint forall(xx in trucks, tt1, tt2 in times where tt1 != tt2)(
               if schedule[xx, tt1]> 0 /\ 
                  schedule[xx, tt2]> 0
               then schedule[xx,tt1] != schedule[xx,tt2]
               else true endif
              );
    
 %----------------Minimise Total Cost--------------
    %[1]: Calculate the total cost for each schedule:
    tot_cost = sum(xx in trucks, tt in result_times )(if schedule[xx, tt]>=0  then centsPerKm[xx] * D[schedule[xx, tt], schedule[xx, tt-1]] else 0 endif);
   
               

% In question Q3, we are optimising the total cost
solve minimize tot_cost;



% Write a Minizinc output item to print the solution in the desired format for Q3
output[show(T) ++ "," ++ show(C) ++ "," ++ show(tot_cost/100)++ "\n"] ++
      [if (fix(schedule[xx,tt])>=0)
      then show(xx)++","++ show(tt)++","++ show(schedule[xx, tt])++","++ show(result[schedule[xx, tt],xx,chilled])++","++show(result[schedule[xx, tt],xx,ambient]) ++
      "\n" else "" endif |
      xx in trucks, tt in result_times
];