function out = prac1_0(in,n)
   i = size(in);
   i = i(2);
   j = 0;
   out = [];
   while i > 0 && j >= 0
       j = i - n;
       if j < 0 
           j = 0;
       end
       for x = j+1:i
           out = [out,in(x)];
       end  
       i=j;    
   end
   
end
