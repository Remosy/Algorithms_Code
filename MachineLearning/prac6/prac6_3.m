%Train Database
%load('mnist_train.mat');
labels_train = [];  
mylabels = [];
x_train = horzcat(train_X,train_labels);
x_test = horzcat(test_X,test_labels);
x = vertcat(x_train,x_test);
x = sortrows(x,785);
x(:,785)=[];
%pos_start = 1;
for ii = 1:10
    %Data
    a = x_train(x_train(:,785)==ii);
    b = x_test(x_test(:,785)==ii);
    pos_train = size(a)
    pos_test = size(b)
    
    %Labels
    c = [train_labels(train_labels == ii);test_labels(test_labels == ii)];
    labels_train = [labels_train;c];   
end
for ii = 1:10
    mylabels = [mylabels, labels_train == ii];
end


