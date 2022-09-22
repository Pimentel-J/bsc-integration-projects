/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for "Park".
 */
public class ParkTest {

    private Park example;

    @BeforeEach
    void setUp() {
        example = new Park();
    }

    /**
     * Test of getId method, of class Park.
     */
    @Test
    public void testGetId() {
        System.out.println("testGetId");
        String expResult = "1";
        Park instance = new Park(expResult, 5, -5, 200, "Porto", 10, 12, 16, 2);
        String result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLatitude method, of class Park.
     */
    @Test
    public void testGetLatitude() {
        System.out.println("testGetLatitude");
        double expResult = 5;
        Park instance = new Park("1", expResult, -5, 200, "Porto", 10, 12, 16, 2);
        double result = instance.getLatitude();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLongitude method, of class Park.
     */
    @Test
    public void testGetLongitude() {
        System.out.println("testGetLongitude");
        double expResult = -5;
        Park instance = new Park("1", 5, expResult, 200, "Porto", 10, 12, 16, 2);
        double result = instance.getLongitude();
        assertEquals(expResult, result);
    }

    /**
     * Test of getElevation method, of class Park.
     */
    @Test
    public void testGetElevation() {
        System.out.println("testGetElevation");
        int expResult = 200;
        Park instance = new Park("1", 5, -5, expResult, "Porto", 10, 12, 16, 2);
        int result = instance.getElevation();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescription method, of class Park.
     */
    @Test
    public void testGetDescription() {
        System.out.println("testGetDescription");
        String expResult = "Porto";
        Park instance = new Park("1", 5, -5, 200, expResult, 10, 12, 16, 2);
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMaxNumberBicycles method, of class Park.
     */
    @Test
    public void testGetMaxNumberBicycles() {
        System.out.println("testGetMaxNumberBicycles");
        int expResult = 10;
        Park instance = new Park("1", 5, -5, 200, "Porto", expResult, 12, 16, 2);
        int result = instance.getMaxNumberBicycles();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMaxNumberEscooters method, of class Park.
     */
    @Test
    public void testGetMaxNumberEscooters() {
        System.out.println("testGetMaxNumberEscooters");
        int expResult = 12;
        Park instance = new Park("1", 5, -5, 200, "Porto", 10, expResult, 16, 2);
        int result = instance.getMaxNumberEscooters();
        assertEquals(expResult, result);
    }

    /**
     * Test of getInputVoltage method, of class Park.
     */
    @Test
    public void testGetInputVoltage() {
        System.out.println("testGetInputVoltage");
        int expResult = 16;
        Park instance = new Park("1", 5, -5, 200, "Porto", 10, 12, expResult, 2);
        int result = instance.getInputVoltage();
        assertEquals(expResult, result);
    }

    /**
     * Test of getInputCurrent method, of class Park.
     */
    @Test
    public void testGetInputCurrent() {
        System.out.println("testGetInputCurrent");
        int expResult = 2;
        Park instance = new Park("1", 5, -5, 200, "Porto", 10, 12, 16, expResult);
        int result = instance.getInputCurrent();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Park.
     */
    @Test
    public void testSetId() {
        System.out.println("testSetId");
        String expResult = "3";
        Park instance = new Park("1", 5, -5, 200, "Porto", 10, 12, 16, 2);
        assertNotEquals(expResult, instance.getId());
        instance.setId(expResult);
        assertEquals(expResult, instance.getId());
        assertThrows(IllegalArgumentException.class, () -> instance.setId(null));
        assertThrows(IllegalArgumentException.class, () -> instance.setId(""));
    }

    /**
     * Test of setLatitude method, of class Park.
     */
    @Test
    public void testSetLatitude() {
        System.out.println("testSetLatitude");
        int expResult = 3;
        Park instance = new Park("1", 5, -5, 200, "Porto", 10, 12, 16, 2);
        assertNotEquals(expResult, instance.getLatitude());
        instance.setLatitude(expResult);
        assertEquals(expResult, instance.getLatitude());
        expResult = 90;
        instance.setLatitude(expResult);
        assertEquals(expResult, instance.getLatitude());
        expResult = -90;
        instance.setLatitude(expResult);
        assertEquals(expResult, instance.getLatitude());
        assertThrows(IllegalArgumentException.class, () -> instance.setLatitude(91));
        assertThrows(IllegalArgumentException.class, () -> instance.setLatitude(-91));
    }

    /**
     * Test of setLongitude method, of class Park.
     */
    @Test
    public void testSetLongitude() {
        System.out.println("testSetLongitude");
        int expResult = 3;
        Park instance = new Park("1", 5, -5, 200, "Porto", 10, 12, 16, 2);
        assertNotEquals(expResult, instance.getLongitude());
        instance.setLongitude(expResult);
        assertEquals(expResult, instance.getLongitude());
        expResult = 180;
        instance.setLongitude(expResult);
        assertEquals(expResult, instance.getLongitude());
        expResult = -180;
        instance.setLongitude(expResult);
        assertEquals(expResult, instance.getLongitude());
        assertThrows(IllegalArgumentException.class, () -> instance.setLongitude(181));
        assertThrows(IllegalArgumentException.class, () -> instance.setLongitude(-181));
    }

    /**
     * Test of setElevation method, of class Park.
     */
    @Test
    public void testSetElevation() {
        System.out.println("testSetElevation");
        int expResult = 3;
        Park instance = new Park("1", 5, -5, 200, "Porto", 10, 12, 16, 2);
        assertNotEquals(expResult, instance.getElevation());
        instance.setElevation(expResult);
        assertEquals(expResult, instance.getElevation());
        expResult = 0;
        instance.setElevation(expResult);
        assertEquals(expResult, instance.getElevation());
        expResult = -10;
        instance.setElevation(expResult);
        assertEquals(expResult, instance.getElevation());
    }

    /**
     * Test of setDescription method, of class Park.
     */
    @Test
    public void testSetDescription() {
        System.out.println("testSetDescription");
        String expResult = "Lisboa";
        Park instance = new Park("1", 5, -5, 200, "Porto", 10, 12, 16, 2);
        assertNotEquals(expResult, instance.getDescription());
        instance.setDescription(expResult);
        assertEquals(expResult, instance.getDescription());
        assertThrows(IllegalArgumentException.class, () -> instance.setDescription(null));
        assertThrows(IllegalArgumentException.class, () -> instance.setDescription(""));
    }

    /**
     * Test of setMaxNumberBicycles method, of class Park.
     */
    @Test
    public void testSetMaxNumberBicycles() {
        System.out.println("testSetMaxNumberBicycles");
        int expResult = 3;
        Park instance = new Park("1", 5, -5, 200, "Porto", 10, 12, 16, 2);
        assertNotEquals(expResult, instance.getMaxNumberBicycles());
        instance.setMaxNumberBicycles(expResult);
        assertEquals(expResult, instance.getMaxNumberBicycles());
        assertThrows(IllegalArgumentException.class, () -> instance.setMaxNumberBicycles(0));
        assertThrows(IllegalArgumentException.class, () -> instance.setMaxNumberBicycles(-1));
    }

    /**
     * Test of setMaxNumberEscooters method, of class Park.
     */
    @Test
    public void testSetMaxNumberEscooters() {
        System.out.println("testSetMaxNumberEscooters");
        int expResult = 3;
        Park instance = new Park("1", 5, -5, 200, "Porto", 10, 12, 16, 2);
        assertNotEquals(expResult, instance.getMaxNumberEscooters());
        instance.setMaxNumberEscooters(expResult);
        assertEquals(expResult, instance.getMaxNumberEscooters());
        assertThrows(IllegalArgumentException.class, () -> instance.setMaxNumberEscooters(0));
        assertThrows(IllegalArgumentException.class, () -> instance.setMaxNumberEscooters(-1));
    }

    /**
     * Test of setInputVoltage method, of class Park.
     */
    @Test
    public void testSetInputVoltage() {
        System.out.println("testSetInputVoltage");
        int expResult = 3;
        Park instance = new Park("1", 5, -5, 200, "Porto", 10, 12, 16, 2);
        assertNotEquals(expResult, instance.getInputVoltage());
        instance.setInputVoltage(expResult);
        assertEquals(expResult, instance.getInputVoltage());
        assertThrows(IllegalArgumentException.class, () -> instance.setInputVoltage(0));
        assertThrows(IllegalArgumentException.class, () -> instance.setInputVoltage(-1));
    }

    /**
     * Test of setInputCurrent method, of class Park.
     */
    @Test
    public void testSetInputCurrent() {
        System.out.println("testSetInputCurrent");
        int expResult = 3;
        Park instance = new Park("1", 5, -5, 200, "Porto", 10, 12, 16, 2);
        assertNotEquals(expResult, instance.getInputCurrent());
        instance.setInputCurrent(expResult);
        assertEquals(expResult, instance.getInputCurrent());
        assertThrows(IllegalArgumentException.class, () -> instance.setInputCurrent(0));
        assertThrows(IllegalArgumentException.class, () -> instance.setInputCurrent(-1));
    }

    /**
     * Test of toString method, of class User.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String id = "1";
        double latitude = 5;
        double longitude = -5;
        int elevation = 200;
        String description = "Porto";
        int maxNumberBicycles = 10;
        int maxNumberEscooters = 12;
        int inputVoltage = 516;
        int inputCurrent = 2;
        Park instance = new Park(id, latitude, longitude, elevation, description, maxNumberBicycles, maxNumberEscooters, inputVoltage, inputCurrent);

        String expResult = "Park: \n" + "id=" + id + "\n laititude=" + latitude + "\n longitude=" + longitude + "\n elevation=" + elevation + "\n description=" + description + "\n Bicycles=" + maxNumberBicycles + "\n Escooters=" + maxNumberEscooters + "\n volatge=" + inputVoltage + "\n current=" + inputCurrent + '.';

        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of compareTo method, of class Park.
     */
    @Test
    public void testCompareTo() {
        System.out.println("testCompareTo");
        Park instance = new Park("2", 7, -7, 100, "Coimbra", 15, 11, 15, 3);
        Park instanceTwo = new Park("1", 5, -5, 200, "Porto", 10, 12, 16, 2);
        Park instanceThree = new Park("1", 5, -5, 200, "Porto", 10, 12, 16, 2);
        // if (this == obj) return 0
        int result = instanceTwo.compareTo(instanceThree);
        assertTrue(result == 0, "Park differs from the compared object");
        // if (this > obj) return 1
        result = instance.compareTo(instanceTwo);
        assertTrue(result == 1, "Park isn't greater");
        // if (this < obj) return -1
        result = instanceTwo.compareTo(instance);
        assertTrue(result == -1, "Park isn't smaller");
    }
}
