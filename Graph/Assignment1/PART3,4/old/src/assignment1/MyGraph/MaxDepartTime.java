package assignment1.MyGraph;

import assignment1.Delivery;
import assignment1.Location;

import java.util.Comparator;

public class MaxDepartTime implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        return o2.departTime - o1.departTime;
    }
}
