/**
 * Test class for Mafia Country
 * @author A. Malthe Henriksen
 * @author Gustav Burchardt
 * @since 1.0
 */

public class MafiaCountry extends Country {
    /**
     * Construct a new MafiaCountry object
     * @param name The name of the country
     */
    public MafiaCountry (String name) {
        super(name);
    }

    /**
     * If the player is unlucky, a negative loss will be returned. Otherwise a positive bonus is returned.
     * @param value The value of the bonus.
     * @return Integer containing bonus or loss
     */
    @Override
    public int bonus (int value) {
        int risk = getGame().getSettings().getRisk();

        if (getGame().getRandom().nextInt(100) < risk) {
            return -getGame().getLoss();
        }
        return super.bonus(value);
    }
}
