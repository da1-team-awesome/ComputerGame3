import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Capital city test class. Use for testing border cities.
 * @author A. Malthe Henriksen
 * @author Gustav Burchardt
 * @since 1.0
 */
public class CapitalCityTest {
    private Game game;
    private Country country1, country2;
    private City cityA, cityB, cityC,cityF, cityG;
    private City cityD,cityE;
    private GUIPlayer player;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        game = new Game(456);
        // Create countries
        country1 = new Country("Country 1");
        country2 = new Country("Country 2");

        country1.setGame(game);
        country2.setGame(game);

        // Create cities
        cityA = new City("City A", 80, country1);
        cityB = new City("City B", 60, country1);
        cityC = new City("City C", 40, country1);
        cityD = new CapitalCity("City D", 100, country1);
        cityE = new CapitalCity("City E", 50, country2);
        cityF = new City("City F", 90, country2);
        cityG = new City("City G", 70, country2);

        // Connect cities to countries
        country1.addCity(cityA);
        country1.addCity(cityB);
        country1.addCity(cityC);
        country1.addCity(cityD);

        country2.addCity(cityE);
        country2.addCity(cityF);
        country2.addCity(cityG);

        // Create roads
        country1.addRoads(cityA, cityB, 4);
        country1.addRoads(cityA, cityC, 3);
        country1.addRoads(cityA, cityD, 5);
        country1.addRoads(cityB, cityD, 2);
        country1.addRoads(cityC, cityD, 2);
        country1.addRoads(cityC, cityE, 4);
        country1.addRoads(cityD, cityF, 3);
        country2.addRoads(cityE, cityC, 4);
        country2.addRoads(cityE, cityF, 2);
        country2.addRoads(cityE, cityG, 5);
        country2.addRoads(cityF, cityD, 3);
        country2.addRoads(cityF, cityG, 6);
    }

    @Test
    public void constructor() {
        assertTrue(country1.equals(cityD.getCountry()));
        assertEquals(100, cityD.getInitialValue());
        assertEquals(100, cityD.getValue());
        assertEquals("City D", cityD.getName());
    }

    @Test
    public void arriveFromSameCountry() {
        int n  = 100000;

        int sum = 0;
        for (int i = 0; i < n; i++) {
            // Set seed
            country1.getGame().getRandom().setSeed(i);
            // Create player with 100 eur.
            GUIPlayer p = new GUIPlayer(new Position(cityA,cityD,0),100);
            // Set value of city to 100 eur.
            cityD.changeValue(-cityD.getValue());
            cityD.changeValue(100);
            int arrivalBonus = cityD.arrive(p);
            assertEquals(100-arrivalBonus,cityD.getValue());
            sum+=arrivalBonus;
        }

        // When arriving at a city we recieve at a city of 100 value, we get an average of 50 bonus.
        // Which means the average we spend is 75, since the player has 100 to begin with, which yields an average of 150.
        // The bonus is setup, such we get a bonus, and deduct the money we spent at the capital.
        // The average bonus for arriving is 50, and the average spending is 75, because we can spend the bonus we get when arriving.
        // This leads to an average bonus of -25
        assertEquals(-25, sum/n);
    }
    @Test
    public void arriveFromOtherCountry() {
        int n  = 100000;

        int sum = 0;
        for (int i = 0; i < n; i++) {
            // Set seed
            country1.getGame().getRandom().setSeed(i);
            // Create player with 100 eur.
            GUIPlayer p = new GUIPlayer(new Position(cityF,cityD,0),100);
            // Set value of city to 100 eur.
            cityD.changeValue(-cityD.getValue());
            cityD.changeValue(100);
            int arrivalBonus = cityD.arrive(p);
            assertEquals(100-arrivalBonus,cityD.getValue());
            sum+=arrivalBonus;
        }

        // Same as before, but now we add the toll. The toll of a player with 100 is 20(20%).
        // Now the bonus for arriving is 50-20=30, and the spending average is 130/2 = 65.
        // This leads to an average bonus of -35, since 30-65=-35
        assertEquals(-35, sum/n);
    }


}