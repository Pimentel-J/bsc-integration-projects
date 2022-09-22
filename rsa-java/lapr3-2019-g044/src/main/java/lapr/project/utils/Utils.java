/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lapr.project.model.Escooter.Type;
import lapr.project.model.POI;
import lapr.project.model.Park;

/**
 *
 *
 */
public class Utils {

    public static final Type NO_TYPE = null;
    public static final float NO_MAX_BATTERY = 0;
    public static final int NO_ACT_BATTERY = 0;
    public static final int NO_WHEEL_SIZE = 0;
    public static final float NO_MOTOR = 0;
    public static final int DEFAULT_RAIUS = 1000;
    public static final float DEFAULT_VELOCITY = 5.5f;
    public static final float EFFICIENCY = 0.7f;
    public static final String HEADER_NEAREST_PARKS = "latitude;longitude;distance in meters";
    public static final String HEADER_OUTPUT_ESCOOTERS = "escooter description;type;actual battery capacity";
    public static final String HEADER_OUTPUT_BICYCLES = "bicycle description;wheel size";
    public static final String HEADER_OUTPUT_BALANCE = "vehicle description;vehicle unlock time;vehicle lock time;origin park latitude;origin park longitude;destination park latitude;destination park longitude;total time spent in seconds;charged value";
    public static final String HEADER_OUTPUT_POINTS = "vehicle description;vehicle unlock time;vehicle lock time;origin park latitude;origin park longitude;origin park elevation;destination park latitude;destination park longitude;destination park elevation;points";
    public static final String HEADER_OUTPUT_CHARGING = "escooter description;actual battery capacity;time to finish charge in seconds";
    public static final double COST_MINUTE = 0.025;
    public static final double COEF_ALL_ATR_MECA = 0.0053;
    public static final double AIR_DENSITY = 1.225;
    public static final double DRAG_COEF = 0.5;
    public static final double FRONTAL_AREA = 0.5;
    public static final double GRAVITY = 9.8;
    public static final double WATT_CAL = 0.24;
    public static final int DEFAULT_MASS = 10;

    /**
     * Calcula e devolve a distância entre duas coordenadas em metros (m)
     *
     * @param lat1 Latitude da primeira coordenada
     * @param lon1 Longitude da primeira coordenada
     * @param lat2 Latitude da segunda coordenada
     * @param lon2 Longitude da segunda coordenada
     * @return distância (m)
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        // shortest distance over the earth’s surface
        // https://www.movable-type.co.uk/scripts/latlong.html
        final double R = 6371e3;
        double theta1 = Math.toRadians(lat1);
        double theta2 = Math.toRadians(lat2);
        double deltaTheta = Math.toRadians(lat2 - lat1);
        double deltaLambda = Math.toRadians(lon2 - lon1);
        double a = Math.sin(deltaTheta / 2) * Math.sin(deltaTheta / 2)
                + Math.cos(theta1) * Math.cos(theta2)
                * Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // distância em metros

    }

    /**
     * Calculate the duration of the battery in hours
     *
     * @param mot motor power
     * @param actBatInKwH actual battery in KwH
     * @return the duration of the battery in hours
     */
    public static float calcDurationBattery(float mot, float actBatInKwH) {

        return (mot != 0) ? actBatInKwH / mot : 0;
    }

    /**
     * Return the max distance given a time capacity of a battery The distance
     * is calculated using a standart velocity DEFAULT_VELOCITY and it's
     * multiply by EFFICIENCY because the equipment ins't 100% effencient
     *
     * @param hours hours
     * @return distance in Km
     */
    public static float calcMaxDistanceInPerfectConditions(float hours) {
        return hours * DEFAULT_VELOCITY * EFFICIENCY;
    }

