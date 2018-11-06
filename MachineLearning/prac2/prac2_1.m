figure;
subplot(2,1,1);
Xt = rand(20,1)*5;
Xt = sort(Xt);
Yt = randn(20,1)+2*sin(1.5*Xt);
% Poly Degree from 1 -> 9
scatter(Xt,Yt,'+');
hold on;
sseT = zeros(9,1);%SSE for Traning
sseV = zeros(9,1);%SSE for Validation
denseInfo = zeros(size(Xt,1),9);%fit error
for j = 1:9
    deg = ['poly',num2str(j)];
    fitpoly = polyfit(Xt,Yt,j);
    y1 = polyval(fitpoly,Xt);
    [fitpoly3, gof]=fit(Xt,Yt,deg);
    sseT(j) = gof.sse;
    plot(Xt,y1);
end
axis([0 5 -5 5]);
xticks(0:0.5:5);
yticks([-5 0 5]);
title('(a) Data and fitted polynomials');
subplot(2,1,2);
% New validation sets
Xv = rand(20,1)*5;
Xv = sort(Xv);
Yv = 2*sin(1.5*Xv);
for k = 1:9
    deg1 = ['poly',num2str(k)];
    [fitpoly1, gof1]=fit(Xv,Yv,deg1);
    sseV(k) = gof1.sse;
end
plot((1:9),sseT,'-',(1:9),sseV,'--');
legend('Training','Validation')
title('(b) Error vs. polynomial order');



%sseT= [37.9851,35.8628,27.8292,26.3964,22.8244,20.9952,17.5180,17.4976,15.3243];
%sseV=[22.9824,21.0432,1.0379,0.5315,0.0043,0.0021,3.6435e-06,8.2285e-07,1.5168e-09];