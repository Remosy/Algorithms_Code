package assignment1.MyGraph;

import assignment1.Delivery;

import java.util.Comparator;

public class MinArriveTime implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return o1.arriveTime-o2.arriveTime;
    }
}
