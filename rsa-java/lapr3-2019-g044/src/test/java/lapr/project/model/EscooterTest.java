package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 * Test class of Escooter
 *
 *
 */
public class EscooterTest {

    Escooter instance;
    Escooter instanceTwo;

    public EscooterTest() {
    }

    @BeforeEach
    public void setUp() {
        instance = new Escooter("S050", 25, Escooter.Type.CITY, 25.001, 35.001, 0.15f, 50, 1.10f, 0.3f, 350);
        instanceTwo = new Escooter("S101", 25, Escooter.Type.CITY, 25.001, 35.001, 0.15f, 50, 1.10f, 0.3f, 350, 3600);
    }

    /**
     * Test of getType method, of class Escooter.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        Escooter.Type result = instance.getType();
        assertTrue(result.equals(Escooter.Type.CITY), "Escooter's correct type is 'City'");
    }

    /**
     * Test of getMaxBattery method, of class Escooter.
     */
    @Test
    public void testGetMaxBattery() {
        System.out.println("getMaxBattery");
        float expResult = 0.15f;
        float result = instance.getMaxBattery();
        assertTrue(result == expResult, "Escooter's correct maximum battery is 0,15 kWh");
    }

    /**
     * Test of getActualBattery method, of class Escooter.
     */
    @Test
    public void testGetActualBattery() {
        System.out.println("getActualBattery");
        int expResult = 50;
        int result = instance.getActualBattery();
        assertTrue(result == expResult, "Escooter's correct actual battery is 50%");
    }

    /**
     * Test of getMotor method, of class Escooter.
     */
    @Test
    public void testGetMotor() {
        System.out.println("getMotor");
        float expResult = 350;
        float result = instance.getMotor();
        assertTrue(result == expResult, "Escooter's correct motor power is 350 W");
    }

    /**
     * Test of getTotalChargeTime method, of class Escooter.
     */
    @Test
    public void testGetTotalChargeTime() {
        System.out.println("getTotalChargeTime");
        int expResult = 3600;
        int result = instanceTwo.getTotalChargeTime();
        assertTrue(result == expResult, "Escooter's time to finish charge is 3600");
    }

    /**
     * Test of setType method, of class Escooter.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        Escooter.Type expResult = Escooter.Type.OFFROAD;
        instance.setType(Escooter.Type.OFFROAD);
        assertTrue(instance.getType().equals(expResult), "Escooter's correct type is Off-Road");
        assertThrows(IllegalArgumentException.class, () -> instance.setType(null));
        assertThrows(IllegalArgumentException.class, () -> instance.setDescr(""));
    }

    /**
     * Test of setMaxBattery method, of class Escooter.
     */
    @Test
    public void testSetMaxBattery() {
        System.out.println("setMaxBattery");
        float maxBattery = 0.10f;
        assertFalse(instance.getMaxBattery() == maxBattery, "Escooter's maximum battery is 0,15 kWh");
        instance.setMaxBattery(maxBattery);
        assertTrue(instance.getMaxBattery() == maxBattery, "Escooter's correct maximum battery is 0,10 kWh");
        assertThrows(IllegalArgumentException.class, () -> instance.setMaxBattery(0));
        assertThrows(IllegalArgumentException.class, () -> instance.setMaxBattery(-1));
    }

    /**
     * Test of setActualBattery method, of class Escooter.
     */
    @Test
    public void testSetActualBattery() {
        System.out.println("setActualBattery");
        int actualBattery = 75;
        assertFalse(instance.getActualBattery() == actualBattery, "Escooter's actual battery is 50%");
        instance.setActualBattery(actualBattery);
        assertTrue(instance.getActualBattery() == actualBattery, "Escooter's correct actual battery is 75%");
        actualBattery = 0;
        instance.setActualBattery(actualBattery);
        assertTrue(instance.getActualBattery() == actualBattery, "Escooter's correct actual battery is 0%");
        assertThrows(IllegalArgumentException.class, () -> instance.setActualBattery(-1));
    }

    /**
     * Test of setMotor method, of class Escooter.
     */
    @Test
    public void testSetMotor() {
        System.out.println("setMotor");
        int motor = 220;
        assertFalse(instance.getMotor() == motor, "Escooter's motor power is 350 W");
        instance.setMotor(motor);
        assertTrue(instance.getMotor() == motor, "Escooter's motor power is 220 W");
        assertThrows(IllegalArgumentException.class, () -> instance.setMotor(0));
        assertThrows(IllegalArgumentException.class, () -> instance.setMotor(-1));
    }

    /**
     * Test of setTotalChargeTime method, of class Escooter.
     */
    @Test
    public void testSetTotalChargeTime() {
        System.out.println("setTotalChargeTime");
        int totalChargeTime = 2200;
        assertFalse(instanceTwo.getMotor() == totalChargeTime, "Escooter's time to finish charge is 3600");
        instanceTwo.setMotor(totalChargeTime);
        assertTrue(instanceTwo.getMotor() == totalChargeTime, "Escooter's time to finish charge is 2200");
    }

    /**
     * Test of toString method, of class Escooter.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String result = instance.toString().trim();
        String expResult = "S050";
        assertTrue(result.contains(expResult), "Escooter's string doesn't match");
    }

}
