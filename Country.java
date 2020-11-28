import java.util.*;

/**
 * Country class. Represents a country in the game.
 * @author A. Malthe Henriksen
 * @author Gustav Burchardt
 * @since 1.0
 */
public class Country {
    private String name;
    private Random random;
    private Game game;
    /**
     * Maps the cities in the country with a set of roads, that is connected to them.
     */
    private Map<City, Set<Road>> network;

    /**
     * Constructor for Country object.
     * @param name The name of country.
     */
    public Country (String name) {
        this.name = name;
        network = new TreeMap<>();
        random = new Random();
    }

    /**
     * Returns the cities in this country as a set.
     * @return Set of cities.
     */
    public Set<City> getCities () {
        return network.keySet();
    }

    /**
     * Returns a city object with a specified name.
     * @param name Name of city to retrieve.
     * @return The specified city object.
     */
    public City getCity (String name) {
        return network.keySet().stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Sets the Game object.
     * @param game Game object to set.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Returns the Game object.
     * @return Game Object.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Returns the roads connected to a specified city.
     * @param c The city, which roads to retrieve.
     * @return The set of roads connecting to the city specified.
     */
    public Set<Road> getRoads (City c) {
        if (network.containsKey(c)) {
            return network.get(c);
        }
        return new TreeSet<>();
    }

    /**
     * Adds a city to a country. No roads are connected to the city.
     * @param c The city to add.
     */
    public void addCity(City c) {
        network.put(c,new TreeSet<>());
    }

    /**
     * Adds roads between two cities in the country's network.
     * @param a City A.
     * @param b City B.
     * @param length The distance between city A and city B.
     */
    public void addRoads(City a, City b, int length) {
        if (length <=0 || a.equals(b)) {
            // Length is too short or same city
            return;
        }
        Road roadA = new Road(a,b,length); // Road from A to B
        Road roadB = new Road(b,a,length); // Road from B to A

        if (a.getCountry().equals(this)) {
            if (getCity(a.getName()) != null) {
                getRoads(a).add(roadA);
            }
        }
        if (b.getCountry().equals(this)) {
            if (getCity(b.getName()) != null) {
                getRoads(b).add(roadB);
            }
        }
    }

    /**
     * Ready the player for travel.
     * @param from The origin of travel.
     * @param to The destination of travel.
     * @return Returns the position of the player.
     */
    public Position readyToTravel(City from, City to) {
        if (from.equals(to)) {
            // Same city.
            return position(from);
        }
        if (!from.getCountry().equals(this)) {
            // 'From' not in this country.
            return position(from);
        }
        Set<Road> roadsFrom = getRoads(from);
        Optional<Road> result = roadsFrom.stream().filter(road -> road.getTo().equals(to)).findFirst();
        Road roadToTravel = result.orElse(null);
        if (roadToTravel == null) {
            // No direct road between cities.
            return position(from);
        }
        return new Position(from,to,roadToTravel.getLength());
    }

    /**
     * Get the position object of a specified city.
     * @param c The city to get position of.
     * @return Position of city.
     */
    public Position position(City c) {
        return new Position(c,c,0);
    }

    /**
     * Resets the network of roads and cities to an empty map.
     */
    public void reset () {
        network.keySet().forEach(City::reset);
    }

    /**
     * Generate a bonus for this country.
     * @param value The value of the bonus.
     * @return A randomized bonus value.
     */
    public int bonus (int value) {
        if (value > 0) {
            return game.getRandom().nextInt(value);
        }
        return 0;
    }

    /**
     * Get the name of country.
     * @return Name of country.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Country other = (Country) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
