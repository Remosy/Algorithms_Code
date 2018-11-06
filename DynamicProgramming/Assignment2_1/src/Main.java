import java.util.*;

public class Main {
    public static void main(String[] args) {
        MyFlag flag1 = new MyFlag(1,5);
        MyFlag flag2 = new MyFlag(2, 3);
        MyFlag flag3 = new MyFlag(3, 11);
        PriorityQueue<MyFlag> flagList = new PriorityQueue<>(flagComp);
        flagList.add(flag1);
        flagList.add(flag2);
        flagList.add(flag3);
        FetchFlag(flagList);
    }

    public static Comparator<MyFlag> flagComp = new Comparator<MyFlag>(){
        @Override
        public int compare(MyFlag o1, MyFlag o2) {
            return (int) (o1.distance - o2.distance);
        }
    };


    public static void FetchFlag(PriorityQueue<MyFlag> flagList){
        int distance=0;
        List<Integer> old_distance = new ArrayList<>();
        while (!flagList.isEmpty()){
            for(int i = 0;i<old_distance.size();i++){
                distance += old_distance.get(i);
            }
            distance+= 2*flagList.peek().distance;
            old_distance.add(flagList.peek().distance);
            System.out.println("Flag "+flagList.poll().index);
        }
        System.out.println("Minimum Distance: "+ distance);
    }
}
class MyFlag{
    int index;
    int distance;
    public MyFlag(int index, int distance){
        this.index = index;
        this.distance = distance;
    }
}