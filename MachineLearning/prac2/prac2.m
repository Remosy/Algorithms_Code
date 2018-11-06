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
    deg = ['poly',num2str(j)];
    fitpoly = polyfit(Xt,Yt,j);
    y1 = polyval(fitpoly,Xt);
    [fitpoly3, gof]=fit(Xt,Yt,deg);
    sseT(j) = gof.sse;
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
    deg1 = ['poly',num2str(k)];
    [fitpoly1, gof1]=fit(Xv,Yv,deg1);
    sseV(k) = gof1.sse;
end

plot((1:9),sseT,'-',(1:9),sseV,'--');
legend('Training','Validation')
title("(b) Error vs. polynomial order");
%% Q2 & Q3
figure;
% --- Q2 ---
houseData = load('housing.data');
input = houseData(:,6);
target = houseData(:,14);

% linear Fit
sseL = 2.291e+4;

% poly Fit
sseP = zeros(5:1);
for m = 1:5
    fitpoly = polyfit(input,target,m);
    target1 = polyval(fitpoly,input);
    sseP(m) = mean((target-target1).^2);
end
plot((1:5),sseP,'k--o');

hold on;
% --- Q3 ---
half_input = houseData((1:253),6);
half2_input = houseData((254:506),6);

half_target = houseData((1:253),14);
half2_target = houseData((254:506),14);
% linear Fit
%[fitpoly3_1, gof3_1]=fit(half_input,half_target,'lowess');
sseL2 = 4965;
% poly Fit
sseP2 = zeros(5:1);
for n = 1:5
    fitpoly2 = polyfit(half_input,half_target,n);
    half_target1 = polyval(fitpoly2,half2_input);
    sseP2(n) = mean((half2_target-half_target1).^2);
end
plot((1:5),sseP2,'b--o');

if sseL>sseL2
    disp('sseL is bigger than sseL2');
else
    disp('sseL is less than sseL2');
end
    