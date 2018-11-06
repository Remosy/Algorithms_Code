/*************************************************************************
 *  Compilation:  javac mst.java
 *  Execution:    java mst
 *  Priority Queue, Disjoint Set, Kruskal, Prim codes are similar to CLRS book
 *  Graphs: CompleteGraph, ConnectedGraph
 *  @author  u6325688
 *************************************************************************/
import java.util.*;

/**
 Minimum Priority Queue: Min Heap
 **/
class MPQueue {
    private List<Node> A;

    MPQueue() {
        A = new ArrayList<>();
    }

    private int parent(int i) {
        return (int) Math.floor(i / 2);
    }

    private int left(int i) {
        return 2 * i;
    }

    private int right(int i) {
        return 2 * i + 1;
    }

    private void swap(int i, int j) {
        Node tmp = A.get(i);
        A.set(i, A.get(j));
        A.set(j, tmp);
    }

    void add(Node x) {
        A.add(x);
        int heap_loc = A.size() - 1;
        decreaseKey(heap_loc, x);
    }

    int getPosition(Node x) {
        return A.indexOf(x);
    }

    Node extract_Min() {
        Node min = A.get(0);
        A.set(0, A.get(A.size() - 1));
        A.remove(A.size() - 1);
        min_Heapfify(0);
        return min;
    }

    private void min_Heapfify(int i) {
        int largest;
        int l = left(i);
        int r = right(i);
        if (l <= A.size() - 1 && A.get(l).key < A.get(i).key) {
            largest = l;
        } else {
            largest = i;
        }
        if (r <= A.size() - 1 && A.get(r).key < A.get(largest).key) {
            largest = r;
        }
        if (largest != i) {
            swap(i, largest);
            min_Heapfify(largest);
        }
    }

    void decreaseKey(int i, Node x) {
        if (x.key > A.get(i).key) {
            System.err.println("new Key is " +
                    "smaller than current Key");
            return;
        }
        while (i > 0 &&
                A.get(parent(i)).key > A.get(i).key) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    boolean isEmpty() {
        return A.size() == 0;
    }

    public int size() {
        return A.size();
    }

}


/**
 Disjoint Set
 **/
class DisjointSet {
    private int[] parent;
    private int[] rank;
    int size;
    DisjointSet(int size) {
        this.parent = new int[size];
        this.rank = new int[size];
        this.size = size;
        makeSet();
    }

    void makeSet(){
        for (int i=0; i<size; i++)
        {
            parent[i] = i;
            rank[i]= 0;
        }
    }

    void union(int v1, int v2){
        link(findSet(v1),findSet(v2));
    }

    private void link(int v1, int v2){
        if (rank[v1] < rank[v2])
            parent[v1] = v2;
        else if (rank[v2] < rank[v1])
            parent[v2] = v1;
        else
        {
            parent[v2] = v1;
            rank[v1] += 1;
        }
        size--;
    }

    int findSet(int v){
        if(v != parent[v]){
            parent[v] = findSet(parent[v]);
        }
        return parent[v];
    }


}

/**
 Node
 **/
class Node {
    int parent;
    int id;
    double x;
    double y;
    double key;

    Node(int id) {
        this.x = Math.random();
        this.y = Math.random();
        this.id = id;
        this.parent = -1;
        this.key = Double.MAX_VALUE;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public void setKey(double key) {
        this.key = key;
    }
}

/**
 Edge
 **/
class Edge {
    Node v1;
    Node v2;
    double w; //weight
    Edge(Node v1, Node v2, double w) {
        this.v1 = v1;
        this.v2 = v2;
        this.w = w;
    }

    void setW(double w){
        this.w = w;
    }
}

/**
 Complete Graph
 **/
class CompleteGraph {
    private double[][] weightGraph;
    ArrayList<Edge> edges;
    int numNode;
    int numEdge;

    public CompleteGraph(Node[] nodes, int size) {
        //Init
        this.numNode = size;
        this.numEdge = size*(size-1)/2; //formula
        this.edges = new ArrayList<>();
        this.weightGraph = new double[size][size];
        //Add weight and edges
        for(int i = 0; i<size;i++){
            Node v1 = nodes[i];
            for (int j = i; j<size; j++){
                Node v2 = nodes[j];
                if(i == j) {
                    this.weightGraph[i][j] = 0;
                    this.weightGraph[j][i] = 0;
                }else {
                    this.weightGraph[i][j] = getWeight(v1,v2);
                    this.weightGraph[j][i] = this.weightGraph[i][j];
                }
                Edge e = new Edge(v1,v2, this.weightGraph[i][j]);
                this.edges.add(e);
            }
        }
        //Sort Edges
        sortEdge();
    }

    private double getWeight(Node v1, Node v2){
        double diff_x = v1.x - v2.x;
        double diff_y = v1.y - v2.y;
        return Math.sqrt(diff_x*diff_x +diff_y*diff_y);
    }

