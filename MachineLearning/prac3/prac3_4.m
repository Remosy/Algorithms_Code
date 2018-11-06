
x = [randn(30,1); 5+randn(30,1)];
test_pts = linspace(min(x),max(x),100);
M = 0.5.*normpdf(test_pts,0)+0.5.*normpdf(test_pts,5);
% Histogram -> Density Probability based 20_bin bin_width
[N,center]= hist(x,20);
H = histogram(x,20);
H_head = H.BinLimits(1);
H_end = H.BinLimits(2);
H_density = (H.Values/60)/H.BinWidth; 

% Histogram -> Scale new #100 bin_width based on the center density
new_intervals =  linspace(0.6,20.4,100); %intervals got from H0 = bar(H_density,'w')
H1 = [];
for i = new_intervals
    H1 = [H1,(H_density(round(i)))];
end
figure;


[f1,x1,b] = ksdensity(x,test_pts);%K1
[f2,x2] = ksdensity(x,test_pts,'Bandwidth',b/2);%K2

hold on;
bar(x1,H1,'w');
hold on;
plot(x1,f1)
hold on;
plot(x2,f2)


%KL divergences: M -> H1
size(H1(H1==0))
H1(H1==0) = 0.000000001;
Process = M.*log(M./H1);
M_H1 = sum(Process(isfinite(Process)));
%KL divergences: M -> K1
M_K1 = sum(M.*log(M./f1));
%KL divergences: M -> K2
M_K2 = sum(M.*log(M./f2));
 