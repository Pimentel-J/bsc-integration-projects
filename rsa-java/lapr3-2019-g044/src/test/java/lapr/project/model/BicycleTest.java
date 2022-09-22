package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 * Test class of Bicycle
 *
 *
 */
public class BicycleTest {

    Bicycle instance;

    public BicycleTest() {
    }

    @BeforeEach
    public void setUp() {
        instance = new Bicycle("B050", 10, 25.001, 35.001, 0.15f, 1.10f, 26);
    }

    /**
     * Test of getWheelSize method, of class Escooter.
     */
    @Test
    public void testGetWheelSize() {
        System.out.println("getWheelSize");
        int expResult = 26;
        int result = instance.getWheelSize();
        assertTrue(result == expResult, "Bicycle's wheel size is 26'");
    }

    /**
     * Test of setWheelSize method, of class Escooter.
     */
    @Test
    public void testSetWheelSize() {
        System.out.println("setWheelSize");
        int wheelSize = 18;
        assertFalse(instance.getWheelSize() == wheelSize, "Bicycle's wheel size is 26'");
        instance.setWheelSize(wheelSize);
        assertTrue(instance.getWheelSize() == wheelSize, "Bicycle's wheel size is 18'");
        assertThrows(IllegalArgumentException.class, () -> instance.setWheelSize(0));
        assertThrows(IllegalArgumentException.class, () -> instance.setWheelSize(-1));
    }
}
