import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CountryTest {

    Country country;

    @Before
    public void setUp() {
        country = new Country("Denmark");
    }

    @Test
    public void constructor() {
        Country constructorCountry = new Country("Denmark");
        assertEquals("Denmark",constructorCountry.getName());
        assertNotNull(constructorCountry.getCities());
        assertEquals(0, constructorCountry.getCities().size());
    }

    @Test
    public void addCity() {
        // Create new city
        City city = new City("namedCity",1,country);
        // Count how many cities are present at the moment
        int cityCount = country.getCities().size();
        // Add the city
        country.addCity(city);
        // Check if count only went up by 1
        assertEquals(cityCount+1,country.getCities().size());
    }

    @Test
    public void getCity() {
        String targetName = "namedCity";
        int targetValue = 1;
        Country targetCountry = country;
        City city = new City(targetName,targetValue,targetCountry);
        country.addCity(city);

        // Test getter
        assertEquals(city, country.getCity(targetName));
    }

    @Test
    public void getRoads() {
        String targetName = "namedCity";
        int targetValue = 1;
        Country targetCountry = country;
        City targetCity1 = new City(targetName,targetValue,targetCountry);
        City targetCity2 = new City("a",2,targetCountry);

        country.addCity(targetCity1);
        country.addCity(targetCity2);
        // Add roads
        country.addRoads(targetCity1,targetCity2,1);
        /// Test getter
        assertEquals(1,country.getRoads(targetCity1).size());
        assertEquals(1,country.getRoads(targetCity2).size());
    }

    @Test
    public void AddRoads() {
        Country foreign = new Country("Dummy country");
        City targetCity1 = new City("namedCity",1,country);
        City targetCity2 = new City("a",2,country);
        City targetCity3 = new City("b",3,foreign);
        City targetCity4 = new City("d",4,foreign);

        assertEquals(0,country.getRoads(targetCity1).size());
        country.addCity(targetCity1);
        country.addCity(targetCity2);

        // Add roads first with negative length
        country.addRoads(targetCity1,targetCity2,0);
        assertEquals(0,country.getRoads(targetCity1).size());
        assertEquals(0,country.getRoads(targetCity2).size());
        country.addRoads(targetCity1,targetCity2,-100);
        assertEquals(0,country.getRoads(targetCity1).size());
        assertEquals(0,country.getRoads(targetCity2).size());

        // Connect a city to itself
        country.addRoads(targetCity1,targetCity1,1);
        assertEquals(0,country.getRoads(targetCity1).size());
        assertEquals(0,country.getRoads(targetCity2).size());

        // Add roads with a valid input
        country.addRoads(targetCity1,targetCity2,1);
        assertEquals(1,country.getRoads(targetCity1).size());
        assertEquals(1,country.getRoads(targetCity2).size());

        // Add roads outside country.
        foreign.addCity(targetCity3);
        foreign.addCity(targetCity4);
        country.addRoads(targetCity3,targetCity4,1);
        assertEquals(0,country.getRoads(targetCity3).size());
        assertEquals(0,country.getRoads(targetCity4).size());

        // Add roads between countries.
        country.addRoads(targetCity1,targetCity4,1);
        assertEquals(2,country.getRoads(targetCity1).size());
        assertEquals(0,country.getRoads(targetCity4).size());

        country.addRoads(targetCity3,targetCity2,1);
        assertEquals(2,country.getRoads(targetCity2).size());
        assertEquals(0,country.getRoads(targetCity3).size());
    }

    @Test
    public void reset() {
        City targetCity1 = new City("namedCity",1,country);
        City targetCity2 = new City("a",2,country);

        country.addCity(targetCity1);
        country.addCity(targetCity2);
        targetCity1.changeValue(1);
        targetCity2.changeValue(1);
        assertEquals(5, country.getCities().stream().mapToInt(City::getValue).sum());
        country.reset();
        assertEquals(3, country.getCities().stream().mapToInt(City::getValue).sum());

    }

    @Test
    public void testToString() {
        assertEquals("Denmark", country.toString());
    }

    @Test
    public void bonus() {
        Game game = new Game(1);
        int n = 100000;
        long sum = 0;
        country.setGame(game);

        // Test that we wont get any bonus for input = 0
        for (int i = 0; i < n; i++) {
            sum+= country.bonus(0);
        }
        assertEquals(0, sum);

        // Test that we wont get any bonus for input = 1
        for (int i = 0; i < n; i++) {
            sum+= country.bonus(1);
        }
        assertEquals(0, sum);

        int seedsToTest = 1;

        // Test several different seed values
        for (long i = 0; i < seedsToTest; i++) {
            int seed = (int)(n*((double)i/n));
            game = new Game(seed);
            country.setGame(game);

            // Check that if we use a certain input, when averaged, it should be 0.5 * input
            for (long j = 2; j < n; j*=2) {
                int input = (int) j;
                sum = 0;
                for (int k = 0; k < 100000; k++) {
                    sum+= country.bonus(2);
                }
                assertNotEquals(0,sum);
                assertTrue(Math.abs((double)sum/300000)-0.5*input < 0.01*input);
            }
        }

    }

    @Test
    public void position() {
        Country foreign = new Country("f");
        City c = new City("a",1,country);
        City c2 = new City("b",1,foreign);
        foreign.addCity(c2);
        country.addCity(c);
        Position p = country.position(c);
        Position p2 = country.position(c2);
        Position validP = new Position(c,c,0);
        Position validP2 = new Position(c2,c2,0);
        assertEquals(p,validP);
        assertEquals(p2,validP2);
        assertNotEquals(p,validP2);
        assertNotEquals(p2,validP);
    }

    @Test
    public void readyToTravel() {
        City c1, c2, c3, c4;
        Country foreign = new Country("f");
        c1 = new City("a",1,country);
        c2 = new City("b",1,country);
        c3 = new City("c",1,foreign);
        c4 = new City("d",1,foreign);

        country.addCity(c1);
        country.addCity(c2);
        foreign.addCity(c3);
        foreign.addCity(c4);

        country.addRoads(c1,c2,1);
        country.addRoads(c1,c3,1);
        country.addRoads(c1,c4,1);

        // case 1
        Position p1 = new Position(c1,c2,1);
        assertEquals(p1,country.readyToTravel(c1,c2));
        // case 2
        Position p2 = new Position(c2,c1,1);
        assertEquals(p2,country.readyToTravel(c2,c1));
        // case 3
        Position p3 = new Position(c2,c2,0);
        assertEquals(p3,country.readyToTravel(c2,c2));
        // case 4
        Position p4 = new Position(c3,c3,0);
        assertEquals(p4,country.readyToTravel(c3,c4));
        // case 5
        Position p5 = new Position(c4,c4,0);
        assertEquals(p5,country.readyToTravel(c4,c3));
        // case 6
        Position p6 = new Position(c4,c4,0);
        assertEquals(p6,foreign.readyToTravel(c4,c3));
    }

}
