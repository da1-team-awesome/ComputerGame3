import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {
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
     * Tests that the constructor works as expected
     */
    @Test
    public void testConstructor () {
        Position pos = new Position(c1, c2, 300);

        assertEquals("Aarhus (100)", pos.getFrom().toString());
        assertEquals("Copenhagen (150)", pos.getTo().toString());

        assertTrue(c1.equals(pos.getFrom()));
        assertTrue(c2.equals(pos.getTo()));

        assertEquals(300, pos.getDistance());
        assertEquals(300, pos.getTotal());
    }

    @Test
    public void move() {
        Position pos = new Position(c1, c2, 300);

        assertEquals(300, pos.getDistance());

        boolean hasMoved = pos.move();

        assertEquals(299, pos.getDistance());
        assertTrue(hasMoved);

        pos = new Position(c1, c2, 0);

        hasMoved = pos.move();

        assertFalse(hasMoved);
        assertEquals(0, pos.getDistance());
        assertEquals(0, pos.getDistance());
    }

    @Test
    public void turnAround() {
        Position pos = new Position(c1, c2, 300);

        assertTrue(c1.equals(pos.getFrom()));
        assertTrue(c2.equals(pos.getTo()));
        assertEquals(300, pos.getDistance());

        pos.turnAround();

        assertTrue(c2.equals(pos.getFrom()));
        assertTrue(c1.equals(pos.getTo()));
        assertEquals(0, pos.getDistance());
    }

    @Test
    public void hasArrived() {
        Position pos = new Position(c1, c2, 300);

        assertFalse(pos.hasArrived());

        pos = new Position(c1, c2, 0);

        assertTrue(pos.hasArrived());
    }

    @Test
    public void testToString() {
        Position pos = new Position(c1, c2, 300);

        assertEquals("Aarhus (100) -> Copenhagen (150) : 300/300", pos.toString());
    }
}