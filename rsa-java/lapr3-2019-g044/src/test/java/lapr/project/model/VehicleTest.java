package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 * Test class of Vehicle
 *
 *
 */
public class VehicleTest {

    Vehicle instance;
    Vehicle instanceTwo;
    Vehicle instanceThree;

    public VehicleTest() {
    }

    @BeforeEach
    public void setUp() {
        instance = new Vehicle("V050", 15, 25.001, 35.001, 1.2f, 0.5f);
        instanceTwo = new Vehicle("V050", 15, 25.001, 35.001, 1.2f, 0.5f);
        instanceThree = new Vehicle("V049", 14, 24.001, 34.001, 1.1f, 0.4f);
    }

    /**
     * Test of getDescr method, of class Vehicle.
     */
    @Test
    public void testGetDescr() {
        System.out.println("getDescr");
        String expResult = "V050";
        String result = instance.getDescr();
        assertTrue(result.equalsIgnoreCase(expResult), "Vehicle's correct description is V050");
    }

    /**
     * Test of getWeight method, of class Vehicle.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        int expResult = 15;
        int result = instance.getWeight();
        assertTrue(result == expResult, "Vehicle's correct weight is 15");
    }

    /**
     * Test of getParkLat method, of class Vehicle.
     */
    @Test
    public void testGetParkLat() {
        System.out.println("getParkLat");
        double expResult = 25.001;
        double result = instance.getParkLat();
        assertTrue(result == expResult, "Vehicle's correct park latitude is 25.001");
    }

    /**
     * Test of getParkLong method, of class Vehicle.
     */
    @Test
    public void testGetParkLong() {
        System.out.println("getParkLong");
        double expResult = 35.001;
        double result = instance.getParkLong();
        assertTrue(result == expResult, "Vehicle's correct park longitude is 35.001");
    }

    /**
     * Test of getAerodynCoeffic method, of class Vehicle.
     */
    @Test
    public void testGetAerodynCoeffic() {
        System.out.println("getAerodynCoeffic");
        float expResult = 1.2f;
        float result = instance.getAerodynCoeffic();
        assertTrue(result == expResult, "Vehicle's correct aerodynamic coefficient is 1.2");
    }

    /**
     * Test of getFrontalArea method, of class Vehicle.
     */
    @Test
    public void testGetFrontalArea() {
        System.out.println("getFrontalArea");
        float expResult = 0.5f;
        float result = instance.getFrontalArea();
        assertTrue(result == expResult, "Vehicle's correct frontal area is 0.5");
    }

    /**
     * Test of setDescr method, of class Vehicle.
     */
    @Test
    public void testSetDescr() {
        System.out.println("setDescr");
        String descr = "V049";
        instance.setDescr(descr);
        assertTrue(instance.getDescr().equals(descr), "Vehicle's correct description is V049");
        assertThrows(IllegalArgumentException.class, () -> instance.setDescr(null));
        assertThrows(IllegalArgumentException.class, () -> instance.setDescr(""));
    }

    /**
     * Test of setWeight method, of class Vehicle.
     */
    @Test
    public void testSetWeight() {
        System.out.println("setWeight");
        int weight = 12;
        assertFalse(instance.getWeight() == weight, "Vehicle's weight is 15");
        instance.setWeight(weight);
        assertTrue(instance.getWeight() == weight, "Vehicle's correct weight is 12");
        assertThrows(IllegalArgumentException.class, () -> instance.setWeight(0));
        assertThrows(IllegalArgumentException.class, () -> instance.setWeight(-1));
    }

    /**
     * Test of setParkLat method, of class Vehicle.
     */
    @Test
    public void testSetParkLat() {
        System.out.println("setParkLat");
        double parkLat = 24.40;
        instance.setParkLat(parkLat);
        assertTrue(instance.getParkLat() == parkLat, "Vehicle's correct park latitude is 24.40");
        parkLat = -90;
        instance.setParkLat(parkLat);
        assertTrue(instance.getParkLat() == parkLat, "Vehicle's correct park latitude is -90");
        parkLat = 90;
        instance.setParkLat(parkLat);
        assertTrue(instance.getParkLat() == parkLat, "Vehicle's correct park latitude is 90");
        assertThrows(IllegalArgumentException.class, () -> instance.setParkLat(-91));
        assertThrows(IllegalArgumentException.class, () -> instance.setParkLat(91));

    }