    /**
     * Method that converts string of time to date
     *
     * @param timeString string with time
     * @return SimpleDateFormat date format with the string
     */
    public static Date stringToDate(String timeString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(timeString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Method that calculates the duration of a trip in minutes.
     *
     * @param startTime start time of the trip
     * @param endTime end time of the trip
     * @return duration of trip in minutes
     */
    public static Long getTripDuration(Date startTime, Date endTime) {
        if (startTime == null || endTime == null) {
            return null;
        }
        long durationMinutes = Math.abs(startTime.getTime() - endTime.getTime());
        long diffmin = (durationMinutes / (60 * 1000));
        return diffmin;
    }

    /**
     * Method that calculates the duration of a trip in seconds.
     *
     * @param start start time of the trip
     * @param end end time of the trip
     * @return duration of trip in miliseconds
     */
    public static Long getTripDurationInSeconds(String start, String end) {
        if (start == null || end == null) {
            return null;
        }
        Date startTime = stringToDate(start);
        Date endTime = stringToDate(end);
        long duration = Math.abs(startTime.getTime() - endTime.getTime());
        duration = duration / 1000;
        return duration;
    }

    /**
     *
     *
     * Method that calculates the cost of a trip.
     *
     * @param duration the duration of a trip in minutes
     * @return cost cost of trip
     */
    public static double calculateTripCost(double duration) {
        double cost;
        if (duration > 60) {
            cost = (duration - 60) * COST_MINUTE;
        } else {
            cost = 0.0;
        }
        return cost;
    }

    /**
     *
     * Method that calculates points from parking a vehicle considerating
     * elavation
     *
     * @param startElevation the altitude of starting park
     * @param endElevation the altitude of end park
     * @return points total points calcualted
     */
    public static int calculatePointsFromAltitude(int startElevation, int endElevation) {
        int points = 0;
        int altitude = endElevation - startElevation;
        if (altitude > 50) {
            points = 15;
        } else if (altitude > 25) {
            points = 5;
        }
        return points;
    }

    /**
     * Calculate the Slope
     *
     * @param startElevation start elevation
     * @param endElevation end elevation
     * @param distance distance
     * @return slop
     */
    public static double calcSlope(int startElevation, int endElevation, double distance) {
        double slope;
        if (distance > 0) {
            slope = (endElevation - startElevation) / distance;
            return (slope < 0) ? 0 : slope;
        }
        return 0;
    }

    /**
     * Calculate Air drag or resistance
     *
     * @return air drag
     */
    public static double calcK2() {
        return 0.5 * AIR_DENSITY * DRAG_COEF * FRONTAL_AREA;
    }

    /**
     * Calculate in watts (w) the energy required to travel from A to B
     *
     * @param m mass (user and bycicle)
     * @param slop slop
     * @param Va Wind speed
     * @param Vg vehicle speed
     * @param angle angle in degrees
     * @return energy required in watts
     */
    public static double calcPower(double m, double slop, double Va, double Vg,
            int angle) {

        double finalVa = 0;

        if (angle > 90 && angle < 180) {
            finalVa = Math.cos(180 - angle);
        } else {
            if (angle > 180 && angle < 270) {
                finalVa = Math.cos(angle - 180);
            }
            if (angle == 180) {
                finalVa = Va;
            }
        }

        return GRAVITY * m * Vg * (COEF_ALL_ATR_MECA + slop)
                + calcK2() * Math.pow(finalVa, 2) * Vg;

    }

    /**
     * Given the power return the calories wasted
     *
     * @param power power
     * @return calories
     */
    public static double calcCalories(double power) {
        return power * WATT_CAL;
    }

    /**
     *
     *
     * @param inputVoltage - park input voltage (in Volts)
     * @param inputCurrent - park input current (in Amperes)
     * @param maxBattery - escooter maximum battery capacity in kWh (2 dp)
     * @param actualBattery - escooter current battery capacity (%)
     * @param chargingPoints - charging points with
     * @return timeToFinish - time to finish charge in seconds
     */
    public static int calcEscooterChargeTime(int inputVoltage, int inputCurrent,
            float maxBattery, int actualBattery, int chargingPoints) {

        float missingBatteryCharge = maxBattery - (maxBattery * (actualBattery / 100));

        //P = V x I
        //Parks max. charg. capacity: 220 V * 16 A = 3520 W
        int loadPowerWatts = inputVoltage * inputCurrent;
        //W to kW and evenly split by each charging point
        float loadPowerKwatts = (loadPowerWatts / 1000.0f) / chargingPoints;

        //Charging time = battery capacity (kWh) / park charging power (kW)
        //Example with 1 charging point: 24 kWh / (3.52 kW * 0.90 charge efficiency) = 0.28 hours * 3600 = 27300 seconds        
        int timeToFinish = (int) (missingBatteryCharge / (loadPowerKwatts * 0.90f) * 3600);

        return timeToFinish;
    }

    /**
     * Calculate the duration given a distance
     *
     * @param distance distance
     * @return duration in seconds
     */
    public static double calcDurationInSeconds(double distance) {
        return (distance >= 0) ? distance / DEFAULT_VELOCITY : 0;
    }
}
