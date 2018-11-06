package assignment1.MyGraph;

import java.util.ArrayList;
import java.util.List;

public class MinPriorityQ {
    private List<Node> A;

    public MinPriorityQ(){
        A = new ArrayList<>();
    }

    int parent(int i){
        return (int) Math.ceil((i-1)/2);
    }

    int left(int i){
        return 2*i;
    }

    int right(int i){
        return 2*i + 1;
    }

    public void swap(int i, int j){
        Node tmp = A.get(i);
        A.set(i, A.get(j));
        A.set(j,tmp);
    }

    public void insert(Node x){
        A.add(x);
        int heap_loc = A.size()-1;
        System.out.println("Heap_location = "+heap_loc);
        decreaseKey(heap_loc,x);
    }
    public Node minimum(){
        return A.get(0);
    }

    public Node extract_Min(){
        if(A.size() < 0){
            System.err.println("Queue underflow");
        }
        Node min = A.get(0);
        A.set(0, A.get(A.size()-1));
        A.remove(A.size()-1);
        min_Heapfify(0);
        return min;
    }

    public void min_Heapfify(int i){
        int largest;
        int l = left(i);
        int r = right(i);
        if(l<=A.size()-1 && A.get(l).arriveTime < A.get(i).arriveTime){
            largest = l;
        }else{
            largest = i;
        }
        if(r<=A.size()-1 && A.get(r).arriveTime < A.get(largest).arriveTime){
            largest = r;
        }
        if(largest != i){
            swap(i,largest);
            min_Heapfify(largest);
        }
    }

    public void decreaseKey(int i, Node x){
        if(x.arriveTime > A.get(i).arriveTime){
            System.err.println("new Key_departTime is " +
                    "smaller than current Key");
            return;
        }
        while(i > 0 &&
                A.get(parent(i)).arriveTime > A.get(i).arriveTime){
            swap(i,parent(i));
            i = parent(i);
        }
    }

    public boolean isEmpty(){
        return A.size() == 0;
    }

    public int size(){
        return A.size();
    }
}
