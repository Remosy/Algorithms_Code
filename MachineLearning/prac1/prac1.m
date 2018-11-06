%% SETUP
close all
clear all

 data3 = load('prac1_q3(2).dat');
 data4 = load('prac1_q4(2).dat');
%% Question 3

Mean = mean(data3)
Standard_Deviation = std(data3)

%% Quetstion 4
figure;
data4_size = size(data4);
col_size = data4_size(1);
%plot(data4(1:201,1),data4(1:201,2),'bo',data4(1:201,3),data4(1:201,4));
plot(data4(1:col_size,1),data4(1:col_size,2),'bo',data4(1:col_size,3),data4(1:col_size,4),'rs');
xlabel('Input');
ylabel('Output');

%% Quetstion 5
hold on;
mean5 = 2;
std5 = 4;
data5 = std5*randn(1000,1) + mean5;
figure;
histogram(data5,30);
xlabel('Random Variable');
ylabel('Frequency');
