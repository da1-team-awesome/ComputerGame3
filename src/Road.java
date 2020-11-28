/**
 * Road class. Represents roads in the game.
 * @author A. Malthe Henriksen
 * @author Gustav Burchardt
 * @since 1.0
 */
public class Road implements Comparable<Road> {
    private City from;
    private City to;
    private int length;

    /**
     * Constructor for road objects.
     * @param from The origin city.
     * @param to The destination city.
     * @param length The length of road.
     */
    public Road (City from, City to, int length) {
        this.from = from;
        this.to = to;
        this.length = length;
    }

    /**
     * Get the destination of this road.
     * @return Destination of road.
     */
    public City getTo() {
        return to;
    }

    /**
     * Get the origin of this road.
     * @return Origin of road.
     */
    public City getFrom() {
        return from;
    }

    /**
     * Get the length of this road.
     * @return Length of road.
     */
    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s : %s", from, to, length);
    }

    @Override
    public int compareTo(Road o) {
        if (!from.equals(o.from)) {
            return from.compareTo(o.from);
        } else if (!to.equals(o.to)) {
            return to.compareTo(o.to);
        }
        return length - o.length;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Road other = (Road) obj;
        return other.from.equals(from) &&
                other.to.equals(to) &&
                length == other.length;
    }

    @Override
    public int hashCode() {
        return 11 * from.hashCode()
                + 17 * to.hashCode()
                + 13 * Integer.valueOf(length).hashCode();
    }
}
