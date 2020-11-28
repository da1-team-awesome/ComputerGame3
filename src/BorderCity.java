/**
 * Border city class. Represents a border city in the game.
 * @author A. Malthe Henriksen
 * @author Gustav Burchardt
 * @since 2.0
 */
public class BorderCity extends City {
    /**
     * Constructor for City object.
     *
     * @param name    Name of city.
     * @param value   Value of city.
     * @param country Country of city.
     */
    public BorderCity(String name, int value, Country country) {
        super(name, value, country);
    }

    @Override
    public int arrive(Player p) {
        // Get player money and from country
        int money = p.getMoney();
        Country fromCountry = p.getFromCountry();

        if (fromCountry.equals(getCountry())) {
            // Player is from the same country. Don't pay toll
            return arrive();
        }
        // Player is NOT from the same country. Do pay toll
        // 0-100 toll percentage
        int tollPercentage = fromCountry.getGame().getSettings().getTollToBePaid();
        // Convert to decimals
        double decimalPercentage = (double)tollPercentage/100;
        // Calculate the toll from the player's fortune.
        int toll = (int) Math.floor((double)money * decimalPercentage);

        // Calculate total
        int total = arrive() - toll;

        // Add toll to value
        changeValue(toll);

        return total;
    }
}
