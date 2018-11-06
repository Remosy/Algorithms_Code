
function out = prac1_1(in,n)
    out = [];
    temp = [];
    p = size(in);
    p = p(2);
    form1 = fliplr(in);
    ct=0;
    
    for x = 1:p
        temp = [temp,form1(x)];
        ct = ct+1;
        if ct == n || x == p
          out = [out,fliplr(temp)];
          ct = 0;
          temp = [];
        end
    end
        
end