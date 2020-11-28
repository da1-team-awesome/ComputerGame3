import java.util.Random;

/**
 * City class. Represents cities in the game.
 * @author A. Malthe Henriksen
 * @author Gustav Burchardt
 * @since 2.0
 */
public class CapitalCity extends BorderCity {
    /**
     * Constructor for City object.
     *
     * @param name    Name of city.
     * @param value   Value of city.
     * @param country Country of city.
     */
    public CapitalCity(String name, int value, Country country) {
        super(name, value, country);
    }

    @Override
    public int arrive(Player p) {
        Random r = getCountry().getGame().getRandom();
        int beforeSpending = super.arrive(p);
        int moneySpent = r.nextInt(p.getMoney()+beforeSpending+1);
        changeValue(moneySpent);
        return beforeSpending-moneySpent;
    }

}
