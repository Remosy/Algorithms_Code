function [povResult,reducedData] = PCA(train_X,train_labels,n)
%prac5_1 Principal Component Analysis 
%
%   [P, beta] = PCA(dataset1, dataset2, number)
% Inputs:
%   dataset1: Raw data (For MNIST: train_X)
%   dataset2: Data labels (For MNIST: train_labels)
%   number:   Expected classes (But only Max 64 can be displayed on 
%             Figure 1)
% Outputs:
%   ReducedData: Reduced to 2D, included First & Second principal component
%   povResult:   Proportion of variance for Q2(b)
    %% Q1
    x = train_X; %n = 10;
    data = cov(x);
    [vector,value] = eig(data);
    %First principal component: 1st largest -> Dimention Reduction 
    X1 = x*vector(:,end); 
    %Second principal component: 2nd largest -> Dimention Reduction 
    X2 = x*vector(:,end-1); 
    %Reduced Data
    reducedData = [X1,X2];
    size(reducedData)
    %Added lables onto reduced data
    X1 = horzcat(X1,train_labels);
    X2 = horzcat(X2,train_labels); 
    %% Q2
    c=colorcube;
    eval0 = eig(data); % inputed_raw eigenvalues
    labels = [];
    for ii = 1:n
        a = X1(X1(:,2)==ii);%x_axis
        b = X2(X2(:,2)==ii);%y_axis
        scatter(a,b,[],c(ii,:,:));
        legendInfo{ii} = ['Class = ' num2str(ii)];
        hold on;
    end
    
    grid on;
    legend(legendInfo);
    xlabel('First eigenvector');
    ylabel('Second eigenvector');
    title('Input data after PCA');
    %% Q3 & Q4
    sort_value = sort(eval0,'descend');
    figure,subplot(2,1,1);
    plot(sort_value,'+-');
    grid on;
    xlabel('Eigenvectors')
    ylabel('Eigenvalues')
    title('(a) Scree graph for Input data');
    
    hold on;
    %Proportion of variance: used feature(1->k)/inputed_raw features(1->d)
    povResult = (eval0(end)+ eval0(end-1))/sum(eval0);
    subplot(2,1,2);
    cumsum_sort_value = cumsum(sort_value);
    plot(cumsum_sort_value/sum(eval0),'+-');
    hold on;
    txt = ['Q2\_(b) =',num2str(povResult)];
    plot(povResult,'kd','MarkerSize',8,'MarkerFaceColor',[.49 1 .63])
    hoz_line = refline([0 povResult]);
    hoz_line.Color = 'r';
    text(0,povResult,txt,'HorizontalAlignment','left')
    grid on;
    xlabel('Eigenvectors')
    ylabel('Prop. of var.')
    title('(b) Proportion of variance explained');
    hold off;
end