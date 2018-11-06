package assignment1.MyGraph.test;

import java.util.*;

import assignment1.Location;
import assignment1.MyGraph.MaxPriorityQ;
import assignment1.MyGraph.MinPriorityQ;
import assignment1.MyGraph.Node;
import org.junit.Assert;
import org.junit.Test;

public class MyPriorityQTest {
    @Test
    public void MaxPQ_Test() {
        MaxPriorityQ maxPQ = new MaxPriorityQ();
        Node node1 = new Node(new Location(0),3,4);
        maxPQ.insert(node1);
        Node node2 = new Node(new Location(0),5,6);
        maxPQ.insert(node2);
        Node node3 = new Node(new Location(0),7,8);
        maxPQ.insert(node3);
        Node node4 = new Node(new Location(0),1,2);
        Assert.assertEquals(maxPQ.extract_Max(), node3);
        Assert.assertEquals(maxPQ.extract_Max(), node2);
        maxPQ.insert(node4);
        Node node5 = new Node(new Location(0),9,12);
        maxPQ.insert(node5);
        Node node6 = new Node(new Location(0),5,6);
        maxPQ.insert(node6);
        Assert.assertEquals(maxPQ.extract_Max(), node5);
    }

    @Test
    public void MinPQ_Test() {
        MinPriorityQ minPQ = new MinPriorityQ();
        // Node 1
        Node node1 = new Node(new Location(0),3,4);
        minPQ.insert(node1);
        System.out.println("MINI1  = " + minPQ.minimum());
        // Node 2
        Node node2 = new Node(new Location(0),5,6);
        minPQ.insert(node2);
        System.out.println("MINI2 = " + minPQ.minimum());
        // Node 3
        Node node3 = new Node(new Location(0),7,8);
        minPQ.insert(node3);
        System.out.println("MINI3 = " + minPQ.minimum());
        // Node 4
        Node node4 = new Node(new Location(0),1,2);
        minPQ.insert(node4);
        System.out.println("MINI4 = " + minPQ.minimum());
        //TEST
        Assert.assertEquals(minPQ.extract_Min(), node4);
        System.out.println("After node 4 MINI = " + minPQ.minimum());
        Assert.assertEquals(minPQ.extract_Min(), node1);


        Node node5 = new Node(new Location(0),0,12);
        minPQ.insert(node5);
        Node node6 = new Node(new Location(0),5,6);
        minPQ.insert(node6);
        Assert.assertEquals(minPQ.extract_Min(), node5);
    }
}