    private void sortEdge(){
        this.edges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge c1, Edge c2) {
                return Double.compare(c1.w, c2.w);
            }
        });
    }
}

/**
 Connected Graph
 **/
class ConnectedGraph {
    LinkedList<LinkedList<Node>> adjLists;
    ArrayList<Edge> edges;
    ArrayList<Edge> checkEdges;
    int numNode;
    int numEdge;
    Node[] nodes;
    ConnectedGraph(Node[] nodes, int size){
        this.nodes = nodes;
        //Init
        this.numNode = size;
        this.checkEdges = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.adjLists = new LinkedList<LinkedList<Node>>();
        for (int i = 0; i < size; i++)
            adjLists.add(i, new LinkedList<Node>());
        //Init Disjoint Set
        DisjointSet disjointSet = new DisjointSet(size);
        disjointSet.makeSet();
        //Add weight and edges
        while(disjointSet.size > 1){
            Random rn = new Random();
            int v1_id = rn.nextInt(size);
            int v2_id = rn.nextInt(size);
            Node v1 = nodes[v1_id];
            Node v2 = nodes[v2_id];
            double weight = getWeight(v1,v2);
            Edge e = new Edge(v1,v2,weight);
            //Simulate part-join
            if(!adjLists.get(v1_id).contains(v2) && v1_id != v2_id){
                if (disjointSet.findSet(v1.id) != disjointSet.findSet(v2.id)){
                    disjointSet.union(v1.id,v2.id);
                }
                this.edges.add(e);
                this.checkEdges.add(e);
                this.checkEdges.add(new Edge(v2,v1,weight));
                this.adjLists.get(v1_id).add(v2);
                this.adjLists.get(v2_id).add(v1);
            }
        }
        this.numEdge = edges.size();
    }

    private double getWeight(Node v1, Node v2){
        double diff_x = v1.x - v2.x;
        double diff_y = v1.y - v2.y;
        return Math.sqrt(diff_x*diff_x +diff_y*diff_y);
    }

    long sortEdge(){
        //RUNNING TIME
        long t = (long) (numEdge*Math.log(numEdge)); //Java 7 sort uses O(nlgn)

        this.edges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge c1, Edge c2) {
                return Double.compare(c1.w, c2.w);
            }
        });
        return t;
    }
}

/**
 Kruskal Algorithm
 **/
class KruskalAlgorithm {
    public double weight;
    public ArrayList<Edge> mst_edges;
    long timmer;

    public KruskalAlgorithm(CompleteGraph completeGraph){
        //init
        this.weight = 0;
        this.mst_edges = new ArrayList<Edge>();
        DisjointSet set = new DisjointSet(completeGraph.numNode);

        for(Edge e: completeGraph.edges){
            int id_v1 = e.v1.id;
            int id_v2 = e.v2.id;
            if(set.findSet(id_v1)!=set.findSet(id_v2)){
                this.mst_edges.add(e);
                set.union(id_v1,id_v2);
                this.weight+=e.w;
            }
        }
    }

    KruskalAlgorithm(ConnectedGraph connectedGraph) {
        //RUNNING TIME
        timmer=0;
        timmer++;
        //Init
        this.weight = 0;
        this.mst_edges = new ArrayList<Edge>();

        //Sort Edges
        long t = connectedGraph.sortEdge();
        //RUNNING TIME
        timmer+=t;

        DisjointSet set = new DisjointSet(connectedGraph.numNode);
        //RUNNING TIME
        timmer+=connectedGraph.numNode;//Make-Set:O(n)

        for(Edge e: connectedGraph.edges){
            //RUNNING TIME
            timmer++;

            int id_v1 = e.v1.id;
            int id_v2 = e.v2.id;
            if(set.findSet(id_v1)!=set.findSet(id_v2)){
                mst_edges.add(e);
                set.union(id_v1,id_v2);
                this.weight+=e.w;

                //RUNNING TIME
                timmer++;
                timmer+=Math.log(set.size);//Union/find:O(lgn)
            }
        }
    }

}

/**
 Prim Algorithm
 **/
class PrimAlgorithm {
    private MPQueue queue;
    //private Node[] primNodes;
    private Boolean[] isInPrimMST;
    long timmer;
    public double weight;

     double getWeight(Node v1, Node v2){
         //RUNNING TIME
         //timmer++;

        double diff_x = v1.x - v2.x;
        double diff_y = v1.y - v2.y;
        return Math.sqrt(diff_x*diff_x +diff_y*diff_y);
    }

