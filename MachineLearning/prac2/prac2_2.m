clear;
close all;
%% Q0
% Original graph
%a = rand(1,20);
%a = a*5;

x = linspace(0, 5);
y = 2*sin(1.5*x);

xMean = mean(x);
yMean = mean(y);
xStd = std(x);
yStd = std(y);

plot(x,y);
ax = subplot(1,1,1);
axis([0 5 -5 5]);
xticks(1:5);
yticks([-5 0 5]);
box(ax,'off');
title("(a) Function and data");
% Add training set
hold on;
Xt = rand(20,1)*5;
Xt = sort(Xt);
Yt = randn(20,1)+2*sin(1.5*Xt);
%scatter(Xt,Yt,'b','+');

for i = 1 : size(Xt,1)
   scatter(Xt(i),Yt(i),'b','+');
   %X = ['(',num2str(Xt(i)),',',num2str(Yt(i)),')'];
   %disp(X);
end

%% Q1
figure;
subplot(2,1,1);
Xt = rand(20,1)*5;
Xt = sort(Xt);
Yt = randn(20,1)+2*sin(1.5*Xt);
% Poly Degree from 1 -> 9
scatter(Xt,Yt,'+');
hold on;
sseT = zeros(9,1);%SSE for Traning
sseV = zeros(9,1);%SSE for Validation
denseInfo = zeros(size(Xt,1),9);%fit error

for j = 1:9
    fitpoly = polyfit(Xt,Yt,j);
    y1 = polyval(fitpoly,Xt);
    sseT(j) = mean((Yt-y1).^2);
    plot(Xt,y1);
end
axis([0 5 -5 5]);
xticks(0:0.5:5);
yticks([-5 0 5]);
title("(a) Data and fitted polynomials");

subplot(2,1,2);
% New validation sets
Xv = rand(20,1)*5;
Xv = sort(Xv);
Yv = 2*sin(1.5*Xv);
for k = 1:9
    fitpoly = polyfit(Xt,Yt,k);
    y2 = polyval(fitpoly,Xv);
    sseV(k) = mean((Yv-y2).^2);
end

plot((1:9),sseT,'-',(1:9),sseV,'--');
legend('Training','Validation')
title("(b) Error vs. polynomial order");