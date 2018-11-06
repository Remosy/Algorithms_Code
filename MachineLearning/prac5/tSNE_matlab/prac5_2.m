
idx = unidrnd(60000, 6000, 1);
x = train_X(idx, :);
labels = train_labels(idx);
bigError = zeros(30,30);
%for ii = 1:10
    [ydata, error] = tsne(x, labels, 2, 30, 290);
    bigError(:,ii)= error;
    size(bigError);
%end