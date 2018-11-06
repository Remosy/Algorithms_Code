package assignment1;

import assignment1.MyGraph.*;
import javafx.beans.binding.When;

import java.util.*;

public class LocationFinder {
    static Graph graph;
    static Graph graphT;
    static Location src;
    static Location dst;
    //static Integer src_time;
    //static Integer dst_time;
    //static Integer delay;
    //static PriorityQueue<Node> nodelist = new PriorityQueue<>(new MinArriveTime());
    //static PriorityQueue<Node> nodelistT = new PriorityQueue<>(new MaxDepartTime());
    /**
     * @require The set of locations on the postal network, locations, is not
     *          null and each location in the set of locations is not null and
     *          has a unique identifier in the range [0, locations.size()-1].
     * 
     *          The set of locations contains the source and destination
     *          locations, and those two locations are not equal.
     * 
     *          The time after which the package departed the source location,
     *          ts, is non-negative (i.e. 0 <= ts). The time that the package
     *          arrived at the destination location by, td, is later than ts
     *          (i.e. ts < td).
     * 
     *          The deliveries in the deliveries log are sorted in ascending
     *          order by their departure times.
     *
     *          For each delivery x in the deliveries, the source and
     *          destination of x are in the set of locations, and ts <
     *          x.departure() < x.arrival() < td.
     * 
     *          The delay d is a positive integer (i.e. 0 < d).
     * 
     * @ensure Returns the set of locations on the postal network such that the
     *         package may have departed the source location at some time after
     *         ts, and arrived at the destination location at some time before
     *         td, having been routed through the location (using the given
     *         deliveries), with a delay in that location of at least d time
     *         units between two deliveries. (See the assignment handout for
     *         details.)
     */
    public static Set<Location> findLocations(Set<Location> locations,
            Location source, int ts, Location destination, int td,
            List<Delivery> deliveries, int d) {
        //Validation
        //ValidateInputs(locations,source,ts,destination,td,deliveries,d);
        //Assign variables
        src = source;
        dst = destination;
        //src_time = ts;
        //dst_time = td;
        //delay = d;
        //Contruct Graph
        graph = new Graph(locations.size());

        //////////////////////////////////////////////////////////////////////////////////////
        //        METHOD 1
        //***********************************************************************************
        /*for(Location location:locations){
            Node node = new Node(location);
            if(location==source){
                node.arriveTime = ts;
            }else if(location == destination){
                node.departTime = td;
            }
            graph.addNode(node);
        }
        System.out.println("Source = "+source.toString());
        for(Delivery delivery: deliveries){
            Node src = new Node(delivery.source());
            Node dst = new Node(delivery.destination());
            Edge edge = new Edge(src,dst,delivery.departure(),delivery.arrival());
            graph.addEdge(src.location.identifier(),edge);
        }
        graph.printGraph();
        filterArrivals();
        graphT = transposeGraph(graph,deliveries);
        graphT.printGraph();
        filterDepartures();
        Set<Location> reults = monitorNodes(graphT.adjacentList);*/
        //*************************************************************************************
        //////////////////////////////////////////////////////////////////////////////////////
        //        METHOD 2
        //***********************************************************************************
        for(Location location:locations){
            Node node = new Node(location);
            if(location==source){
                node.arriveTime = ts;
            }else if(location == destination){
                node.departTime = td;
            }
            graph.addNode(node);
        }
        for(Delivery delivery: deliveries){
            Node src = graph.adjacentList.get(delivery.source().identifier());
            Node dst = graph.adjacentList.get(delivery.destination().identifier());
            Edge edge = new Edge(src,dst,delivery.departure(),delivery.arrival());
            graph.addEdge(src.location.identifier(),edge);
        }
        graph.printGraph();
        filterArrivals2();
        graphT = transposeGraph(graph,deliveries);
        graphT.printGraph();
        filterDepartures2();
        Set<Location> reults = monitorNodes(graphT.adjacentList);
        //*************************************************************************************
        return reults;
    }

    public static Set<Location> monitorNodes(ArrayList<Node> graphNodes){
        Set<Location> results = new HashSet<Location>();
        for(Node node: graphNodes){
            if(node.location!=src && node.location!=dst
                    && node.departTime-node.arriveTime>=3){
                results.add(node.location);
            }
        }
        return results;

    }

    public static PriorityQueue<Node> getNodeList(Set<Location> locations, Location source,Integer ts){
        PriorityQueue<Node> nodelist = new PriorityQueue<>(new MinArriveTime());
        Node src = new Node(source);
        src.arriveTime = ts;
        src.parent = src;
        nodelist.add(src);
        for(Location location:locations) {
            if(!location.equals(source))nodelist.add(new Node(location));
        }
        System.out.println("Finish getting nodelist");
        return nodelist;
    }

    public static Graph transposeGraph(Graph graph, List<Delivery> deliveries){
        Graph graphT = graph;
        /*for(Location location:locations){
            Node node = new Node(location);
            graphT.addNode(node);
        }*/
        for(Node node: graphT.adjacentList){
            node.edges = new ArrayList<>();
        }
        for(Delivery delivery: deliveries){
            Node src = graph.adjacentList.get(delivery.source().identifier());
            Node dst = graph.adjacentList.get(delivery.destination().identifier());
            Edge edge = new Edge(dst,src,delivery.departure(),delivery.arrival());
            graphT.addEdge(dst.location.identifier(),edge);
        }
        return graphT;
    }
/*
    public static Graph initializeSingleSource(Graph graph){
        for(Node node:graph.adjacentList){
            node.parent = null;
            if(node.location!=src || node.location!=dst) {
                node.arriveTime = Integer.MAX_VALUE;
                node.departTime = Integer.MIN_VALUE;
            }
        }
        return graph;
    }*/

