package assignment1.test;

import assignment1.*;
import java.util.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Basic tests for the {@link LocationFinder} implementation class.
 * 
 * We will use a more comprehensive test suite to test your code, so you should
 * add your own tests to this test suite to help you to debug your
 * implementation.
 */
public class LocationFinderTest {

    @Test
    public void basicTest() {
        // number of locations in the postal network
        int n = 6;
        // create n locations so that location.get(i) has identifier i
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            locations.add(new Location(i));
        }
        // the postal location P_s and time t_s
        Location source = locations.get(0);
        int ts = 2;
        // the postal location P_d and time t_d
        Location destination = locations.get(4);
        int td = 20;
        // the delay d
        int d = 3;

        // log of deliveries that departed after ts and arrived before td
        List<Delivery> log = new ArrayList<>();
        log.add(getDelivery(locations, 0, 1, 3, 4));
        log.add(getDelivery(locations, 5, 4, 4, 5));
        log.add(getDelivery(locations, 1, 2, 5, 7));
        log.add(getDelivery(locations, 0, 1, 10, 12));
        log.add(getDelivery(locations, 0, 3, 10, 16));
        log.add(getDelivery(locations, 2, 1, 10, 11));
        log.add(getDelivery(locations, 0, 3, 12, 14));
        log.add(getDelivery(locations, 1, 4, 13, 16));
        log.add(getDelivery(locations, 3, 4, 16, 19));
        Collections.sort(log); // sort the deliveries by departure time

        // the locations that are expected by LocationFinder.findLocations
        Set<Location> locationsExpected = new HashSet<>();
        locationsExpected.add(locations.get(1));
        locationsExpected.add(locations.get(2));

        // the locations actually returned by LocationFinder.findLocations
        Set<Location> locationsActual = LocationFinder.findLocations(
                new HashSet<>(locations), source, ts, destination, td, log, d);

        // compare the actual and expected outputs
        Assert.assertEquals(locationsExpected, locationsActual);
    }

    /*---Helper methods--------------------*/

    /**
     * Creates and returns a delivery from the ith location in locations to the
     * jth location in locations, departing at time departure and arriving at
     * time arrival.
     */
    private static Delivery getDelivery(List<Location> locations, int source,
            int destination, int departure, int arrival) {
        return new Delivery(locations.get(source), locations.get(destination),
                departure, arrival);
    }

}