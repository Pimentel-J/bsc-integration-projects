/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import lapr.project.data.ParkDB;
import lapr.project.model.Park;
import lapr.project.utils.Utils;

/**
 *
 *
 */
public class ParkController {

    private ParkDB parkDb;

    public ParkController() {
        parkDb = new ParkDB();
    }

    /**
     * Set parkDb to instance.
     *
     * @param parkDb new instance of ParkDB
     */
    public void setParkDb(ParkDB parkDb) {
        this.parkDb = parkDb;
    }

    /**
     * Method that returns the distance between a user and a given park
     *
     * @param userLatitude, Double, user´s latitude
     * @param userLongetitude, Double, user´s latitude
     * @param idPark, String, park id to get lat and long
     * @return distance, Double, distance between a user and a given park
     */
    public double getDistanceFromUserToPark(double userLatitude, double userLongetitude, String idPark) {
        double distance = 0.0;
        try {
            Park park = parkDb.getPark(idPark);
            if (park != null) {
                distance = Utils.distance(userLatitude, userLongetitude, park.getLatitude(), park.getLongitude());
            }
            return distance;
        } catch (IllegalArgumentException ex) {
            return 0.0;
        }
    }


    /**
     * Method that returns the park with certain coordinates
     *
     * @param latitude, Double, park latitude
     * @param longitude, Double, park latitude
     * @return park park object that as matching coordinates
     */
     public Park getParkByCoordinates(double latitude, double longitude) {
        return parkDb.getParkByCoordinates(latitude, longitude);
    }

    /**
     * Method that returns the park witha certain id
     *
     * @param id, String, park´s id
     * @return park park object that as matching id
     */
    public Park getParkById(String id) {
        return parkDb.getPark(id);
    }

}
