package assignment1;

import assignment1.Edge;
import assignment1.Location;

import java.util.ArrayList;

/**
 * This class is for applying the postal information onto Node structure
 *
 * @author Yangyang Xu
 */
public class Node {
    public Location location;
    public Integer arriveTime;
    public Integer departTime;
    public ArrayList<Edge> edges = new ArrayList<Edge>();

    public Node(Location location) {
        this.location = location;
        this.arriveTime = Integer.MAX_VALUE;
        this.departTime = Integer.MIN_VALUE;
    }

    public Node(Location location, int arriveTime, int departTime) {
        this.location = location;
        this.arriveTime = arriveTime;
        this.departTime = departTime;
    }

    @Override
    public String toString() {
        String s = "Node: " + location.toString() + " ArrivalTime = "
                + arriveTime + " DepartTime = " + departTime
                + "\t\n";
        String e = "";
        for (Edge edge : edges) {
            e += edge.toString() + '\n';
        }
        return s + e;
    }

}
