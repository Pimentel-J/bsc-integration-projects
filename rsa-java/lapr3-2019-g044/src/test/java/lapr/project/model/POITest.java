/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for "POI".
 */
public class POITest {
    
    private POI poi;
    
    @BeforeEach
    void setUp() {
        poi = new POI();
    }
    
    /**
     * Test of getLatitude method, of class POI.
     */
    @Test
    public void testGetLatitude() {
        System.out.println("testGetLatitude");
        double expResult = 5;
        POI instance = new POI(expResult, -5, 200, "Clerigos");
        double result = instance.getLatitude();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getLongitude method, of class POI.
     */
    @Test
    public void testGetLongitude() {
        System.out.println("testGetLongitude");
        double expResult = -5;
        POI instance = new POI(5, expResult, 200, "Clerigos");
        double result = instance.getLongitude();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getElevation method, of class POI.
     */
    @Test
    public void testGetElevation() {
        System.out.println("testGetElevation");
        int expResult = 200;
        POI instance = new POI(5, -5, expResult, "Clerigos");
        int result = instance.getElevation();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getDescription method, of class POI.
     */
    @Test
    public void testGetDescription() {
        System.out.println("testGetDescription");
        String expResult = "Clerigos";
        POI instance = new POI(5, -5, 200, expResult);
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of setLatitude method, of class POI.
     */
    @Test
    public void testSetLatitude() {
        System.out.println("testSetLatitude");
        int expResult = 3;
        POI instance = new POI(5, -5, 200, "Clerigos");
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
     * Test of setLongitude method, of class POI.
     */
    @Test
    public void testSetLongitude() {
        System.out.println("testSetLongitude");
        int expResult = 3;
        POI instance = new POI(5, -5, 200, "Clerigos");
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
     * Test of setElevation method, of class POI.
     */
    @Test
    public void testSetElevation() {
        System.out.println("testSetElevation");
        int expResult = 3;
        POI instance = new POI(5, -5, 200, "Clerigos");
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
     * Test of setDescription method, of class POI.
     */
    @Test
    public void testSetDescription() {
        System.out.println("testSetDescription");
        String expResult = "Almada";
        POI instance = new POI(5, -5, 200, "Clerigos");
        assertNotEquals(expResult, instance.getDescription());
        instance.setDescription(expResult);
        assertEquals(expResult, instance.getDescription());
    }
    
    /**
     * Test of hashCode method, of class POI.
     */
    @Test
    public void testHashCode() {
        System.out.println("testHashCode");
        int expResult = 41959377;
        POI instance = new POI(5, -5, 200, "Clerigos");
        assertEquals(expResult, instance.hashCode());
    }
    
    /**
     * Test of equals method, of class POI.
     */
    @Test
    public void testEquals() {
        System.out.println("testEquals");
        POI instance = new POI(5, -5, 200, "Clerigos");
        assertEquals(true, instance.equals(instance));
        assertEquals(false, instance.equals(null));
        assertEquals(false, instance.equals(new Park()));
        assertEquals(false, instance.equals(new POI(5, -4.9, 200, "Clerigos")));
        assertEquals(false, instance.equals(new POI(4.9, -5, 200, "Clerigos")));
        assertEquals(true, instance.equals(new POI(5, -5, 200, "Clerigos")));
    }
    
    /**
     * Test of toString method, of class POI.
     */
    @Test
    public void testToString() {
        System.out.println("testToString");
        boolean expResult = true;
        POI instance = new POI(5, -5, 200, "Clerigos");
        assertEquals(expResult, instance.toString().contains("Clerigos"));
    }
    
}
