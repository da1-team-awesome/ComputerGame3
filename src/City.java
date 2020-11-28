import java.util.Random;

/**
 * City class. Represents cities in the game.
 * @author A. Malthe Henriksen
 * @author Gustav Burchardt
 * @since 1.0
 */
public class City implements Comparable<City> {
    private String name;
    private int value;
    private int initialValue;
    private Country country;

    /**
     * Constructor for City object.
     * @param name Name of city.
     * @param value Value of city.
     * @param country Country of city.
     */
    public City(String name, int value, Country country) {
        this.name = name;
        this.value = value;
        initialValue = value;
        this.country = country;
    }

    /**
     * Changes the value of the city by a specified amount.
     * @param amount The value of change.
     */
    public void changeValue(int amount) {
        value += amount;
    }

    /**
     * Resets city value to initial value.
     */
    public void reset() {
        value = initialValue;
    }

    /**
     * The name of this city.
     * @return Name of city.
     */
    public String getName() {
        return name;
    }

    /**
     * The country of this city.
     * @return Country of city.
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Arrive at this city.
     * @return The bonus for arriving.
     */
    public int arrive() {
        int bonus = country.bonus(value);

        if (bonus > 0) {
            value -= bonus;
        }

        return bonus;
    }

    public int arrive(Player p) {
        return arrive();
    }

    /**
     * Get the initial value of this city.
     * @return Initial value of city.
     */
    public int getInitialValue() {
        return initialValue;
    }

    /**
     * Get the value of this city.
     * @return Value of city.
     */
    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(City other) {
        return name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        City other = (City) obj;
        return name.equals(other.name) && country.equals(other.country);
    }

    @Override
    public int hashCode() {
        return 11 * name.hashCode() + 13 * country.hashCode();
    }
}
