import java.io.IOException;
import java.net.URISyntaxException;

public class TestDriver {
    public static void main(String[] args) {
        try {
            TestServer.test("CG3-1");
        } catch (Exception e) {
            System.out.println(e);
        }

        // GUI.createGameBoard();
//        Country dk = new Country("Denmark");
//
//        City aarhus = new City("Aarhus", 50, dk);
//
//        dk.addCity(aarhus);
//
//        City odense = new City("Odense", 40, dk);
//
//        dk.addCity(odense);
//
//        dk.addRoads(aarhus, odense, 100);
//
//        System.out.println(dk.getCities());
//        System.out.println(dk.getRoads(aarhus));
//        System.out.println(dk.getRoads(odense));
//
//        City aarhus2 = new City("Aarhus", 50, dk);
//
//        System.out.println(aarhus.equals(aarhus2));
//
//        System.out.println(aarhus.hashCode());
//        System.out.println(aarhus2.hashCode());
//
//        System.out.println(dk.hashCode());
//        Country dk2 = new Country("Denmark");
//
//        System.out.println(dk2.hashCode());
//
//        System.out.println(dk2.equals(dk));
    }
}
