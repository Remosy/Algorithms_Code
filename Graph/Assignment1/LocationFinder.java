package assignment1;

import java.util.*;

public class LocationFinder {

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
	private static int[] minArrival;
	private static int[] maxDepart;
	private static Location[] heap;
	private static int[] heapIndex;
	private static int n;
	private static Comparator c;
	
	
	static class minHeapComparator implements Comparator<Location>{

		@Override
		public int compare(Location l1, Location l2) {
			return minArrival[l1.identifier()] - minArrival[l2.identifier()];
		}
		
	}
	
	static class maxHeapComparator implements Comparator<Location>{

		@Override
		public int compare(Location l1, Location l2) {
			return maxDepart[l2.identifier()] - maxDepart[l1.identifier()];
		}
		
	}
	
	public static void swap(int i, int j) {
		int t = heapIndex[heap[i].identifier()];
		heapIndex[heap[i].identifier()] = heapIndex[heap[j].identifier()];
		heapIndex[heap[j].identifier()] = t;
		Location tmp = heap[i];
		heap[i] = heap[j];
		heap[j] = tmp;
	}
	
	public static void insert(Location l) {	
		heap[n] = l;
		heapIndex[l.identifier()] = n;
		popUp(n);
		n++;
	}
	
	public static void heapify(int i) {
		int l,r,m = i,t;
		l = 2 * i + 1;
		r = 2 * i + 2;
		if (l < n && c.compare(heap[l], heap[i]) < 0) {
			m = l;
		}
		if (r < n && c.compare(heap[r], heap[m]) < 0) {
			m = r;
		}
		if (m != i) {
			swap(m,i);
			heapify(m);
		}
	}
	
	public static void popUp(int i) {
		if (i > 0) {
			if (c.compare(heap[i], heap[(i-1) / 2]) < 0) {
				swap(i, (i-1) / 2);
				popUp((i - 1) / 2);
			}
		}
	}
	
	public static Location poll() {
		Location root = heap[0];
		n--;
		swap(0,n);
		heapify(0);
		return root;
	}
	
    public static Set<Location> findLocations(Set<Location> locations,
            Location source, int ts, Location destination, int td,
            List<Delivery> deliveries, int d) {
    	minArrival = new int[locations.size()];
    	maxDepart = new int[locations.size()];
    	List<ArrayList<Delivery>> edges = new ArrayList<ArrayList<Delivery>>();
    	List<ArrayList<Delivery>> edgesT = new ArrayList<ArrayList<Delivery>>();
    	for(int i = 0; i < locations.size(); i++) {
    		edges.add(new ArrayList<Delivery>());
    		edgesT.add(new ArrayList<Delivery>());
    		minArrival[i] = Integer.MAX_VALUE;
    		maxDepart[i] = 0;
    	}
    	minArrival[source.identifier()] = ts;
    	maxDepart[destination.identifier()] = td;
    	for (Delivery deli:deliveries){
    		edges.get(deli.source().identifier()).add(deli);
    	}
    	Collections.reverse(deliveries);
    	for (Delivery deli:deliveries){
    		edgesT.get(deli.destination().identifier()).add(deli);
    	}
    	heap = new Location[locations.size()];
    	heapIndex = new int[locations.size()];
    	c = new minHeapComparator();
    	n = 0;
    	for (Location l:locations) {
    		insert(l);
    	}
    	while (n != 1) {
    		Location current = poll();
    		for(Delivery deli:edges.get(current.identifier())) {
    			if (minArrival[current.identifier()] < deli.departure()) {
    				if (deli.arrival() < minArrival[deli.destination().identifier()]) {
    					minArrival[deli.destination().identifier()] = deli.arrival();
    					popUp(heapIndex[deli.destination().identifier()]);
    				}
    			}
    		}
    	}
    	
    	heap = new Location[locations.size()];
    	heapIndex = new int[locations.size()];
    	c = new maxHeapComparator();
    	n = 0;
    	for (Location l:locations) {
    		insert(l);
    	}
    	while (n != 1) {
    		Location current = poll();
    		for(Delivery deli:edgesT.get(current.identifier())) {
    			if (maxDepart[current.identifier()] > deli.arrival()) {
    				if (deli.departure() > maxDepart[deli.source().identifier()]) {
    					maxDepart[deli.source().identifier()] = deli.departure();
    					popUp(heapIndex[deli.source().identifier()]);
    				}
    			}
    		}
    	}
    	
    	Set<Location> answer = new HashSet<Location>();
    	for (Location loc: locations) {
    		if (loc != source && loc != destination) {
    			if (maxDepart[loc.identifier()] - minArrival[loc.identifier()] >= d) {
    				answer.add(loc);
    			}
    		}
    	}
        return answer; // REMOVE THIS LINE AND IMPLEMENT THIS METHOD
    }

}