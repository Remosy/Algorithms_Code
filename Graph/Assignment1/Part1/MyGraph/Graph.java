package MyGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {
    public enum Color{ White, Grey, Black }
    static public class Edge {
        public int src, dest, weight;

        public Edge(int src, int dest, int weight)
        {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    };

    static public class Node {
        public int value, weight,discovery,finish;
        public Color color;
        public Node parent;

        public Node(int value, int weight)
        {
            this.value = value;
            this.weight = weight;
            initVertex();
        }
        public void initVertex() {
            color = Color.White;
            parent = null;
        }
        public String toString() {
            return super.toString() + " discovered " + discovery +
                    " finished " + finish +
                    " parent " + (parent == null ? "null" : parent.value);
        }
    };

    public List<List<Node>> adj = new ArrayList<>();
    public Graph(List<Edge> edges) {

        for (int i = 0; i < edges.size(); i++)
            adj.add(i, new ArrayList<>());

        for (Edge e : edges) {
            // allocate new node in adjacency List from src to dest
            adj.get(e.src).add(new Node(e.dest, e.weight));
            //System.out.println("src = "+e.src+" dest = "+e.dest);
            //System.out.println("ADJList_Size = "+adj.get(e.src).size());
            //System.out.println();
        }

    }

    public static void printGraph(Graph graph) {
        int src = 0;
        int n = graph.adj.size();

        while (src < n)
        {
            // print current vertex and all its neighboring vertices
            for (Node edge : graph.adj.get(src))
                System.out.print(src + " --> " + edge.value + " (" + edge.weight + ")\t");

            System.out.println();
            src++;
        }
    }

}
