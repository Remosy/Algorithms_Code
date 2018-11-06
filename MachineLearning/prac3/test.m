function[]= DicePlot()
for roll=1:5000
    diceValues = randi(6,[1, 10]);
    SumDice(roll) = sum(diceValues);
end
distr=zeros(1,6*10);
for i = 10:60
    distr(i)=histc(SumDice,i);
end
bar(distr,1)
xlabel('sum of dice values')
ylabel('relative frequency')
title(['NumDice = ',num2str(NumDice),' , NumRolls = ',num2str(NumRolls)]); 
  end