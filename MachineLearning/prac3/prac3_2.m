clear;
data = importdata('pimaindiansdiabetes.mat');
sample = data(1:500,1:8); %sample
%Mean
sample_mean = mean(sample);
%Classification Error:loss function
Class1 = sample(data(1:500,9)==1,:);
Class2 = sample(data(1:500,9)==0,:);
%Covariance S=sum(P(c)S)
sample_cov = (182/500).*cov(Class1)+(318/500).*cov(Class2);
%% Q2: LDA - Sample
% p(x)
sp = mvnpdf(sample,sample_mean,sample_cov);
% P(X|Class1)
sP1 = mvnpdf(sample,mean(Class1),sample_cov);
% P(X|Class2)
sP2 = mvnpdf(sample,mean(Class2),sample_cov);
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
%Training Error
trainingError = num_error(1)/500;
%% Q2: LDA - Test
sample = data(1:500,1:8); %sample
test = data(501:768,1:8);
%Mean
sample_mean = mean(sample);
%Classification Error:loss function
Class1 = sample(data(1:500,9)==1,:);
Class2 = sample(data(1:500,9)==0,:);
%Covariance S=sum(P(c)S)
sample_cov = (182/500).*cov(Class1)+(318/500).*cov(Class2);
% p(x)
p = mvnpdf(test,sample_mean,sample_cov);
% P(X|Class1)
P1 = mvnpdf(test,mean(Class1),sample_cov);
% P(X|Class2)
P2 = mvnpdf(test,mean(Class2),sample_cov);
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
%Testing Error
testingError = num_errorTest(1)/268;