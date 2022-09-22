/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 *
 */
public class TripTest {

    private Trip example;

    @BeforeEach
    void setUp() {
        example = new Trip();
    }

    /**
     * Test of getId method, of class Park.
     */
    @Test
    public void testGetId() {
        System.out.println("testGetId");
        int expResult = 1;
        Trip instance = new Trip(expResult, "B050", "joao", "1", "2", "27-12-2019 11-00-00", "27-12-2019 12-00-00", 16,0, 2);
        int result = instance.getTripId();
        assertEquals(expResult, result);
    }
      /**
     * Test of getId method, of class Park.
     */
    @Test
    public void testGetPoints() {
        System.out.println("testGetPoints");
        int expResult = 0;
        Trip instance = new Trip(1, "B050", "joao", "1", "2", "27-12-2019 11-00-00", "27-12-2019 12-00-00", 16,expResult, 2);
        int result = instance.getPoints();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class Park.
     */
    @Test
    public void testGetVehicleDescriptiom() {
        System.out.println("testGetVehicleDescriptiom");
        String expResult = "B050";
        Trip instance = new Trip(1, expResult, "joao", "1", "2", "27-12-2019 11-00-00", "27-12-2019 12-00-00", 16,0, 2);
        String result = instance.getVehicleDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class Park.
     */
    @Test
    public void testGetUserName() {
        System.out.println("testGetUserName");
        String expResult = "joao";
        Trip instance = new Trip(1, "B050", expResult, "1", "2", "27-12-2019 11-00-00", "27-12-2019 12-00-00", 16,0, 2);
        String result = instance.getUserName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class Park.
     */
    @Test
    public void testGetStartParkId() {
        System.out.println("testGetStartParkId");
        String expResult = "1";
        Trip instance = new Trip(1, "B050", "joao", expResult, "2", "27-12-2019 11-00-00", "27-12-2019 12-00-00", 16,0, 2);
        String result = instance.getStartParkId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class Park.
     */
    @Test
    public void testGetEndParkId() {
        System.out.println("testGetEndParkId");
        String expResult = "2";
        Trip instance = new Trip(1, "B050", "joao", "1", expResult, "27-12-2019 11-00-00", "27-12-2019 12-00-00", 16,0, 2);
        String result = instance.getEndParkId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class Park.
     */
    @Test
    public void testGetStartTime() {
        System.out.println("testGetId");
        String expResult = "27-12-2019 11-00-00";
        Trip instance = new Trip(1, "B050", "joao", "1", "2", expResult, "27-12-2019 12-00-00", 16,0, 2);
        String result = instance.getStartTime();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class Park.
     */
    @Test
    public void testGetEndTime() {
        System.out.println("testGetEndTime");
        String expResult = "27-12-2019 12-00-00";
        Trip instance = new Trip(1, "B050", "joao", "1", "2", "27-12-2019 11-00-00", expResult, 16,0, 2);
        String result = instance.getEndTime();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class Park.
     */
    @Test
    public void testGetCost() {
        System.out.println("testGetCost");
        double expResult = 16;
        Trip instance = new Trip(1, "B050", "joao", "1", "2", "27-12-2019 11-00-00", "27-12-2019 12-00-00", expResult,0, 2);
        double result = instance.getCost();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class Park.
     */
    @Test
    public void testGetInvoiceId() {
        System.out.println("testGetInvoiceId");
        int expResult = 2;
        Trip instance = new Trip(1, "B050", "joao", "1", "2", "27-12-2019 11-00-00", "27-12-2019 12-00-00", 16, 0,expResult);
        int result = instance.getInvoiceId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setElevation method, of class Park.
     */
    @Test
    public void testSetTripId() {
        System.out.println("testSetTripId");
        int expResult = 2;
        Trip instance = new Trip(1, "B050", "joao", "1", "2", "27-12-2019 11-00-00", "27-12-2019 12-00-00", 16,0, 2);
        assertNotEquals(expResult, instance.getTripId());
        instance.setTripId(expResult);
        assertEquals(expResult, instance.getTripId());
        expResult = 1;
        instance.setTripId(expResult);
        assertEquals(expResult, instance.getTripId());
        expResult = -10;
        instance.setTripId(expResult);
        assertEquals(expResult, instance.getTripId());
    }
        /**
     * Test of setElevation method, of class Park.
     */
    @Test
    public void testSetPoints() {
        System.out.println("testSetPoints");
        int expResult = 2;
        Trip instance = new Trip(1, "B050", "joao", "1", "2", "27-12-2019 11-00-00", "27-12-2019 12-00-00", 16,0, 2);
        assertNotEquals(expResult, instance.getPoints());
        instance.setPoints(expResult);
        assertEquals(expResult, instance.getPoints());
        expResult = 1;
        instance.setPoints(expResult);
        assertEquals(expResult, instance.getPoints());
        expResult = -10;
        instance.setPoints(expResult);
        assertEquals(expResult, instance.getPoints());
    }

    /**
     * Test of setElevation method, of class Park.
     */
    @Test
    public void testSetVehicleDescription() {
        System.out.println("testSetVehicleDescription");
        String expResult = "B051";
        Trip instance = new Trip(1, "B050", "joao", "1", "2", "27-12-2019 11-00-00", "27-12-2019 12-00-00", 16,0, 2);
        assertNotEquals(expResult, instance.getVehicleDescription());
        instance.setVehicleDescription(expResult);
        assertEquals(expResult, instance.getVehicleDescription());
        expResult = "B050";
        instance.setVehicleDescription(expResult);
        assertEquals(expResult, instance.getVehicleDescription());
        expResult = "B052";
        instance.setVehicleDescription(expResult);
        assertEquals(expResult, instance.getVehicleDescription());
    }

    /**
     * Test of hashCode method, of class Trip.
     */
    @Test
    public void testhashCode() {
        System.out.println("hashCode");
        Trip instance = new Trip(1, "B050", "joao", "1", "2", "27-12-2019 11-00-00", "27-12-2019 12-00-00", 16,0, 2);
        int expResult = -947792682;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class Trip.
     */
    @Test
    public void testHashCodeNotEqual() {
        System.out.println("hashCodeNotEqual");
        Trip instance = new Trip(1, "B050", "joao", "1", "2", "27-12-2019 11-00-00", "27-12-2019 12-00-00", 16, 0,2);
        Trip instanceTwo = new Trip(1, "B050", "joao", "1", "2", "27-12-2019 11-30-00", "27-12-2019 12-00-00", 16,0, 2);
        int expResult = instanceTwo.hashCode();
        int result = instance.hashCode();
        assertTrue(result != expResult, "Trip's hashcode doesn't differ from the compared object");
    }

    /**
     * Test of equals method, of class Vehicle.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Trip instance = new Trip(1, "B050", "joao", "1", "2", "27-12-2019 11-00-00", "27-12-2019 12-00-00", 16,0, 2);
        Trip instanceTwo = new Trip(1, "B050", "joao", "1", "2", "27-12-2019 11-30-00", "27-12-2019 12-00-00", 16,0, 2);
        Trip instanceThree = new Trip(2, "B050", "joao", "1", "2", "27-12-2019 11-30-00", "27-12-2019 12-00-00", 16,0, 2);
        // if (this == obj) return true
        boolean result = instance.equals(instance);
        assertTrue(result, "Trip differs from the compared object");
        // if (obj == null) return false
        Trip nullTrip = null;
        result = instance.equals(nullTrip);
        assertFalse(result, "Trip isn't null");
        int id = 1;
        result = instance.equals(id);
        assertFalse(result, "Trip's class doesn't differ from the compared object's class");
        result = instance.equals(instanceTwo);
        assertTrue(result, "Trip differs from the compared object");
        result = instance.equals(instanceThree);
        assertFalse(result, "Trip doesn't differ from the compared object");
        instanceTwo.setTripId(3);
        assertFalse(instance.equals(instanceTwo));
    }

    /**
     * Test of toString method, of class Trip.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int tripId = 1;
        String vehicleDescription = "B050";
        String userName = "joao";
        String startParkId = "1";
        String endParkId = "2";
        String startTime = "27-12-2019 11-00-00";
        String endTime = "27-12-2019 12-00-00";
        double cost = 16;
        int points = 0;
        int invoiceId = 2;

        Trip instance = new Trip(tripId, vehicleDescription, userName, startParkId, endParkId, startTime, endTime, cost,points, invoiceId);
        String expResult = "Trip: \n" + "TripID=" + tripId + "\n VehicleDescription=" + vehicleDescription + "\n UserName=" + userName + "\n StartParkID=" + startParkId + "\n EndParkID=" + endParkId + "\n StartTime=" + startTime + "\n EndTime=" + endTime + "\n cost=" + cost + "\n points=" + points + "\n InvoiceID=" + invoiceId + '.';

        String result = instance.toString();
        assertEquals(expResult, result);
    }

}
