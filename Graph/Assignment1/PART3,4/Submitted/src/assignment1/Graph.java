package assignment1;

import java.util.ArrayList;

/**
 * This class is for applying the network information onto a graph structure
 * Graph is formed by Node class and Edge class
 * @author  Yangyang Xu
 *
 */
public class Graph {
    public int totalNode;
    public ArrayList<Node> adjacentList;

    public Graph(int totalNode) {
        this.totalNode = totalNode;
        adjacentList = new ArrayList<>();
    }

    public void addNode(Node node) {
        adjacentList.add(node);
    }

    public void addEdge(int pos, Edge edge) {
        adjacentList.get(pos).edges.add(edge);
    }

    public void printGraph() {
        System.out.println("You have graph: ");
        int count = 0;
        for (Node node : adjacentList) {
            System.out.println(node.toString());
        }
        System.out.println("------Finish Print Graph-------" + '\n');
    }

}
