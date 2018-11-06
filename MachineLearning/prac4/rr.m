function prac4_2(X,lamda)
 
 %----------------Plot X START---------------
    a = randn(200,2);
    b=a+4;
    c=a;
    c(:,1) = 3*c(:,1);
    c=c-4;
    d=[b;c];
    e=[a;b;c];
    plot(a(:,1),a(:,2),'+');
    hold on;
    plot(b(:,1),b(:,2),'o');
    hold on;
    plot(c(:,1),c(:,2),'*');
    hold on;
 %----------------Plot X END-------------------
    %MS(a,zeros(size(a)),zeros(size(a,2))); %
    %MS(b,zeros(size(b)),zeros(size(b,2))); %
    %MS(c,zeros(size(c)),zeros(size(c,2))); %
    %MS(d,zeros(size(d)),zeros(size(d,2))); %
    MS(e,zeros(size(e)),zeros(size(e,2))); %
 %----------------MEAN SHIFT START--------------
 %s: taget range//  x: current range// 
    function MS(x,s,center)
        n = size(x,1);
        if s ~= x %compare current Vs target
            for i = 1:n
                for j = 1:n
                    xx = x(i,:);
                    ss = x(j,:);
                    k = norm(ss-xx);
                    if k <= lamda
                       s(j,:) = ss;
                    end
                end
                center(i,:) = mean(s);
                
                %plot(center(i,1),center(i,2),'kd','MarkerSize',12)
                %hold on;
            end     
            MS(s,x,center); %new x is s; 
        else
           plot(center(end,1),center(end,2),'kd','MarkerSize',12,'MarkerFaceColor',[.49 1 .63])
        end
    end
  %----------------MEAN SHIFT END----------------
  
end