idx = unidrnd(60000, 6000, 1);
x = train_X(idx, :);
labels = train_labels(idx);
[y,error] = tsne(x, labels, 2, 30, 100);