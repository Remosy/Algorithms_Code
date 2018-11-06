function prac4_test(x,lambda) 
%----------------Plot X START---------------
    a = randn(200,2);
b=a+4;
c=a;
c(:,1) = 3*c(:,1);
c=c-4;
d=[a;b];
e=[a;b;c];
plot(a(:,1),a(:,2),'+');
hold on
plot(b(:,1),b(:,2),'o');
plot(c(:,1),c(:,2),'*');
 %----------------Plot X END-------------------
 times = 0;
 ct = 0;
 old_Center = 0;
 this_Center = 0;
 meanlist = [];
 
    for i = 1:size(x,1)
        this_Center = x(i,:);
        times = times +1
        
        while(this_Center ~= old_Center)
            index = 0;
            s = 0;
            for j = 1:size(x,1)
                o = x(j,:);
                k = norm(o-this_Center);
                if k <= lambda
                    index = index+1;
                    s = s + o;
                end
            end
            old_Center = this_Center;
            this_Center = s ./ index; 
            if isnan(this_Center)
                break;
            end
        end
        
        if (~ismember(this_Center,meanlist))
             ct = ct+1;
             meanlist(ct,:) = this_Center;
             plot(this_Center(:,1),this_Center(:,2),'kd','MarkerSize',8,'MarkerFaceColor',[.49 1 .63])
             hold on;
        end
    end
    hold off;
end