    /**
     * Test of setParkLong method, of class Vehicle.
     */
    @Test
    public void testSetParkLong() {
        System.out.println("setParkLong");
        double parkLong = 34.40;
        instance.setParkLong(parkLong);
        assertTrue(instance.getParkLong() == parkLong, "Vehicle's correct park longitude is 34.40");
        parkLong = -180;
        instance.setParkLong(parkLong);
        assertTrue(instance.getParkLong() == parkLong, "Vehicle's correct park longitude is -180");
        parkLong = 180;
        instance.setParkLong(parkLong);
        assertTrue(instance.getParkLong() == parkLong, "Vehicle's correct park longitude is 180");
        assertThrows(IllegalArgumentException.class, () -> instance.setParkLong(-181));
        assertThrows(IllegalArgumentException.class, () -> instance.setParkLong(181));
    }

    /**
     * Test of setAerodynCoeffic method, of class Vehicle.
     */
    @Test
    public void testSetAerodynCoeffic() {
        System.out.println("setAerodynCoeffic");
        float aerodynCoeffic = 1.5f;
        assertFalse(instance.getAerodynCoeffic() == aerodynCoeffic, "Vehicle's aerodynamic coefficient is 1.2");
        instance.setAerodynCoeffic(aerodynCoeffic);
        assertTrue(instance.getAerodynCoeffic() == aerodynCoeffic, "Vehicle's correct aerodynamic coefficient is 1.5");
        assertThrows(IllegalArgumentException.class, () -> instance.setAerodynCoeffic(0));
        assertThrows(IllegalArgumentException.class, () -> instance.setAerodynCoeffic(-1));
    }

    /**
     * Test of setFrontalArea method, of class Vehicle.
     */
    @Test
    public void testSetFrontalArea() {
        System.out.println("setFrontalArea");
        float frontalArea = 0.7f;
        assertFalse(instance.getFrontalArea() == frontalArea, "Vehicle's correct frontal area is 0.5");
        instance.setFrontalArea(frontalArea);
        assertTrue(instance.getFrontalArea() == frontalArea, "Vehicle's correct frontal area is 0.7");
        assertThrows(IllegalArgumentException.class, () -> instance.setFrontalArea(0));
        assertThrows(IllegalArgumentException.class, () -> instance.setFrontalArea(-1));
    }

    /**
     * Test of hashCode method, of class Vehicle.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        int expResult = instanceTwo.hashCode();
        int result = instance.hashCode();
        assertTrue(result == expResult, "Vehicle's hashcode differs from the compared object");
    }

    /**
     * Test of hashCode method, of class Vehicle.
     */
    @Test
    public void testHashCodeNotEqual() {
        System.out.println("hashCodeNotEqual");
        int expResult = instanceThree.hashCode();
        int result = instance.hashCode();
        assertTrue(result != expResult, "Vehicle's hashcode doesn't differ from the compared object");
    }

    /**
     * Test of equals method, of class Vehicle.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        // if (this == obj) return true
        boolean result = instance.equals(instance);
        assertTrue(result, "Vehicle differs from the compared object");
        // if (obj == null) return false
        Vehicle nullVehicle = null;
        result = instance.equals(nullVehicle);
        assertFalse(result, "Vehicle isn't null");
        // if (getClass() != obj.getClass()) return false
        String test = "testObj";
        result = instance.equals(test);
        assertFalse(result, "Vehicle's class doesn't differ from the compared object's class");
        // return Objects.equals(this.descr, other.descr)
        result = instance.equals(instanceTwo);
        assertTrue(result, "Vehicle differs from the compared object");
    }

    /**
     * Test of toString method, of class Vehicle.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String result = instance.toString().trim();
        String expResult = "V050";
        assertTrue(result.contains(expResult), "Escooter's string doesn't match");
    }

    /**
     * Test of compareTo method, of class Vehicle.
     */
    @Test
    public void testCompareTo() {
        System.out.println("testCompareTo");
        // if (this == obj) return 0
        int result = instance.compareTo(instanceTwo);
        assertTrue(result == 0, "Vehicle differs from the compared object");
        // if (this > obj) return 1
        result = instance.compareTo(instanceThree);
        assertTrue(result == 1, "Vehicle isn't greater");
        // if (this < obj) return -1
        result = instanceThree.compareTo(instance);
        assertTrue(result == -1, "Vehicle isn't smaller");
    }
}
