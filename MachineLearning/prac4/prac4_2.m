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
    meanlist = [];
    ct = 0;
    times = 0;
 %----------------Plot X END-------------------

    for rand_o = 1:size(e,1)
        icenter = e(rand_o,:);
        times = times +1
        MS(e,icenter,zeros(size(icenter))); 
    end
    hold off;
 %----------------MEAN SHIFT START--------------
 %s: taget range//  x: current range// 
    
    function MS(x,so,o)
        n = size(x,1);
        if so ~= o 
            index = 0;
            s = 0;
            %s = [];
            for i = 1:n
                 xx = x(i,:);
                 k = norm(xx-so);
                 if k <= lamda
                    index = index+1;
                    s = s + xx;
                    %s(index,:) = xx;
                 end
            end
            center = s ./ index;
            %center = mean(s);
            %plot(center(:,1),center(:,2),'kd','MarkerSize',10)
            %hold on;    
            MS(x,center,so); %old center is so; 
        else   
            if (~ismember(o,meanlist))
                ct = ct+1;
                meanlist(ct,:) = o;
                %size(o)
                plot(o(:,1),o(:,2),'kd','MarkerSize',8,'MarkerFaceColor',[.49 1 .63])
                hold on;
            end
         end
    end
  %----------------MEAN SHIFT END----------------
  
end