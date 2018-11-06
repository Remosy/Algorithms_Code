
idx = unidrnd(60000, 6000, 1);
x = train_X(idx, :);
labels = train_labels(idx);
bigError = zeros(30,30);
for ii = 1:30
    [ydata, error] = tsne(x, labels, 2, 30, ii*10);
    bigError(:,ii)= error;
    size(bigError);
end