    PrimAlgorithm(ConnectedGraph connectedGraph){
        //RUNNING TIME
        timmer=0;
        timmer++;

        //Init
        this.queue = new MPQueue();
        this.weight = 0;
        //primNodes = new Node[connectedGraph.numNode];
        this.isInPrimMST = new Boolean[connectedGraph.numNode];
        for(int pn = 0;pn<connectedGraph.numNode;pn++){
            this.isInPrimMST[pn]=false;
            //RUNNING TIME
            timmer++;
        }
        //Set Queue
        connectedGraph.nodes[0].key = 0;
        this.isInPrimMST[0]=true;

        for (Node n:connectedGraph.nodes){
            this.queue.add(n);
        }
        //RUNNING TIME
        timmer+=connectedGraph.numNode*Math.log(connectedGraph.numNode);//Priority-insert:O(nlgn)

        while (!this.queue.isEmpty()){
            //RUNNING TIME
            timmer++;

            Node u = this.queue.extract_Min();
            //RUNNING TIME
            if(this.queue.size()>0) {
                timmer += Math.log(this.queue.size());//Priority-extract:O(lgn)
            }else {
                timmer++;
            }

            isInPrimMST[u.id] = true;
            for(Node v : connectedGraph.adjLists.get(u.id)){
                //RUNNING TIME
                timmer++;

                double weight = getWeight(u,v);
                if(isInPrimMST[v.id] == false && weight<v.key){
                    //RUNNING TIME
                    timmer++;
                    v.parent = u.id;
                    v.key = weight;
                    this.queue.decreaseKey(this.queue.getPosition(v),v);
                    //RUNNING TIME
                    timmer+=Math.log(this.queue.size());//Decrease-Key O(lgn)
                }
            }
        }
        //Calculate Weight
        for(Node n: connectedGraph.nodes){
            if(n.parent!=-1){
                this.weight += getWeight(n,connectedGraph.nodes[n.parent]);
                //System.out.println("Node["+n.id+"] -->"+n.parent);
            }
        }
        //RUNNING TIME
        timmer+=connectedGraph.numNode;
    }
}

public class mst {

    /**
     Generate Random points
     **/
    private static Node[] generatePoints(int size){
        Node[] nodes = new Node[size];
        for(int i = 0; i < size; i++){
            nodes[i]= new Node(i);
        }
        return nodes;
    }

    public static void main(String[] args){

        int numGroup[] = {100,500,1000,5000}; //num of nodes in each group
        /**** Generate Complete Graph ****/
       System.out.println("[Q1]  (i)");
        int numGraph = 50;
        double weights[] = new double[numGroup.length];
        for(int i = 0; i < numGroup.length;i++){
            int node_size = numGroup[i];
            if(i==3) {
                System.out.print("5000nodes will take 15 minutes. Finished graph: ");
                //continue;
            }

            for(int j = 0; j < numGraph; j++) {
                CompleteGraph completeGraph = new CompleteGraph(generatePoints(node_size), node_size);
                KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(completeGraph);
                weights[i] += kruskalAlgorithm.weight;
                if(i==3 && j%5==0)
                System.out.print(" "+j);
            }
            weights[i] = weights[i]/numGraph; //store average weight
            System.out.println("When n = "+node_size+";  p = "
                    + numGraph+";  Kruskal's===[MST average L(n) : "+ weights[i]+"]");
        }


        System.out.println();


        /* Generate Connected Graph */
        System.out.println("[Q1]  (iii)");
        int numGraph2 = 50;
        double weights_kruskal[] = new double[numGroup.length];
        double weights_prim[] = new double[numGroup.length];
        long runTime_Kruskal[] = new long[numGroup.length];
        long runTime_Prim[] = new long[numGroup.length];
        for(int i = 0; i < numGroup.length;i++){
            int node_size = numGroup[i];
            for(int j = 0; j < numGraph2; j++) {
                ConnectedGraph connectedGraph
                        = new ConnectedGraph(generatePoints(node_size), node_size);
                KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(connectedGraph);
                PrimAlgorithm primAlgorithm = new PrimAlgorithm(connectedGraph);
                weights_kruskal[i] += kruskalAlgorithm.weight;
                weights_prim[i] += primAlgorithm.weight;
                runTime_Kruskal[i] += kruskalAlgorithm.timmer;
                runTime_Prim[i] += primAlgorithm.timmer;
            }
            //store average time
            runTime_Kruskal[i]=runTime_Kruskal[i]/numGraph2;
            runTime_Prim[i]=runTime_Prim[i]/numGraph2;
            //store average weight
            weights_kruskal[i] = weights_kruskal[i]/numGraph2;
            weights_prim[i] = weights_prim[i]/numGraph2;
            System.out.println("When n = "+node_size+";  p = "
                    + numGraph2+";  Kruskak's===[RunTime : "+ runTime_Kruskal[i]+
                    " / MST Average L(n): "+ weights_kruskal[i]
                    +"]   Prim's===[RunTime : "+ runTime_Prim[i]+
                    " / MST Average L(n): "+ weights_prim[i]+"]");
        }

    }
}