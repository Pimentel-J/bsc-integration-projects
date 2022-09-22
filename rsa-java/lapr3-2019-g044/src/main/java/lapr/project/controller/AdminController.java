/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import lapr.project.data.BicycleDB;
import lapr.project.data.EscooterDB;
import lapr.project.data.ParkDB;
import lapr.project.model.Bicycle;
import lapr.project.model.Escooter;
import lapr.project.model.Escooter.Type;
import lapr.project.model.Park;
import lapr.project.model.Vehicle;
import lapr.project.utils.Utils;

/**
 * Has methods for functionalities related to the administrator.
 */
public class AdminController {

    private ParkDB parkDB;
    private BicycleDB bicycleDB;
    private EscooterDB escooterDB;

    public AdminController() {
        parkDB = new ParkDB();
        bicycleDB = new BicycleDB();
        escooterDB = new EscooterDB();
    }

    /**
     * Set parkDB to instance.
     *
     * @param parkDB new instance of ParkDB
     */
    public void setParkDB(ParkDB parkDB) {
        this.parkDB = parkDB;
    }

    /**
     * Set bicycleDB to instance.
     *
     * @param bicycleDB - new instance of bicycleDB
     */
    public void setBicycleDB(BicycleDB bicycleDB) {
        this.bicycleDB = bicycleDB;
    }

    /**
     * Set escooterDB to instance.
     *
     * @param escooterDB - new instance of escooterDB
     */
    public void setEscooterDB(EscooterDB escooterDB) {
        this.escooterDB = escooterDB;
    }

