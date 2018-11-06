package assignment1;

import java.util.*;

public class LocationFinder {
    static Graph graph;     //graph
    static Graph graphT;    //transposed graph
    static Location src;    //source location
    static Location dst;    //destination location
    static int delay;

    /**
     * @require The set of locations on the postal network, locations, is not
     * null and each location in the set of locations is not null and
     * has a unique identifier in the range [0, locations.size()-1].
     * <p>
     * The set of locations contains the source and destination
     * locations, and those two locations are not equal.
     * <p>
     * The time after which the package departed the source location,
     * ts, is non-negative (i.e. 0 <= ts). The time that the package
     * arrived at the destination location by, td, is later than ts
     * (i.e. ts < td).
     * <p>
     * The deliveries in the deliveries log are sorted in ascending
     * order by their departure times.
     * <p>
     * For each delivery x in the deliveries, the source and
     * destination of x are in the set of locations, and ts <
     * x.departure() < x.arrival() < td.
     * <p>
     * The delay d is a positive integer (i.e. 0 < d).
     * @ensure Returns the set of locations on the postal network such that the
     * package may have departed the source location at some time after
     * ts, and arrived at the destination location at some time before
     * td, having been routed through the location (using the given
     * deliveries), with a delay in that location of at least d time
     * units between two deliveries. (See the assignment handout for
     * details.)
     */
    public static Set<Location> findLocations(Set<Location> locations,
                                              Location source, int ts, Location destination, int td,
                                              List<Delivery> deliveries, int d) {
        //Check the requirements !!! Assume inputs are correct
        //validation(locations,source,ts,destination,td,deliveries,d);

        //Assign prior knowledge by inputs
        src = source;
        dst = destination;
        delay = d;

        //Construct graph
        graph = new Graph(locations.size());
        for (Location location : locations) {
            Node node = new Node(location);
            if (location == source) {
                node.arriveTime = ts;
            } else if (location == destination) {
                node.departTime = td;
            }
            graph.addNode(node);
        }
        for (Delivery delivery : deliveries) {
            Node src = graph.adjacentList.get(delivery.source().identifier());
            Node dst = graph.adjacentList.get(delivery.destination().identifier());
            Edge edge = new Edge(src, dst, delivery.departure(), delivery.arrival());
            graph.addEdge(src.location.identifier(), edge);
        }

        //Process
        filterArrivals();
        graphT = transposeGraph(graph, deliveries);
        filterDepartures();

        //Get final results
        Set<Location> reults = monitorNodes(graphT.adjacentList);
        return reults;
    }

    /**
     * Stop immediately when meet an invalidated requirement
     * @param  locations   an set of locations
     * @param  source      source location
     * @param  destination destination location
     * @param  ts          The time before delivering
     * @param  td          The time before after finishing all deliveries
     * @param  deliveries  a log of deliveries
     * @param  d           delay time
     * @return             a set of postal locations
     */
    public static void validation(Set<Location> locations,
                                  Location source, int ts, Location destination, int td,
                                  List<Delivery> deliveries, int d){
        int oldTime = 0;

        if(d<=0 || ts < 0 || ts >= td){
            System.exit(1);
        }

        if(locations.isEmpty() || deliveries.isEmpty()){
            System.exit(1);
        }

        if(!locations.contains(source)||!locations.contains(destination)){
            System.exit(1);
        }

        for(Location loc:locations){
            if(loc == null){
                System.exit(1);
            }
            if(loc.identifier()<0 || loc.identifier()>locations.size()-1){
                System.exit(1);
            }
        }
        for(Delivery delivery:deliveries){
            if(delivery.departure()<oldTime){
                System.exit(1);
            }
            if(delivery == null){
                System.exit(1);
            }
            if(!locations.contains(delivery.source())|| !locations.contains(delivery.destination())){
                System.exit(1);
            }
            if(ts >= delivery.departure() || delivery.departure()>=delivery.arrival() || delivery.arrival()>=td){
                System.exit(1);
            }
            oldTime = delivery.departure();
        }

        if(source.equals(destination)){
            System.exit(1);
        }
    }

    /**
     * Only pick the locations which have equal or longer delay
     * @param  graphNodes  an saved arraylist of graph nodes
     * @return             a set of postal locations
     */
    public static Set<Location> monitorNodes(ArrayList<Node> graphNodes) {
        Set<Location> results = new HashSet<Location>();
        for (Node node : graphNodes) {
            if (node.location != src && node.location != dst
                    && node.departTime - node.arriveTime >= delay) {
                results.add(node.location);
            }
        }
        return results;
    }

    /**
     * Construct a new transposed graph
     * @param  graph       a graph without transposing
     * @param  deliveries  a list of deliveries
     * @return             a graph has been transposed
     */
    public static Graph transposeGraph(Graph graph, List<Delivery> deliveries) {
        Graph graphT = graph;
        for (Node node : graphT.adjacentList) {
            node.edges = new ArrayList<>();
        }
        for (Delivery delivery : deliveries) {
            Node src = graph.adjacentList.get(delivery.source().identifier());
            Node dst = graph.adjacentList.get(delivery.destination().identifier());
            Edge edge = new Edge(dst, src, delivery.departure(), delivery.arrival());
            graphT.addEdge(dst.location.identifier(), edge);
        }
        return graphT;
    }

    /**
     * Updated the arrival time for a adjacent node of the "src" Node
     * @param  src       The source location node of an edge
     * @param  dst       The destination location node of an edge
     * @param  src_time  The arrival time got from a delivery
     */
    public static void relaxArrival(Node src, Node dst, Integer src_time) {
        int pos = dst.location.identifier();
        if (src.arriveTime <= src_time &&
                graph.adjacentList.get(pos).arriveTime > src_time
                ) {
            graph.adjacentList.get(pos).arriveTime = src_time;
        }
    }

    /**
     * Updated the departure time for a adjacent node of the "src" Node
     * @param  src       The source location node of an edge
     * @param  dst       The destination location node of an edge
     * @param  dpt_time  The departure time got from a delivery
     */
    public static void relaxDeparture(Node src, Node dst, Integer dpt_time) {
        int pos = dst.location.identifier();
        if (src.arriveTime != Integer.MIN_VALUE &&
                src.departTime >= dst.departTime &&
                graphT.adjacentList.get(pos).departTime < dpt_time) {
            graphT.adjacentList.get(pos).departTime = dpt_time;
        }
    }

    /**
     * To obtain the final arrival time of all graph nodes from whole deliveries
     */
    public static void filterArrivals() {
        MinPriorityQ minPQ = new MinPriorityQ();
        for (Node node : graph.adjacentList) {
            minPQ.insert(node);
        }
        while (!minPQ.isEmpty()) {
            Node u = minPQ.extract_Min();
            for (Edge edge : graph.adjacentList.get(u.location.identifier()).edges) {
                relaxArrival(u, edge.dst, edge.arrTime);
            }
        }
    }

    /**
     * To obtain the final departure time of all graph nodes from whole deliveries
     */
    public static void filterDepartures() {
        MaxPriorityQ maxPQ = new MaxPriorityQ();
        for (Node node : graphT.adjacentList) {
            maxPQ.insert(node);
        }
        while (!maxPQ.isEmpty()) {
            Node u = maxPQ.extract_Max();
            for (Edge edge : graphT.adjacentList.get(u.location.identifier()).edges) {
                relaxDeparture(u, edge.dst, edge.dptTime);
            }
        }
    }

}