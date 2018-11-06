%% Q3
x = [1 2 3 4];
correct_rate1 = [77.4648 85.9155 90.1408 90.1408];
lables1 = {'77.4648%', '85.9155%', '90.1408%', '90.1408%'};
plot(x,correct_rate,'o-');
title('Performance On Test Set')
xlabel('C') % x-axis label
ylabel('percentage correct (%)') % y-axis label
text(x,correct_rate1,lables1,'FontSize',12);
%% Q4
correct_rate2 = [82.2115 84.6154 82.2115 82.2115];
lables2 = {'82.2115%' '84.6154%' '82.2115%' '82.2115%'};
hold on;
plot(x,correct_rate2,'o-');
text(x,correct_rate2,lables2,'FontSize',12);