%% Q1
data = importdata('heightWeight.mat');
data = data(:,2:3);
[idx,C] = kmeans(data,2,'Distance','sqeuclidean');
figure;
plot(data(idx==1,1),data(idx==1,2),'r.','MarkerSize',12);
hold on;
plot(data(idx==2,1),data(idx==2,2),'b.','MarkerSize',12);
hold on;
plot(C(:,1),C(:,2),'kx','MarkerSize',15,'LineWidth',3);

%% Q2
a = randn(200,2);
b=a+4;
c=a;
c(:,1) = 3*c(:,1);
c=c-4;
d=[a;b];
e=[a;b;c];
plot(a(:,1),a(:,2),'+');
hold on;
plot(b(:,1),b(:,2),'o');
plot(c(:,1),c(:,2),'*');
