package assignment1;

/**
 * An immutable representation of a location on the postal network. Such a
 * location has a unique identifier. Two locations are equal if they have the
 * same identifier.
 */
public class Location {

    // the unique identifier of the postal network location
    private int identifier;

    /**
     * Creates a new postal network location with the given identifier.
     */
    public Location(int identifier) {
        this.identifier = identifier;
    }

    /**
     * Returns the identifier of the location on the postal network.
     */
    public int identifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "P" + identifier;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        return (this.identifier == other.identifier);
    }

    @Override
    public int hashCode() {
        return identifier;
    }
}
