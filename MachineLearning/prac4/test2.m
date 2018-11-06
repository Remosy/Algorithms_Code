%clear all;
%rng(s);
a = randn(200,2);
b=a+4;
c=a;
c(:,1) = 3*c(:,1);
c=c-4;
d=[a;b];
e=[a;b;c];
plot(a(:,1),a(:,2),'+');
hold on
plot(b(:,1),b(:,2),'o');
plot(c(:,1),c(:,2),'*');