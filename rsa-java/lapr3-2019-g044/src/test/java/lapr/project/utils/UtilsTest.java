/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 *
 *
 */
public class UtilsTest {

    @Test
    void testDistance1() {

        System.out.println("testeDistance1");
        double expResult = 2934.3906335655383;
        double result = Utils.distance(41.158087, -8.629120, 41.179025, -8.607782);
        assertEquals(expResult, result, 0.0001);
    }

    @Test
    void testDistance2() {

        System.out.println("testeDistance2");
        double expResult = 1918015.4569862178;
        double result = Utils.distance(-12.5645, 86.4353, -20.32453, 70.35345);
        assertEquals(expResult, result, 0.0001);
    }

    @Test
    void convertStringToDateTest() {
        Date expected = null;
        try {
            expected = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-12-27 10:30:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date result = Utils.stringToDate("2019-12-27 10:30:00");
        assertEquals(expected, result);
    }

    @Test
    void convertStringToDateExceptionTest() {
        Date result = Utils.stringToDate("deu erro");
        assertNull(result);
    }

    @Test
    void TestCalcDurationBattery() {
        assertEquals(0, Utils.calcDurationBattery(0, 0));
    }

    @Test
    void TestGetTripDuration() {
        System.out.println("GetTripDuration");

        Calendar startTimeC = Calendar.getInstance();
        startTimeC.set(2019, 12, 3, 20, 0, 0);

        Calendar endTimeC = Calendar.getInstance();
        endTimeC.set(2019, 12, 3, 22, 0, 0);

        Date endTime = endTimeC.getTime();
        Date startTime = startTimeC.getTime();

        assertEquals(null, Utils.getTripDuration(null, endTime));
        assertEquals(null, Utils.getTripDuration(startTime, null));
        assertEquals(120, Utils.getTripDuration(startTime, endTime));
    }

    @Test
    void TestCalculateTripCost() {
        System.out.println("CalculateTripCost");

        assertEquals(0, Utils.calculateTripCost(12));
        assertEquals(0.375, Utils.calculateTripCost(75));

    }

    @Test
    void TestCalculatePointsFromAltitude() {
        System.out.println("CalculatePointsFromAltitude");

        assertEquals(0, Utils.calculatePointsFromAltitude(0, 0));
        assertEquals(15, Utils.calculatePointsFromAltitude(0, 55));
        assertEquals(5, Utils.calculatePointsFromAltitude(0, 50));

    }

    @Test
    void TestCalcPower() {
        System.out.println("CalcPower");

        assertEquals(0, Utils.calcPower(12, 12, 12, 0, 0));

    }

    @Test
    void TestCalcSlope() {
        System.out.println("CalcSlope");

        assertEquals(1, Utils.calcSlope(0, 120, 120));
        assertEquals(0, Utils.calcSlope(0, 120, -1));
        assertEquals(0, Utils.calcSlope(120, 0, 120));

    }

    @Test
    void TestCalcCalories() {
        System.out.println("CalcCalories");

        assertEquals(0, Utils.calcCalories(0));

    }

    @Test
    void testCalcEscooterChargeTime() {
        System.out.println("testCalcEscooterChargeTime");
        int expResult = 27272;
        int result = Utils.calcEscooterChargeTime(220, 16, 24, 0, 1);
        assertEquals(expResult, result);
    }

    @Test
    void testGetTripDurationInSeconds() {
        System.out.println("GetTripDurationInSeconds");

        assertEquals(null, Utils.getTripDurationInSeconds(null, "2019-11-20 10:21:00"));
        assertEquals(null, Utils.getTripDurationInSeconds("2019-11-20 10:21:00", null));
        assertEquals(1, Utils.getTripDurationInSeconds("2019-11-20 10:21:00", "2019-11-20 10:21:01"));
        

    }
}
