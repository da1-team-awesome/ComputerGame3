import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CityTest {
    private Game game;
    private Country country;
    private City c;

    @Before
    public void setUp () {
        game = new Game();
        country = new Country("Denmark");
        country.setGame(game);
        c = new City("Aarhus", 100, country);
    }

    @Test
    public void testConstructor () {
        assertTrue(country.equals(c.getCountry()));

        assertEquals(100, c.getInitialValue());
        assertEquals(100, c.getValue());
        assertEquals("Aarhus", c.getName());
    }

    @Test
    public void changeValue() {
        assertEquals(100, c.getValue());

        c.changeValue(50);

        assertEquals(150, c.getValue());
        assertEquals(100, c.getInitialValue());
    }

    @Test
    public void reset() {
        assertEquals(100, c.getValue());

        c.changeValue(50);

        assertEquals(150, c.getValue());
        assertEquals(100, c.getInitialValue());

        c.reset();

        assertEquals(100, c.getValue());
        assertEquals(100, c.getInitialValue());
    }

    @Test
    public void arrive() {
        for (int i = 0; i < 1000; i++) {
            game.getRandom().setSeed(i);
            int bonus = country.bonus(100);
            game.getRandom().setSeed(i);
            assertEquals(bonus, c.arrive());
            assertEquals(100 - bonus, c.getValue());
            c.reset();
        }
    }

    @Test
    public void testToString() {
        assertEquals("Aarhus (100)", c.toString());
    }
}