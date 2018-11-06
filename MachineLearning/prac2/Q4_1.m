f = importdata('Iris.mat');
feature1 = f.VarName1(1:50);     
feature3 = f.VarName1(51:100);  
x = (-10:0.1:10);%Sample

%% Maximum likelihoods -> C1(sepal length)
ml1 = mle(feature1,'distribution','norm');
   
%% Maximum likelihoods -> C2(no include Iris-virginica)
ml2 = mle(feature3,'distribution','norm');

%% p(x|C1) -> C1(sepal length)
p1 = normpdf(x,ml1(1),ml1(2));

%% p(x|C2) -> C2(no include Iris-virginica)
p2 = normpdf(x,ml2(1),ml2(2));

%% Class posteriors -> C1(sepal length)
P1 = (p1.*0.5)./(p1.*0.5+p2.*0.5);

%% Class posteriors -> C2(no include Iris-virginica)
P2 = (p2.*0.5)./(p1.*0.5+p2.*0.5);

%% Draw Likelihood functions -> C1 & C2
figure;
subplot(2,1,1);
plot(x,p1,'c',x,p2,'b-');
xlabel('x')
ylabel('p(x|C_i)')
legend('Sepal Length','Sepal length wiithout Iris-virginica')
title('(a) Likelihoods');
%% Draw Class Posteriors functions -> C1 & C2
subplot(2,1,2);
plot(x,P1,'c',x,P2,'b-');
xlabel('x')
ylabel('p(C_i|x)')
legend('Sepal Length','Sepal length wiithout Iris-virginica')
title('(b) Posteriors with equal priors')