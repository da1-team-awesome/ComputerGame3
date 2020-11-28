/**
 * Position class. Represents the position of a player.
 * @author A. Malthe Henriksen
 * @author Gustav Burchardt
 * @since 1.0
 */
public class Position {
    private City from;
    private City to;
    private int distance;
    private int total;

    /**
     * Constructor for position object.
     * @param from The origin of this player.
     * @param to The destination of this player.
     * @param distance The distance from origin to destination player is to move.
     */
    public Position (City from, City to, int distance) {
        total = distance;
        this.distance = distance;
        this.from = from;
        this.to = to;
    }

    /**
     * Move the player towards the destination.
     * @return Whether or not the player has moved.
     */
    public boolean move () {
        boolean moved = false;
        if (distance > 0) {
            distance--;
            moved = true;
        }
        return moved;
    }

    /**
     * Swap destination and origin to turn around the player.
     */
    public void turnAround () {
        City temp = from;
        from = to;
        to = temp;

        distance = total - distance;
    }

    /**
     * Check if the player has arrived at destination.
     * @return Whether or not player has arrived at destination.
     */
    public boolean hasArrived () {
        return distance == 0;
    }

    /**
     * Get the origin of position object.
     * @return Origin of position.
     */
    public City getFrom() {
        return from;
    }

    /**
     * Get the destination of position object.
     * @return Destination of position.
     */
    public City getTo() {
        return to;
    }

    /**
     * Get the total distance between cities, regardless of how far the player has travelled.
     * @return Total distance between cities.
     */
    public int getTotal() {
        return total;
    }

    /**
     * The remaining distance to destination.
     * @return Distance to destination
     */
    public int getDistance() {
        return distance;
    }

    @Override
    public String toString () {
        return String.format("%s -> %s : %s/%s", from, to, distance, total);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Position other = (Position) obj;
        return other.from.equals(from) &&
                other.to.equals(to) &&
                other.distance == distance &&
                other.total == total;
    }

    @Override
    public int hashCode() {
        return 11 * from.hashCode()
                + 13 * to.hashCode()
                + 17 * Integer.valueOf(distance).hashCode()
                + 23 * Integer.valueOf(total).hashCode();
    }
}
