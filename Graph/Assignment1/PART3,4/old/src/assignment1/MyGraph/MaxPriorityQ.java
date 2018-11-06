package assignment1.MyGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MaxPriorityQ {
    private List<Node> A;

    public MaxPriorityQ(){
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
        increaseKey(heap_loc,x);
    }
    public Node maximum(){
        return A.get(0);
    }

    public Node extract_Max(){
        if(A.size() < 0){
            System.err.println("Queue underflow");
        }
        Node max = A.get(0);
        A.set(0, A.get(A.size()-1));
        A.remove(A.size()-1);
        max_Heapfify(0);
        return max;
    }

    public void max_Heapfify(int i){
        int smallest;
        int l = left(i);
        int r = right(i);
        if(l<=A.size()-1 && A.get(l).departTime > A.get(i).departTime){
            smallest = l;
        }else{
            smallest = i;
        }
        if(r<=A.size()-1 && A.get(r).departTime > A.get(smallest).departTime){
            smallest = r;
        }
        if(smallest != i){
            swap(i,smallest);
            max_Heapfify(smallest);
        }
    }

    public void increaseKey(int i, Node x){
        if(x.departTime < A.get(i).departTime){
            System.err.println("new Key_departTime is " +
                    "smaller than current Key");
            return;
        }
        while(i > 0 &&
                A.get(parent(i)).departTime < A.get(i).departTime){
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
