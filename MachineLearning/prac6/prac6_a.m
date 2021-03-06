% Solve a Pattern Recognition Problem with a Neural Network
% Script generated by Neural Pattern Recognition app
% Created 02-May-2017 11:29:38
%
% This script assumes these variables are defined:
%
%   train_X - input data.
%   labels - target data.
%hLSize = linspace(10,100,10); %8    10    12    14    16
result_pe = zeros(10,1);%percent Error
result_performance = zeros(10,1);
ct=0;
%for i = hLSize
    x = train_X';
    t = labels';

    % Choose a Training Function
    % For a list of all training functions type: help nntrain
    % 'trainlm' is usually fastest.
    % 'trainbr' takes longer but may be better for challenging problems.
    % 'trainscg' uses less memory. Suitable in low memory situations.
    trainFcn = 'traingd';  % Scaled conjugate gradient backpropagation.

    %###########reate a Pattern Recognition Network
    hiddenLayerSize = 200;
    net = patternnet(hiddenLayerSize, trainFcn);

    % Setup Division of Data for Training, Validation, Testing
    net.divideParam.trainRatio = 70/100;
    net.divideParam.valRatio = 15/100;
    net.divideParam.testRatio = 15/100;
    
    % Train the Network
    [net,tr] = train(net,x,t);

    % Test the Network
    ct =  ct +1;
    %net.trainParam.lr = i;
    y = net(x);
    e = gsubtract(t,y);
    performance = perform(net,t,y)
    tind = vec2ind(t);
    yind = vec2ind(y);
    percentErrors = sum(tind ~= yind)/numel(tind);
    result_pe(ct) = percentErrors;
    result_performance(ct) = performance;
%end
plot(lR,result_pe);
% View the Network
view(net)

% Plots
% Uncomment these lines to enable various plots.
%figure, plotperform(tr)
%figure, plottrainstate(tr)
%figure, ploterrhist(e)
%figure, plotconfusion(t,y)
%figure, plotroc(t,y)

