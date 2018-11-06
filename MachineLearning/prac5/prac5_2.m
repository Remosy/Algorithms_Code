clear all; close all; clc;
load('mnist_train.mat');
idx = unidrnd(60000, 6000, 1);
x = train_X(idx, :);
labels = train_labels(idx);
for ii = 1:3
    tsne(x, labels, 2, 30, 30);
end