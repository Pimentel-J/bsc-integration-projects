/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 *
 */
public class InvoiceLineTest {

    private InvoiceLine example;

    @BeforeEach
    void setUp() {
        example = new InvoiceLine();
    }

    /**
     * Test of getId method, of class InvoiceLine.
     */
    @Test
    public void testGetId() {
        System.out.println("testGetId");
        Integer expResult = 0;
        InvoiceLine instance = new InvoiceLine(expResult, "joao", 1, 2, 3, 4, 5);
        Integer result = instance.getInvoiceLineId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUsername method, of class InvoiceLine.
     */
    @Test
    public void testGetUsername() {
        System.out.println("testGetUsername");
        String expResult = "joao";
        InvoiceLine instance = new InvoiceLine(0, expResult, 1, 2, 3, 4, 5);
        String result = instance.getUsername();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetTripId method, of class InvoiceLine.
     */
    @Test
    public void testGetPreviousPoints() {
        System.out.println("testGetPreviousPoints");
        int expResult = 1;
        InvoiceLine instance = new InvoiceLine(0, "joao", expResult, 2, 3, 4, 5);
        int result = instance.getPreviousPoints();
        assertEquals(expResult, result);
    }

    /**
     * Test of GetVehicleDescription method, of class InvoiceLine.
     */
    @Test
    public void testGetEarnedPoints() {
        System.out.println("testGetEarnedPoints");
        int expResult = 10;
        InvoiceLine instance = new InvoiceLine(0, "joao", 1, expResult, 3, 4, 5);
        int result = instance.getEarnedPoints();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStartParkId method, of class InvoiceLine.
     */
    @Test
    public void testGetDiscountedPoints() {
        System.out.println("testGetDiscountedPoints");
        int expResult = 1;
        InvoiceLine instance = new InvoiceLine(0, "joao", 1, 10, expResult, 4, 5);
        int result = instance.getDiscountedPoints();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEndParkId method, of class InvoiceLine.
     */
    @Test
    public void testGetActualPoints() {
        System.out.println("testGetActualPoints");
        int expResult = 5;
        InvoiceLine instance = new InvoiceLine(0, "joao", 1, 10, 5, expResult, 5);
        int result = instance.getActualPoints();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEndParkId method, of class InvoiceLine.
     */
    @Test
    public void testGetCost() {
        System.out.println("testGetCost");
        double expResult = 1;
        InvoiceLine instance = new InvoiceLine(0, "joao", 1, 10, 5, 5, expResult);
        double result = instance.getCost();
        assertEquals(expResult, result);
    }

    /**
     * Test of set method, of class InvoiceLine.
     */
    @Test
    public void testSetInvoiceId() {
        System.out.println("testSetInvoiceId");
        Integer expResult = 3;
        InvoiceLine instance = new InvoiceLine(expResult, "joao", 1, 10, 5, 4, 5);
        instance.setInvoiceLineId(1);
        assertNotEquals(expResult, instance.getInvoiceLineId());
        instance.setInvoiceLineId(expResult);
        assertEquals(expResult, instance.getInvoiceLineId());
    }

    /**
     * Test of set method, of class InvoiceLine.
     */
    @Test
    public void testSetUsername() {
        System.out.println("testSetUsername");
        String expResult = "teste";
        InvoiceLine instance = new InvoiceLine(0, "joao", 1, 2, 3, 4, 5);
        assertNotEquals(expResult, instance.getUsername());
        instance.setUsername(expResult);
        assertEquals(expResult, instance.getUsername());
    }

    /**
     * Test of set method, of class InvoiceLine.
     */
    @Test
    public void testSetCost() {
        System.out.println("testSetCost");
        double expResult = 3;
        InvoiceLine instance = new InvoiceLine(0, "joao", 1, 2, 3, 4, 5);
        assertNotEquals(expResult, instance.getCost());
        instance.setCost(expResult);
        assertEquals(expResult, instance.getCost());
    }

    /**
     * Test of set method, of class InvoiceLine.
     */
    @Test
    public void testSetPreviousPoints() {
        System.out.println("testSetPreviousPoints");
        int expResult = 1;
        InvoiceLine instance = new InvoiceLine(0, "joao", 2, 2, 3, 4, 5);
        assertNotEquals(expResult, instance.getPreviousPoints());
        instance.setPreviousPoints(expResult);
        assertEquals(expResult, instance.getPreviousPoints());
    }

    /**
     * Test of set method, of class InvoiceLine.
     */
    @Test
    public void testSetEarnedPoints() {
        System.out.println("testSetEarnedPoints");
        int expResult = 1;
        InvoiceLine instance = new InvoiceLine(0, "joao", 1, 2, 3, 4, 5);
        assertNotEquals(expResult, instance.getEarnedPoints());
        instance.setEarnedPoints(expResult);
        assertEquals(expResult, instance.getEarnedPoints());
    }

    /**
     * Test of set method, of class InvoiceLine.
     */
    @Test
    public void testSetDiscountedPoints() {
        System.out.println("testSetDiscountedPoints");
        int expResult = 1;
        InvoiceLine instance = new InvoiceLine(0, "joao", 1, 2, 3, 4, 5);
        assertNotEquals(expResult, instance.getDiscountedPoints());
        instance.setDiscountedPoints(expResult);
        assertEquals(expResult, instance.getDiscountedPoints());
    }

    /**
     * Test of set method, of class InvoiceLine.
     */
    @Test
    public void testSetActualPoints() {
        System.out.println("testSetActualPoints");
        int expResult = 1;
        InvoiceLine instance = new InvoiceLine(0, "joao", 1, 2, 3, 4, 5);
        assertNotEquals(expResult, instance.getActualPoints());
        instance.setActualPoints(expResult);
        assertEquals(expResult, instance.getActualPoints());
    }

    /**
     * Test of toString method, of class InvoiceLine.
     */
    @Test
    public void testToString() {
        System.out.println("Invoice");
        Integer invoiceLineId = 0;
        String username = "joao";
        int previousPoints = 1;
        int earnedPoints = 1;
        int discountedPoints = 1;
        int actualPoints = 1;
        double cost = 1;
        InvoiceLine instance = new InvoiceLine(invoiceLineId, username, previousPoints, earnedPoints, discountedPoints, actualPoints, cost);
        String expResult = "Invoice: \n" + "invoice id=" + invoiceLineId + "\n =" + "\n username=" + username + "\n previousPoints=" + previousPoints + "\n earnedPoints=" + earnedPoints + "\n discountedPoints=" + discountedPoints + "\n actualPoints=" + actualPoints + "\n cost=" + cost + '.';

        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
     /**
     * Test of AddInvoice method.
     */
    @Test
    public void testEquals() {
        System.out.println("Equals");
        InvoiceLine invoice = new InvoiceLine();
        
        invoice.setInvoiceLineId(12);
        assertFalse(example.equals(null));
        assertFalse(example.equals(new Park()));
        assertFalse(example.equals(invoice));
        assertTrue(example.equals(example));
    }
    
}