    public static void relaxArrival(Node src, Node dst,Integer src_time){
        int pos = dst.location.identifier();
        System.out.println("time:"+graph.adjacentList.get(pos).arriveTime);
        if(src.arriveTime<=src_time &&
                graph.adjacentList.get(pos).arriveTime > src_time
                ){
            graph.adjacentList.get(pos).parent = src.parent;
            graph.adjacentList.get(pos).arriveTime = src_time;
           // nodelist.add(graph.adjacentList.get(pos));
        }
    }

    public static void relaxArrival2(Node src, Node dst,Integer src_time){
        int pos = dst.location.identifier();
        System.out.println("time:"+graph.adjacentList.get(pos).arriveTime);
        if(src.arriveTime<=src_time &&
                graph.adjacentList.get(pos).arriveTime > src_time
                ){
            //graph.adjacentList.get(pos).parent = src.parent;
            graph.adjacentList.get(pos).arriveTime = src_time;
            //nodelist.add(graph.adjacentList.get(pos));
        }
    }

    public static void relaxDeparture(Node src, Node dst,Integer dpt_time){
        int pos = dst.location.identifier();
        System.out.println("DstTime:"+graphT.adjacentList.get(pos).departTime+" srcArrTime:"+src.arriveTime+" DptTime:"+dpt_time);
        if(src.arriveTime != Integer.MIN_VALUE &&
                src.departTime>=dst.departTime &&
                graphT.adjacentList.get(pos).departTime < dpt_time){
            graphT.adjacentList.get(pos).parent = src;
            graphT.adjacentList.get(pos).departTime = dpt_time;
            //nodelistT.add(graphT.adjacentList.get(pos));
        }
    }

    public static void relaxDeparture2(Node src, Node dst,Integer dpt_time){
        int pos = dst.location.identifier();
        System.out.println("DstTime:"+graphT.adjacentList.get(pos).departTime+" srcArrTime:"+src.arriveTime+" DptTime:"+dpt_time);
        if(src.arriveTime != Integer.MIN_VALUE &&
                src.departTime>=dst.departTime &&
                graphT.adjacentList.get(pos).departTime < dpt_time){
            //graphT.adjacentList.get(pos).parent = src;
            graphT.adjacentList.get(pos).departTime = dpt_time;
            //nodelistT.add(graphT.adjacentList.get(pos));
        }
    }

    public static void filterArrivals(){
        //nodelist.add(graph.adjacentList.get(src.identifier()));
       // while (!nodelist.isEmpty()){
       //     Node u = nodelist.poll();
       //     System.out.println("Node="+ u.location.toString());
       //     for (Edge edge:graph.adjacentList.get(u.location.identifier()).edges){
        //        System.out.println("Adjacent="+edge.toString());
        ////        relaxArrival(u,edge.dst,edge.arrTime);
         //   }
       // }
        //System.out.println("Nodelist size = "+nodelist.size());
       // System.out.println("------------------------After Filter-------------------------");
       // graph.printGraph();
    }

    public static void filterArrivals2(){
        MinPriorityQ minPQ = new MinPriorityQ();
        for(Node node: graph.adjacentList){
            minPQ.insert(node);
        }
        while (!minPQ.isEmpty()){
            Node u = minPQ.extract_Min();
            System.out.println("Node="+ u.location.toString());
            for (Edge edge:graph.adjacentList.get(u.location.identifier()).edges){
                System.out.println("Adjacent="+edge.toString());
                relaxArrival2(u,edge.dst,edge.arrTime);
            }
        }
        System.out.println("Nodelist size = "+minPQ.size());
        System.out.println("------------------------After Filter-------------------------");
        graph.printGraph();
    }

/*
    public static void filterDepartures(){
        nodelistT.add(graphT.adjacentList.get(dst.identifier()));
        while (!nodelistT.isEmpty()){
            Node u = nodelistT.poll();
            System.out.println("Node="+ u.location.toString());
            for (Edge edge:graphT.adjacentList.get(u.location.identifier()).edges){
                System.out.println("Adjacent="+edge.toString());
                relaxDeparture(u,edge.dst,edge.dptTime);
            }
        }
        System.out.println("Nodelist size = "+nodelistT.size());
        System.out.println("------------------------After Filter2-------------------------");
        graphT.printGraph();
    }*/

    public static void filterDepartures2(){
        MaxPriorityQ maxPQ = new MaxPriorityQ();
        for(Node node: graphT.adjacentList){
            maxPQ.insert(node);
        }
        while (!maxPQ.isEmpty()){
            Node u = maxPQ.extract_Max();
            System.out.println("Node="+ u.location.toString());
            for (Edge edge:graphT.adjacentList.get(u.location.identifier()).edges){
                System.out.println("Adjacent="+edge.toString());
                relaxDeparture2(u,edge.dst,edge.dptTime);
            }
        }
        System.out.println("Nodelist size = "+maxPQ.size());
        System.out.println("------------------------After Filter2-------------------------");
        graphT.printGraph();
    }



    public static void validateInputs(Set<Location> locations,
                                      Location source, int ts, Location destination, int td,
                                      List<Delivery> deliveries, int d){
        //Validation1:

        //Validation2:

        //Validation3:

        //Validation4:
    }

}