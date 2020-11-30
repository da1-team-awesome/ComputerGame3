/**
 * Test class for City
 * @author Gustav Burchardt
 * @author A. Malthe Henriksen
 * @since 1.0
 */

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CityTest {
    private Game game;
    private Country country;
    private Country mafiaCountry;
    private City c1;
    private City c2;

    @Before
    public void setUp () {
        game = new Game();
        country = new Country("Denmark");
        country.setGame(game);
        mafiaCountry = new MafiaCountry("Sweden");
        mafiaCountry.setGame(game);
        c1 = new City("Aarhus", 100, country);
        c2 = new City("Aarhus", 100, mafiaCountry);
    }

    @Test
    public void testConstructor () {
        assertTrue(country.equals(c1.getCountry()));

        assertEquals(100, c1.getInitialValue());
        assertEquals(100, c1.getValue());
        assertEquals("Aarhus", c1.getName());
    }

    @Test
    public void changeValue() {
        assertEquals(100, c1.getValue());

        c1.changeValue(50);

        assertEquals(150, c1.getValue());
        assertEquals(100, c1.getInitialValue());
    }

    @Test
    public void reset() {
        assertEquals(100, c1.getValue());

        c1.changeValue(50);

        assertEquals(150, c1.getValue());
        assertEquals(100, c1.getInitialValue());

        c1.reset();

        assertEquals(100, c1.getValue());
        assertEquals(100, c1.getInitialValue());
    }

    @Test
    public void arrive() {
        // Test normal country
        for (int i = 0; i < 1000; i++) {
            game.getRandom().setSeed(i);
            int bonus = country.bonus(100);
            game.getRandom().setSeed(i);
            assertEquals(bonus, c1.arrive());
            assertEquals(100 - bonus, c1.getValue());
            c1.reset();
        }

        // Test mafia country
        for (int i = 0; i < 1000; i++) {
            game.getRandom().setSeed(i);
            int bonus = mafiaCountry.bonus(100);
            game.getRandom().setSeed(i);
            assertEquals(bonus, c2.arrive());
            if (bonus >= 0) {
                assertEquals(100 - bonus, c2.getValue());
            } else {
                assertEquals(100, c2.getValue());
            }
            c2.reset();
        }
    }

    @Test
    public void testToString() {
        assertEquals("Aarhus (100)", c1.toString());
    }
}