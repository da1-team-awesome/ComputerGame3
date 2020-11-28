import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Border city test class. Use for testing border cities.
 * @author A. Malthe Henriksen
 * @author Gustav Burchardt
 * @since 1.0
 */
public class BorderCityTest {
    private Game game;
    private Country country1, country2;
    private City cityA, cityB, cityG;
    private BorderCity cityC, cityD, cityE, cityF;
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

        player = new GUIPlayer(new Position(cityA,cityA,0),500);

        // Create cities
        cityA = new City("City A", 80, country1);
        cityB = new City("City B", 60, country1);
        cityC = new BorderCity("City C", 40, country1);
        cityD = new BorderCity("City D", 100, country1);
        cityE = new BorderCity("City E", 50, country2);
        cityF = new BorderCity("City F", 90, country2);
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
        assertTrue(country1.equals(cityC.getCountry()));
        assertEquals(40, cityC.getInitialValue());
        assertEquals(40, cityC.getValue());
        assertEquals("City C", cityC.getName());
    }

    @Test
    public void arriveFromSameCountry() {
        // This player is from the same country. It shouldn't pay any toll
        // City C is a border city, whilst city A is not.
        Player p = new GUIPlayer(new Position(cityA, cityC, 0), 200);
        int n = 100000;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            p.getFromCountry().getGame().getRandom().setSeed(i);
            // Set the city value to 100
            cityC.changeValue(-cityC.getValue());
            cityC.changeValue(100);
            // Add the sum of arrival bonus
            sum += cityC.arrive(p);
        }
        assertTrue((double) sum / n < 50 * 1.1);
        assertTrue((double) sum / n > 50 * 0.9);

    }

    @Test
    public void arriveFromOtherCountry() {
        Player p;
        int sum;
        int n = 100000;
        // This player is not from the same country. It SHOULD pay a toll.
        p = new GUIPlayer(new Position(cityE,cityC,0),200);
        int tollPercentage = cityE.getCountry().getGame().getSettings().getTollToBePaid();
        int money = p.getMoney();
        sum = 0;
        // Record the resulting values for cities
        int minVal = 100000000;
        int maxVal = 0;

        // Test
        for (int i = 0; i < n; i++) {
            // reset player each time
            p = new GUIPlayer(new Position(cityE,cityC,0),200);
            p.getFromCountry().getGame().getRandom().setSeed(i);
            // Set the city value to 100
            cityC.changeValue(-cityC.getValue());
            cityC.changeValue(100);
            // Add the sum of arrival bonus. If no toll is paid then the average should be 50, so we deduct the average, to get the amount of toll paid.
            sum+= cityC.arrive(p) - 50;

            if (minVal > cityC.getValue()) {
                minVal = cityC.getValue();
            }
            if (maxVal < cityC.getValue()) {
                maxVal = cityC.getValue();
            }

        }
        // Check the city values didn't exceed expected minimums or maximums
        assertNotEquals(40,minVal);
        assertNotEquals(141,maxVal);

        // Check we paid the correct toll. The paid toll should be -(Money*Toll%)
        assertTrue((double)sum/n < -(money * tollPercentage * 0.01)*0.9);
        assertTrue((double)sum/n > -(money * tollPercentage * 0.01)*1.1);
    }
}