    /**
     * Validates, creates and adds park to the table "Parks" according to the
     * data passed as parameters.
     *
     * @param id identifier of the park.
     * @param latitude latitude of the park.
     * @param longitude longitude of the park.
     * @param elevation elevation/altitude (in metres) of the park.
     * @param description descriptive name for the park.
     * @param maxNumberBicycles maximum park capability for bicycles.
     * @param maxNumberEscooters maximum park capability for escooters.
     * @param inputVoltage park input voltage (in Volts).
     * @param inputCurrent park input current (in Amperes).
     * @return true if saved, false if data invalid
     */
    public boolean addPark(String id, double latitude, double longitude, int elevation,
            String description, int maxNumberBicycles, int maxNumberEscooters, int inputVoltage, int inputCurrent) {

        try {
            Park newPark = new Park(id, latitude, longitude, elevation, description,
                    maxNumberBicycles, maxNumberEscooters, inputVoltage, inputCurrent);
            return savePark(newPark);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Checks if park passed already exists, otherwise inserts it in the "Parks"
     * table.
     *
     * @param park park to insert
     * @return true if insert operation successful, false if not
     */
    private boolean savePark(Park park) {
        try {
            parkDB.getPark(park.getId());
            return false;
        } catch (IllegalArgumentException ex) {
            try {
                //If the record does not exist, save it
                parkDB.addPark(park);
                return true;
            } catch (IllegalArgumentException ex2) {
                return false;
            }
        }
    }

    /**
     * Validates, creates and adds Parks to the table "Parks" according to the
     * data passed as parameters.
     *
     * @param ids ids of all the Parks to insert.
     * @param latitudes latitudes of all the Parks to insert.
     * @param longitudes longitudes of all the Parks to insert.
     * @param elevations elevations/altitudes (in metres) of all the Parks to
     * insert.
     * @param descriptions descriptive names for all the Parks to insert.
     * @param maxNumsBikes maximum park capabilities for bicycles of all the
     * Parks to insert.
     * @param maxNumsEscooters maximum park capabilities for escooters of all
     * the Parks to insert.
     * @param inputVoltages input voltage (in Volts) of all the Parks to insert.
     * @param inputCurrents input current (in Amperes) of all the Parks to
     * insert.
     * @return true if all saved, false if not
     */
    public boolean addParks(List<String> ids, List<Double> latitudes, List<Double> longitudes,
            List<Integer> elevations, List<String> descriptions, List<Integer> maxNumsBikes,
            List<Integer> maxNumsEscooters, List<Integer> inputVoltages, List<Integer> inputCurrents) {

        List<Park> parks = new LinkedList<>();
        int numParks = latitudes.size();

        try {
            for (int i = 0; i < numParks; i++) {
                parks.add(new Park(
                        ids.get(i),
                        latitudes.get(i),
                        longitudes.get(i),
                        elevations.get(i),
                        descriptions.get(i),
                        maxNumsBikes.get(i),
                        maxNumsEscooters.get(i),
                        inputVoltages.get(i),
                        inputCurrents.get(i)
                ));
            }
            return saveParks(parks);
        } catch (IllegalArgumentException ex) { // at least one record with invalid data
            return false;
        }
    }

    /**
     * Checks if Parks passed already exist, otherwise inserts them in the
     * "Parks" table.
     *
     * @param parks list of parks to insert
     * @return true if all records were inserted, false if not
     */
    private boolean saveParks(List<Park> parks) {
        int numParks = parks.size();

        // checks if any of the records exist already
        for (int i = 0; i < numParks; i++) {
            Park park = parks.get(i);
            Park temp = getPark(park.getId());
            if (temp != null) {
                return false;
            }
        }

        // if none of the records exist, saves all of them
        try {
            parkDB.addParks(parks);
            return true;
        } catch (IllegalArgumentException ex2) {
            return false;
        }
    }

    /**
     * Returns the specified park if it exists in the DB.
     *
     * @param id identifier of the park.
     * @return specified park or null if it does not exist
     */
    private Park getPark(String id) {
        try {
            return parkDB.getPark(id);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    /**
     *
     * Method to update a park
     *
     * @param id
     * @param longitude
     * @param elevation
     * @param description
     * @param maxNumberBicycles
     * @param maxNumberEscooters
     * @param inputVoltage
     * @param inputCurrent
     * @param latitude
     * @return park id
     */
    public String updatePark(String id, double latitude, double longitude, int elevation,
            String description, int maxNumberBicycles, int maxNumberEscooters, int inputVoltage, int inputCurrent) {

        try {

            if (parkDB.getPark(id) != null) {

                Park park = new Park(id, latitude, longitude, elevation, description, maxNumberBicycles, maxNumberEscooters, inputVoltage, inputCurrent);

                return parkDB.updatePark(park);
            } else {

                return "";
            }
        } catch (IllegalArgumentException ex) {
            return "";
        }

    }

    /**
     *
     * Method to delete a park
     *
     * @param id
     * @return park id
     */
    public String deletePark(String id) {
        try {

            if (parkDB.getPark(id) != null) {
                return parkDB.deletePark(id);
            } else {

                return "";
            }
        } catch (IllegalArgumentException ex) {
            return "";
        }
    }

    /**
     * Creates and adds a bicycle to the DB's corresponding table
     *
     * @param descr - unique bicycle reference
     * @param weight - bicycle weight in kg (no decimal places)
     * @param parkLat - latitude of the park where the bicycle is parked
     * (decimal degrees)
     * @param parkLong - longitude of the park where the bicycle is parked
     * (decimal degrees)
     * @param aerodynCoeffic - bicycle aerodynamic coefficient (2 decimal
     * places)
     * @param frontalArea - bicycle frontal area in m^2 (1 decimal place)
     * @param wheelSize - bicycle wheel size in inches
     * @return true if saved, false if data invalid
     */
    public boolean addBicycle(String descr, int weight, double parkLat, double parkLong,
            float aerodynCoeffic, float frontalArea, int wheelSize) {
        try {
            Bicycle newBicycle = new Bicycle(descr, weight, parkLat, parkLong,
                    aerodynCoeffic, frontalArea, wheelSize);
            return saveBicycle(newBicycle);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Checks if a bicycle already exists in DB, otherwise inserts it in the
     * Bicycle's table.
     *
     * @param bicycle - bicycle instance
     * @return true if insert's operation successful, false if not
     */
    private boolean saveBicycle(Bicycle bicycle) {
        try {
            bicycleDB.getBicycle(bicycle.getDescr());
            return false;
        } catch (IllegalArgumentException ex) {
            try {
                //Check if the park with the vehicle's coordinates exists
                parkDB.getParkByCoordinates(bicycle.getParkLat(), bicycle.getParkLong());
                //If the record does not exist, save it
                bicycleDB.addBicycle(bicycle);
                return true;
            } catch (IllegalArgumentException ex2) {
                return false;
            }
        }
    }

    /**
     * Creates and adds bicycles to the DB's corresponding table
     *
     * @param descriptions - list with unique bicycle references
     * @param weights - list with bicycle weights in kg (no decimal places)
     * @param parkLats - list with latitudes of the parks where the bicycles are
     * parked (decimal degrees)
     * @param parkLongs - list with longitudes of the parks where the bicycles
     * are parked (decimal degrees)
     * @param aerodynCoeffics - aerodynamic coefficients of bicycles (2 decimal
     * places)
     * @param frontalAreas - bicycle frontal areas in m^2 (1 decimal place)
     * @param wheelSizes - bicycle wheel sizes in inches
     * @return true if saved, false if data invalid
     */
    public boolean addBicycles(List<String> descriptions, List<Integer> weights,
            List<Double> parkLats, List<Double> parkLongs, List<Float> aerodynCoeffics,
            List<Float> frontalAreas, List<Integer> wheelSizes) {

        List<Bicycle> bikes = new LinkedList<>();
        int numBikes = descriptions.size();

        try {
            for (int i = 0; i < numBikes; i++) {
                bikes.add(new Bicycle(descriptions.get(i), weights.get(i), parkLats.get(i),
                        parkLongs.get(i), aerodynCoeffics.get(i), frontalAreas.get(i), wheelSizes.get(i)));
            }
            return saveBicycles(bikes);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Checks if the bicycles already exists in DB, otherwise inserts them in
     * the Bicycle's table.
     *
     * @param bicycles - list with bicycle instances
     * @return true if insert's operation successful, false if not
     */
    private boolean saveBicycles(List<Bicycle> bicycles) {
        int numBikes = bicycles.size();

        for (int i = 0; i < numBikes; i++) {
            Bicycle bike = bicycles.get(i);
            try {
                //Checks if any of the records exist already
                bicycleDB.getBicycle(bike.getDescr());
                return false;
            } catch (IllegalArgumentException ex) {
                try {
                    //Check if the park with the vehicle's coordinates exists
                    parkDB.getParkByCoordinates(bike.getParkLat(), bike.getParkLong());
                } catch (IllegalArgumentException ex2) {
                    return false;
                }
            }
        }

        try {
            //If the record does not exist, save it
            bicycleDB.addBicycles(bicycles);
            return true;
        } catch (IllegalArgumentException ex3) {
            return false;
        }
    }

    /**
     *
     * Method to delete a bicycle
     *
     * @param descr - bicycle description
     * @return bicycle reference or empty string if not deleted
     */
    public String deleteBicycle(String descr) {
        try {
            if (bicycleDB.getBicycle(descr) != null
                    && bicycleDB.getBicycle(descr).getParkLat() != 0
                    && bicycleDB.getBicycle(descr).getParkLong() != 0) {
                return bicycleDB.deleteBicycle(descr);
            } else {
                return "";
            }
        } catch (IllegalArgumentException ex) {
            return "";
        }
    }

    /**
     * Creates and adds a escooter to the DB's corresponding table
     *
     * @param descr - unique escooter reference
     * @param weight - escooter weight in kg (no decimal places)
     * @param type - type of escooter (city or off-road)
     * @param parkLat - latitude of the park where the escooter is parked
     * (decimal degrees)
     * @param parkLong - longitude of the park where the escooter is parked
     * (decimal degrees)
     * @param maxBattery - escooter maximum battery capacity in kWh (2 decimal
     * places)
     * @param actualBattery - escooter current battery capacity in percentage
     * (no decimal places)
     * @param aerodynCoeffic - escooter aerodynamic coefficient (2 decimal
     * places)
     * @param frontalArea - escooter frontal area in m^2 (1 decimal place)
     * @param motor - escooter motor power in kWh (2 decimal places)
     * @return true if saved, false if data invalid
     */
    public boolean addEscooter(String descr, int weight, Type type, double parkLat, double parkLong,
            float maxBattery, int actualBattery, float aerodynCoeffic, float frontalArea, float motor) {
        try {
            Escooter newEscooter = new Escooter(descr, weight, type, parkLat, parkLong,
                    maxBattery, actualBattery, aerodynCoeffic, frontalArea, motor);
            return saveEscooter(newEscooter);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Checks if a escooter already exist in DB, otherwise inserts it in the
     * Escooter's table.
     *
     * @param escooter - escooter instance
     * @return true if insert's operation successful, false if not
     */
    private boolean saveEscooter(Escooter escooter) {
        try {
            escooterDB.getEscooter(escooter.getDescr());
            return false;
        } catch (IllegalArgumentException ex) {
            try {
                //Check if the park with the vehicle's coordinates exists
                parkDB.getParkByCoordinates(escooter.getParkLat(), escooter.getParkLong());
                //If the record does not exist, save it
                escooterDB.addEscooter(escooter);
                return true;
            } catch (IllegalArgumentException ex2) {
                return false;
            }
        }
    }

    /**
     * Creates and adds escooters to the DB's corresponding table
     *
     * @param descriptions - list with unique escooter references
     * @param weights - list with escooter weights in kg (no decimal places)
     * @param types -
     * @param parkLats - list with latitudes of the parks where the escooters
     * are parked (decimal degrees)
     * @param parkLongs - list with longitudes of the parks where the escooters
     * are parked (decimal degrees)
     * @param maxBatteries - maximum battery capacity of escooters in kWh (2
     * decimal places)
     * @param actualBatteries - current battery capacity of escooters in
     * percentage (no decimal places)
     * @param aerodynCoeffics - aerodynamic coefficients of escooters (2 decimal
     * places)
     * @param frontalAreas - escooter frontal areas in m^2 (1 decimal place)
     * @param motors - motor power of escooters in kWh (2 decimal places)
     * @return true if saved, false if data invalid
     */
    public boolean addEscooters(List<String> descriptions, List<Integer> weights,
            List<Escooter.Type> types, List<Double> parkLats, List<Double> parkLongs,
            List<Float> maxBatteries, List<Integer> actualBatteries,
            List<Float> aerodynCoeffics, List<Float> frontalAreas, List<Float> motors) {

        List<Escooter> escooters = new LinkedList<>();
        int numScooters = descriptions.size();

        try {
            for (int i = 0; i < numScooters; i++) {
                escooters.add(new Escooter(descriptions.get(i), weights.get(i), types.get(i),
                        parkLats.get(i), parkLongs.get(i), maxBatteries.get(i),
                        actualBatteries.get(i), aerodynCoeffics.get(i),
                        frontalAreas.get(i), motors.get(i)));
            }
            return saveEscooters(escooters);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Checks if the escooters already exist in DB, otherwise inserts them in
     * the Escooters's table.
     *
     * @param escooters - list with escooter instances
     * @return true if insert's operation successful, false if not
     */
    private boolean saveEscooters(List<Escooter> escooters) {
        int numScooters = escooters.size();

        for (int i = 0; i < numScooters; i++) {
            Escooter escooter = escooters.get(i);
            try {
                //Checks if any of the records exist already
                escooterDB.getEscooter(escooter.getDescr());
                return false;
            } catch (IllegalArgumentException ex) {
                try {
                    //Check if the park with the vehicle's coordinates exists
                    parkDB.getParkByCoordinates(escooter.getParkLat(), escooter.getParkLong());
                } catch (IllegalArgumentException ex2) {
                    return false;
                }
            }
        }

        try {
            //If the record does not exist, save it
            escooterDB.addEscooters(escooters);
            return true;
        } catch (IllegalArgumentException ex3) {
            return false;
        }
    }

    /**
     *
     * Method to delete a escooter
     *
     * @param descr - escooter description
     * @return escooter reference or empty string if not deleted
     */
    public String deleteEscooter(String descr) {
        try {
            if (escooterDB.getEscooter(descr) != null
                    && escooterDB.getEscooter(descr).getParkLat() != 0
                    && escooterDB.getEscooter(descr).getParkLong() != 0) {
                return escooterDB.deleteEscooter(descr);
            } else {
                return "";
            }
        } catch (IllegalArgumentException ex) {
            return "";
        }
    }

    /**
     * Returns a TreeSet with all vehicles. Returns an empty set if there's no
     * vehicles.
     *
     * @return vehicles - sorted list of vehicle type objects (alphabetically by
     * description)
     */
    public Set<Vehicle> getAllVehicles() {
        Set<Vehicle> vehicles = new TreeSet<>();
        vehicles.addAll(bicycleDB.getAllBicycles());
        vehicles.addAll(escooterDB.getAllEscooters());
        return vehicles;
    }

    /**
     * Returns a TreeSet with all parks. Returns an empty set if there's no
     * parks.
     *
     * @return parks - sorted list of parks (id ascending order)
     */
    public Set<Park> getAllParks() {
        Set<Park> parks = new TreeSet<>();
        parks.addAll(parkDB.getAllParks());
        return parks;
    }

    /**
     * Returns a TreeSet with all escooters charging status at a given park.
     * Returns an empty set if there's no escooters.
     *
     * @param id - park identification
     * @return escooters - sorted list of escooters (descending order of time to
     * finish charge (seconds) and secondly by ascending escooter description
     * order)
     */
    public List<Escooter> getParkChargingReport(String id) {
        Park park = parkDB.getPark(id);
        double parkLat = park.getLatitude();
        double parkLong = park.getLongitude();

        List<Escooter> escootersList = escooterDB.getAllNotFullEscootersFromPark(parkLat, parkLong);
        int escooterSize = escootersList.size();
        for (Escooter escooter : escootersList) {
            int totalChargeTime = Utils.calcEscooterChargeTime(park.getInputVoltage(),
                    park.getInputCurrent(), escooter.getMaxBattery(), 
                    escooter.getActualBattery(), escooterSize);
            escooter.setTotalChargeTime(totalChargeTime);
        }
        
        Collections.sort(escootersList, new EscooterCompareChargeTime());
        
        return escootersList;
    }

    /**
     * Class with the criteria to compare a scooter by descending order of time
     * to finish charge in seconds and secondly by ascending escooter
     * description order
     */
    class EscooterCompareChargeTime implements Comparator<Escooter> {

        @Override
        public int compare(Escooter sOne, Escooter sTwo) {
            int timeTotalChargeOne = sTwo.getTotalChargeTime();
            int timeTotalChargeTwo = sTwo.getTotalChargeTime();

            if (timeTotalChargeOne < timeTotalChargeTwo) {
                return -1;
            } else if (timeTotalChargeOne > timeTotalChargeTwo) {
                return 1;
            } else {
                return sOne.compareTo(sTwo);
            }
        }
    }
}
