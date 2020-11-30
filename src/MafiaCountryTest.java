/**
 * Test class for Mafia Country
 * @author A. Malthe Henriksen
 * @author Gustav Burchardt
 * @since 1.0
 */

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class MafiaCountryTest {
    private Game game;
    private Country country1, country2;
    private City cityA, cityB, cityC, cityD, cityE, cityF, cityG;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        game = new Game();

        // Create countries
        country1 = new Country("Country 1");
        country2 = new MafiaCountry("Country 2");

        country1.setGame(game);
        country2.setGame(game);

        // Create cities
        cityA = new City("City A", 80, country1);
        cityB = new City("City B", 60, country1);
        cityC = new City("City C", 40, country1);
        cityD = new City("City D", 100, country1);
        cityE = new City("City E", 50, country2);
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

    /**
     * Testing the bonus method
     */
    @Test
    public void bonus() {
        int n = 10000;
        double timesRobbed = 0;
        double totalLoss = 0;
        Set<Integer> usedLosses = new TreeSet<>();

        for (int i = 0; i < n; i++) {
            game.getRandom().setSeed(i);
            int risk = game.getSettings().getRisk();
            int chance = game.getRandom().nextInt(100);
            int loss = game.getLoss();
            game.getRandom().setSeed(i);

            int bonus = country2.bonus(10);

            if (bonus < 0) {
                assertTrue(chance < risk);
                timesRobbed++;
                totalLoss -= bonus;
                usedLosses.add(-bonus);
                assertEquals(loss, -bonus);
            } else {
                assertTrue(chance >= risk);
            }
        }

        // Robbed 20% of the times
        double percentageRobbed = timesRobbed / n * 100;
        assertTrue(Math.abs(percentageRobbed - 20) < 1);

        // Average loss should be $30
        double averageLoss = totalLoss / timesRobbed;
        assertTrue(Math.abs(averageLoss - 30) < 1);

        // Should give all values from 10 to 50 (a total of 41)
        assertEquals(41, usedLosses.size());

        // We also need to check that the 41 possible values are in the interval [10,50]
        int min = Collections.min(usedLosses);
        int max = Collections.max(usedLosses);
        assertEquals(10, min);
        assertEquals(50, max);
    }
}