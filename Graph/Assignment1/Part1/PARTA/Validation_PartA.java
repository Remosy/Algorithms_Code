package PARTA;

import MyGraph.Graph;
import MyGraph.LinkedStack;

import static MyGraph.Graph.Edge;
import static MyGraph.Graph.Node;
import static MyGraph.Graph.Color;
import static MyGraph.Graph.printGraph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Validation_PartA {
    static int[] d = {9,8,4,3,4,4,2,4,0,1,5,2};
    static int time;
    static int count = 10; //For stack
    static LinkedStack<Node> stack = new LinkedStack<Node>();
    public static void main(String[] args) {
        SNI();
        for (Integer i : d){
            System.out.print(i);
        }
        System.out.println();

        Graph S = constructGraph();
        //A node list for all of src node.
        List<Node> nodelist = new LinkedList<Node>();
        for(int i = 0; i< 10;i++) {
            nodelist.add(i,new Node(i,0));
        }
        DFS(nodelist,S);
        List<Node> nodelistT = printStack();
        Graph ST = TransposeGraph(S);
        DFS(nodelistT,ST);

    }

    public static void SNI(){
        for(int i=1; i < 11; i++) {
            //System.out.println("##"+i);
            if (d[i] == d[i - 1]){
                //System.out.print("___"+d[i]+"__");
                d[i] = (d[i] + 3) % 10;
                //System.out.print(d[i]+"\n");
            }
            //System.out.println("[ " + (i+1) + " ]"+" = " + d[i]);
            //System.out.println("_________________________");
        }

    }

    public static Graph constructGraph(){
        List<Edge> edges = new ArrayList<Edge>();
        /*
        List<Edge> edges = Arrays.asList(new Edge(0, 1, 6), new Edge(1, 2, 7),
                new Edge(2, 0, 5), new Edge(2, 1, 4), new Edge(3, 2, 10),
                new Edge(4, 5, 1), new Edge(5, 4, 3));
        */
        //Set<Integer> check = new HashSet<>();

        for(int i = 0; i<11; i++){
            //System.out.println("@@"+i);

            if(d[i]!=d[i+1]) {
                Edge edge = new Edge(d[i], d[i + 1], 0);
                if (!edges.contains(edge)) {
                    //System.out.println("src = "+edge.src+" dest = "+edge.dest);
                    edges.add(edge);
                }else{
                    //System.out.println("Has included"+edge.src+" ---> "+edge.dest);
                }
            }
        }
        // construct graph from given list of edges
        List<Integer> dl = IntStream.of(d).boxed().collect(Collectors.toList());
        Graph graph = CheckEdges(edges,dl);
        // print adjacency list representation of the graph
        return graph;
    }

    public static void DFS(List<Node> nodelist, Graph graph){
        time = 0;
        for(Node node:nodelist){
            node.color = Color.White;
            node.parent = null;
        }

        for(Node node:graph.adj.iterator().next()){
            node.color = Color.White;
            node.parent = null;
        }
        System.out.println("************************Start DFS***********************");

        for(int j = 0; j< 10;j++) {
            Node u = nodelist.get(j);
            System.out.println("***U"+u.value);
            if( u.color == Color.White ) {
                System.out.println("U is white, when U = "+u.value);
                //System.out.println("AjacentLIST_SIZE ="+(graph.adj.find(u.value)).size());
                DFS_Visit(u, graph,nodelist);
                System.out.println("________Finish "+u.value+"'s DFS_Visit_______");
            }
            System.out.println("__________________END____***U = "+u.value+"__________________");
            System.out.println();
        }

        System.out.println("************************END DFS***********************");
    }

    public static void DFS_Visit(Node u, Graph graph, List<Node> nodelist){
        u.color = Color.Grey;
        System.out.println("Changed "+u.value+"'s Color --> GRAY");
        time++;
        u.discovery = time;
        System.out.println("$$$$$$ "+u.value+" -> DiscoverTime= "+u.discovery+" $$$$$$$");
        List<Node> adjList = graph.adj.get(u.value);
        for(int k = 0; k< adjList.size();k++) {
            int index = (adjList.get(k)).value;
            if(index < 0){break;} //When node 6 has no adjencent nodes
            Node v = new Node(0,0);
            for(Node nd:nodelist){
                if (nd.value == (adjList.get(k)).value) v = nd;
            }
            //Node v = nodelist.get((adjList.get(k)).value);
            System.out.println("U: "+u.value + " ---> V: "+ v.value);
            if(v.color == Color.White){
                v.parent = u;
                System.out.println("V is white, when V = "+v.value+" & Parent is -->"+u.value);
                DFS_Visit(v,graph,nodelist);
                System.out.println("___________end__2nd____DFS_Visit_________>>>"+v.value);
            }else{
                System.out.println("V Not white, when V = "+v.value+" & Color is -->"+v.color);
            }
        }
        u.color = Color.Black;
        System.out.println("["+u.value+"'s Color] = BLACK");
        time++;
        u.finish = time;
        System.out.println("$$$$$$ "+u.value+" -> DiscoverTime= "+u.finish+" $$$$$$$");
        stack.push(u);
        System.out.println("Stack Size = "+ stack.size());
    }

    public static Graph TransposeGraph(Graph S){
        List<Edge> edges = new ArrayList<Edge>();
        ArrayList<Integer> dT = new ArrayList<>();
        int src = 0;
        int n = S.adj.size();
        while (src < n) {
            for (Node edge : S.adj.get(src)) {
                if(edge.value<0){
                    edges.add(new Edge(src,edge.value,0));
                    dT.add(src);
                    continue;
                }
                int srcT = edge.value;
                int desT = src;
                Edge edgeT = new Edge(srcT,desT,0);
                edges.add(edgeT);
                dT.add(srcT);
            }
            src++;
        }
        Graph ST = CheckEdges(edges,dT);
        return ST;
    }

    //For testing stack usage.
    static List<Node> printStack(){
        List<Node> nodeListT = new LinkedList<Node>();
        System.out.println("Stack has: ");
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            nodeListT.add(node);
            System.out.print(node.value+"---"+node.finish+", ");
        }
        return nodeListT;

    }

    static Graph CheckEdges(List<Edge> edges,List<Integer> new_d){
        ArrayList<Integer> check = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9));
        for(Integer i : new_d){
            System.out.print(i);
            check.removeAll(Arrays.asList(i));
        }
        System.out.println();
        for(Integer i : check){
            if(!edges.contains(new Edge(i,-1,0)))
                edges.add(new Edge(i,-1,0));
        }
        Graph graph = new Graph(edges);
        printGraph(graph);
        return graph;
    }
}
