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

array[trucks] of int: cap;	     	  % Capacity of trucks
array[trucks] of bool: refrig;	     	  % Whether or not trucks are refrigerated
array[goods,customers] of int: order;	  % Number of units of goods types ordered by customers




% Insert your variables and constraints here
array[trucks, customers, goods] of var int: result;
    
    % Truck: xx    Customer: yy    Goods: zz
    %[1]: Results satisfy customers' orders
    %[2]: Each truck No more than cap[truck]
    %[3]: Chilled food --> Refrigerator
    %[4]: Keep the entries with positive values
    constraint forall (yy in customers)(
               sum (xx in trucks) (result[xx,yy,chilled])=order[chilled,yy] /\
               sum (xx in trucks) (result[xx,yy,ambient])=order[ambient,yy]
               );  
                            
    constraint forall (xx in trucks)(
	             sum (yy in customers, zz in goods) (result[xx,yy,zz]) <= cap[xx]
	             );
    
    constraint forall(xx in trucks, yy in customers)(
               if refrig[xx] = false then result[xx, yy, chilled] = 0 
               else true endif
               ); 
  
    constraint forall (xx in trucks, yy in customers)(
               result[xx,yy,chilled] >= 0 /\
               result[xx,yy,ambient] >= 0
               );         

% In question Q1, we are only finding a satisfying solution
solve satisfy;


% Write a Minizinc output item to print the solution in the desired format for Q1
output[show(T) ++ "," ++ show(C) ++ "\n"] ++
      [if (fix(result[xx,yy,chilled]) != 0 \/ fix(result[xx,yy,ambient]) != 0) then
          show(xx)++","++ show(yy)++","++ show(result[xx,yy,chilled])++","++show(result[xx,yy,ambient]) ++
          "\n" else "" endif |
      xx in trucks, yy in customers
];
   