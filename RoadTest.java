import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import java.util.*;

public class RoadTest {
    private City c1;
    private City c2;
    private Country country;

    @Before
    public void setUp () {
        country = new Country("Denmark");
        c1 = new City("Aarhus", 100, country);
        c2 = new City("Copenhagen", 150, country);
    }

    /**
     * Tests that the constructor does not throw
     */
    @Test
    public void createRoad() {
        Road road = new Road(c1, c2, 300);

        assertTrue(road.getFrom().equals(c1));
        assertTrue(road.getTo().equals(c2));
        assertEquals(300, road.getLength());

        assertEquals("Aarhus (100)", road.getFrom().toString());
        assertEquals("Copenhagen (150)", road.getTo().toString());
    }

    /**
     * Tests that the toString method returns the correct string
     */
    @Test
    public void testToString() {
        Road road = new Road(c1, c2, 200);
        assertEquals("Aarhus (100) -> Copenhagen (150) : 200", road.toString());

        road = new Road(c2, c1, 400);
        assertEquals("Copenhagen (150) -> Aarhus (100) : 400", road.toString());
    }
}