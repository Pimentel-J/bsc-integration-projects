/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lapr.project.data.BicycleDB;
import lapr.project.data.EscooterDB;
import lapr.project.model.Bicycle;
import lapr.project.model.Escooter;
import lapr.project.model.Escooter.Type;
import lapr.project.model.Park;
import lapr.project.model.Vehicle;
import lapr.project.utils.Utils;

/**
 *
 *
 */
public class VehicleController {

    private BicycleDB bicycleDB;
    private EscooterDB eScooterDB;

    public VehicleController() {
        bicycleDB = new BicycleDB();
        eScooterDB = new EscooterDB();
    }

    /**
     * Set bicycleDB to instance.
     *
     * @param bicyleDB new instance of bicyleDB
     */
    public void setBicycleDB(BicycleDB bicyleDB) {
        this.bicycleDB = bicyleDB;
    }

    /**
     * Set eScooterDB to instance.
     *
     * @param eScooterDB new instance of eScooterDB
     */
    public void setEscooterDB(EscooterDB eScooterDB) {
        this.eScooterDB = eScooterDB;
    }

    /**
     * Method that returns all available Bicycles at a park.
     *
     * @param park object representative of Park
     * @return bikesAtPark list with all available Bicycles
     */
    public ArrayList<Bicycle> getAllBikes(Park park) {
        List<Bicycle> allBikes = bicycleDB.getAllBicycles();
        ArrayList<Bicycle> bikesAtPark = new ArrayList<>();
        for (Bicycle bike : allBikes) {
            if (bike.getParkLat() == park.getLatitude() && bike.getParkLong() == park.getLongitude()) {
                bikesAtPark.add(bike);
            }
        }
        Collections.sort(bikesAtPark);
        return bikesAtPark;
    }

    /**
     * Method that returns a list with all available Escooters at a park.
     *
     * @param park object representative of Park
     * @return escootersAtPark list wiith all available Escooters
     */
    public ArrayList<Escooter> getAllEscooters(Park park) {
        List<Escooter> allEscooters = eScooterDB.getAllEscooters();
        ArrayList<Escooter> escootersAtPark = new ArrayList<>();
        for (Escooter escooter : allEscooters) {
            if (escooter.getParkLat() == park.getLatitude() && escooter.getParkLong() == park.getLongitude()) {
                escootersAtPark.add(escooter);
            }
        }
        Collections.sort(escootersAtPark);
        return escootersAtPark;
    }

    /**
     * Method that returns a bicycle.
     *
     * @param descr description of a bicycle
     * @return bike bicycle with mathcing description
     */
    public Bicycle getBikeByDesc(String descr) {
        try {
            Bicycle bike = bicycleDB.getBicycle(descr);
            return bike;
        } catch (IllegalArgumentException ex1) {
            return null;
        }
    }

    /**
     * Method that returns a escooter.
     *
     * @param descr description of a Escooter
     * @return bike bicycle with mathcing description
     */
    public Escooter getEscooterByDesc(String descr) {
        try {
            Escooter eScooter = eScooterDB.getEscooter(descr);
            return eScooter;
        } catch (IllegalArgumentException ex1) {
            return null;
        }
    }

    /**
     * Method that updates a vehicle.
     *
     * @param descr description of vehicle
     * @param weight weight of vehicle
     * @param parkLat park latitude of vehicle
     * @param parkLong park longitude of vehicle
     * @param aerodynCoeffic Aerodynamic Coefficient of vehicle
     * @param frontalArea frontal Area of vehicle
     * @param type Type of vehicle
     * @param maxBattery Max battery of vehicle
     * @param actualBattery Actual battery of vehicle
     * @param wheelSize wheel size of vehicle
     * @param motor motor power of vehicle
     *
     * @return true (if upade with sucess) or false (it dons't update)
     */
    public boolean updateVehicle(String descr, int weight, double parkLat, double parkLong,
            float aerodynCoeffic, float frontalArea, Type type, float maxBattery,
            int actualBattery, int wheelSize, float motor) {

        if (type == Utils.NO_TYPE && maxBattery == Utils.NO_MAX_BATTERY
                && actualBattery == Utils.NO_ACT_BATTERY && motor == Utils.NO_MOTOR) {
            return bicycleDB.updateBicycle(new Bicycle(descr, weight, parkLat, parkLong,
                    aerodynCoeffic, frontalArea, wheelSize));
        }
        if (type != Utils.NO_TYPE && maxBattery > Utils.NO_MAX_BATTERY
                && actualBattery >= Utils.NO_ACT_BATTERY && motor > Utils.NO_MOTOR
                && wheelSize == Utils.NO_WHEEL_SIZE) {
            return eScooterDB.updateEscooter(new Escooter(descr, weight, type, parkLat,
                    parkLong, maxBattery, actualBattery, aerodynCoeffic, frontalArea, motor));
        }
        return false;
    }
    
    /**
     * Returns the vehicle with the description passed as parameters, wether it 
     * is a bicycle, an escooter... Returns null if vehicle doesn't exist.
     * 
     * @param description identification of the vehicle
     * @return Vehicle with the description passed; or null if no vehicle
     */
    public Vehicle getVehicle(String description) {
        Vehicle vehicle;
        try {
            vehicle = bicycleDB.getBicycle(description);
        } catch(IllegalArgumentException ex) {
            try {
                vehicle = eScooterDB.getEscooter(description);
            } catch(IllegalArgumentException ex2) {
                vehicle = null;
            }
        }
        return vehicle;
    }
    
    /**
     * Returns the number of bicycles currently locked in the park
     * passed as parameter.
     * 
     * @param park park
     * @return number of bicycles in park
     */
    public int getNumberOfBikesFromPark(Park park) {
        List<Bicycle> bikesFromPark = this.getAllBikes(park);
        return bikesFromPark.size();
    }
    
    /**
     * Returns the number of escooters currently locked in the park 
     * passed as parameter.
     * 
     * @param park id of park
     * @return number of escooters in park
     */
    public int getNumberOfEscootersFromPark(Park park) {
        List<Escooter> escootersFromPark = this.getAllEscooters(park);
        return escootersFromPark.size();
    }
}
