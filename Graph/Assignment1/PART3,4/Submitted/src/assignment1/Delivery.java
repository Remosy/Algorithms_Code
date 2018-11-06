package assignment1;

/**
 * An immutable representation of a delivery from one location on the postal
 * network to another. A delivery has a source location, where the delivery came
 * from, and a destination location, that the delivery was made to, as well as a
 * departure time of the delivery from the source location and an arrival time
 * of the delivery at the destination location. Both the departure and arrival
 * time are non-negative integers and the arrival time must be strictly greater
 * than the departure time.
 * 
 * Any two instances of the Delivery class are equal if they have equal source
 * locations, equal end locations, equal departure times and equal arrival
 * times.
 */
public class Delivery implements Comparable<Delivery> {

    // the source location of the delivery
    private Location source;
    // the destination location of the delivery
    private Location destination;
    // the departure time of the delivery from the source location
    private int departure;
    // the arrival time of the delivery at the destination location
    private int arrival;

    /*
     * class invariant:
     * 
     * source != null && destination != null && 0 <= departure < arrival
     */

    /**
     * @require source!=null && destination!=null && 0 <= departure < arrival
     * @ensure creates a new delivery from the source location at the given
     *         departure time, to the destination location at the given arrival
     *         time.
     */
    public Delivery(Location source, Location destination, int departure,
            int arrival) {
        this.source = source;
        this.destination = destination;
        this.departure = departure;
        this.arrival = arrival;
    }

    /**
     * @ensure Returns the source location of the delivery.
     */
    public Location source() {
        return source;
    }

    /**
     * @ensure Returns the destination location of the delivery.
     */
    public Location destination() {
        return destination;
    }

    /**
     * @ensure Returns the departure time of the delivery from the source
     *         location.
     */
    public int departure() {
        return departure;
    }

    /**
     * @ensure Returns the arrival time of the delivery at the destination
     *         location.
     */
    public int arrival() {
        return arrival;
    }

    @Override
    public String toString() {
        return "(" + source.toString() + ", " + destination.toString() + ", "
                + departure + ", " + arrival + ")";
    }

    /**
     * Compares deliveries based on the ascending order of their departure time.
     */
    @Override
    public int compareTo(Delivery other) {
        return this.departure - other.departure;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Delivery)) {
            return false;
        }
        Delivery other = (Delivery) object;
        return (source.equals(other.source)
                && destination.equals(other.destination)
                && departure == other.departure && arrival == other.arrival);
    }

    @Override
    public int hashCode() {
        int prime = 7;
        int result = source.hashCode();
        result = prime * result + destination.hashCode();
        result = prime * result + departure;
        result = prime * result + arrival;
        return result;
    }

}
