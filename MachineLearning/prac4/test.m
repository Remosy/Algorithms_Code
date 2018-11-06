 a = randn(200,2); o b=a+4;
 c=a;
 c(:,1) = 3*c(:,1); o c=c-4;
 d=[a;b];
 e=[a;b;c];
 plot(a(:,1),a(:,2),'+'); o hold on
 plot(b(:,1),b(:,2),'o'); o plot(c(:,1),c(:,2),?*?);
 
data = d;
data_rows = size(data, 1);
lamda = 2;
while (1)
    newData = [];
    for i = 1:data_rows
        temp_num = 1;
        temp = [];
        for j = 1:data_rows
            distance = norm(data(i,:)-data(j,:));
            if distance < lamda
                temp(temp_num,:) = data(j,:);
                temp_num = temp_num+1;
            end
        end
        newData(i,:) = mean(temp);
    end
    for k = 1:data_rows
        if (norm(data(k,:)-newData(k,:)) == 0)
            return
        end
        plot([data(k,1),newData(k,1)],[data(k,2),newData(k,2)],'r'); 
    end
    if (norm(data-newData) < 0.01)
        break
    end
    data = newData;
end