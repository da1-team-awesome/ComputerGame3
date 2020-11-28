
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
        double money = p.getMoney();
        Country fromCountry = p.getFromCountry();
        int tollPercentage = fromCountry.getGame().getSettings().getTollToBePaid();
        double toll = Math.floor(tollPercentage * 0.01 * money);
        changeValue((int)toll);
        return fromCountry.bonus(getValue()) - (int)toll;
    }
}
