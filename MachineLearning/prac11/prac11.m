data = meas(:,3);
%% data info
my_mean = 1;
my_var = 1;
data_mean = mean(data);
data_std = std(data);
%% posterior distributions
mu_N=(data_std/(150*1+data_std))*1+(150*1/(150*data_std+data_std))*data_mean;
std_N =sqrt(1/(1/1 + size(data,1)/data_std));
%% Plot Posterior
x = linspace(0,6,1000);
y = normpdf(x,mu_N,std_N);
p1 = plot(x,y);
hold on;
%% Plot Prior
x2 = linspace(0,6,1000);
y2 = normpdf(x2,1,1);
p2 = plot(x2,y2);
hold on;
%% Plot data
plot(data,0,'rx');
hold off;
legend([p1 p2],'Posterior','Prior')