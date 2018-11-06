clear;
data = importdata('pimaindiansdiabetes.mat');
iris = importdata('iris.mat');
sample = data(1:500,1:8); %sample
%Mean
sample_mean = mean(sample);
%Covariance
sample_cov = cov(sample);
%Classification Error:loss function
Class1 = sample(data(1:500,9)==1,:);
Class2 = sample(data(1:500,9)==0,:);
%% Q1: QDA - Sample
% p(x)
sp = mvnpdf(sample,sample_mean,sample_cov);
% P(X|Class1)
sP1 = mvnpdf(sample,mean(Class1),cov(Class1));
% P(X|Class2)
sP2 = mvnpdf(sample,mean(Class2),cov(Class2));
%Likelihood Density P(C1|x)
sP_density1 = sP1.*(182/500)./sp;
%Likelihood Density P(C2|x)
sP_density2 = sP2.*(318/500)./sp;
%Error: compare to class 1
specifier_sClass1 = sP_density1 ./ (sP_density1+sP_density2);
%Get exact specifier for test data
specifier_s = round(specifier_sClass1);
%Find the # of error data from test
serrorSet = specifier_s(data(1:500,9)~=specifier_s(:));
num_error = size(serrorSet);
%Classification Error
classificationError = num_error(1)/500;
%% Q1: QDA - Test
sample = data(1:500,1:8); %sample
test = data(501:768,1:8);
%Mean
sample_mean = mean(sample);
%Covariance
sample_cov = cov(sample);
%Classification Error:loss function
Class1 = sample(data(1:500,9)==1,:);
Class2 = sample(data(1:500,9)==0,:);
% p(x)
p = mvnpdf(test,sample_mean,sample_cov);
% P(X|Class1)
P1 = mvnpdf(test,mean(Class1),cov(Class1));
% P(X|Class2)
P2 = mvnpdf(test,mean(Class2),cov(Class2));
%Likelihood Density P(C1|x)
P_density1 = P1.*(86/268)./p;
%Likelihood Density P(C2|x)
P_density2 = P2.*(182/268)./p;
%Error: compare to class 1
specifier_Class1 = P_density1 ./ (P_density1+P_density2);
%Get exact specifier for test data
specifier = round(specifier_Class1);
%Find the # of error data from test
errorSet = specifier(data(501:768,9)~=specifier(:));
num_errorTest = size(errorSet);
%Trainning Error
trainingError = num_errorTest(1)/268;

