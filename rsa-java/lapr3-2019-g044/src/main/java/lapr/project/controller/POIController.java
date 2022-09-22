/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.util.LinkedList;
import java.util.List;
import lapr.project.data.POIDB;
import lapr.project.model.POI;
import lapr.project.model.Point;

/**
 * Has methods for functionalities related to points of interest (POIs).
 */
public class POIController {
    
    private POIDB poiDb;

    public POIController() {
        poiDb = new POIDB();
    }

    /**
     * Set parkDb to instance.
     *
     * @param poiDb new instance of POIDB
     */
    public void setPoiDb(POIDB poiDb) {
        this.poiDb = poiDb;
    }

    /**
     * Validates, creates and adds POI to the table "POIs" according to the
     * data passed as parameters.
     *
     * @param latitude latitude of the POI.
     * @param longitude longitude of the POI.
     * @param elevation elevation/altitude (in metres) of the POI.
     * @param description descriptive name for the POI.
     * @return true if saved, false if data invalid
     */
    public boolean addPoi(double latitude, double longitude, int elevation, String description) {

        try {
            POI newPoi = new POI(latitude, longitude, elevation, description);
            return savePoi(newPoi);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Checks if POI passed already exists, otherwise inserts it in the "POIs"
     * table.
     *
     * @param poi POI to insert
     * @return true if insert operation successful, false if not
     */
    private boolean savePoi(POI poi) {
        try {
            poiDb.getPoi(poi.getLatitude(), poi.getLongitude());
            return false;
        } catch (IllegalArgumentException ex) {
            try {
                //If the record does not exist, save it
                poiDb.addPoi(poi);
                return true;
            } catch (IllegalArgumentException ex2) {
                return false;
            }
        }
    }
    
    /**
     * Validates, creates and adds POI to the table "POIs" according to the
     * data passed as parameters.
     *
     * @param latitudes latitudes of all the POIs to insert.
     * @param longitudes longitudes of all the POIs to insert.
     * @param elevations elevations/altitudes (in metres) of all the POIs to insert.
     * @param descriptions descriptive names for all the POIs to insert.
     * @return true if all saved, false if not
     */
    public boolean addPois(List<Double> latitudes, List<Double> longitudes, 
            List<Integer> elevations, List<String> descriptions) {
        
        List<POI> pois = new LinkedList<>();
        int numPois = latitudes.size();
        
        try {
            for (int i = 0; i < numPois; i++) {
                pois.add(new POI(latitudes.get(i), longitudes.get(i), elevations.get(i), descriptions.get(i)));
            }
            return savePois(pois);
        } catch (IllegalArgumentException ex) { // at least one record with invalid data
            return false;
        }
    }
    
    /**
     * Checks if POIs passed already exist, otherwise inserts them in the "POIs"
     * table.
     *
     * @param pois list of POIs to insert
     * @return true if all records were inserted, false if not
     */
    private boolean savePois(List<POI> pois) {
        int numPois = pois.size();
        
        // checks if any of the records exist already
        for (int i = 0; i < numPois; i++) {
            POI poi = pois.get(i);
            try {
                poiDb.getPoi(poi.getLatitude(), poi.getLongitude());
                return false;
            } catch (IllegalArgumentException ex) {
            }
        }
        
        // if none of the records exist, saves all of them
        try {
            poiDb.addPois(pois);
            return true;
        } catch (IllegalArgumentException ex2) {
            return false;
        }
    }
    
    /**
     * Returns a list with all the existing POIs.
     * 
     * @return a list with all the existing POIs.
     */
    public List<POI> getAllPois() {
        List<POI> pois = new LinkedList<>();
        pois.addAll(poiDb.getAllPois());
        return pois;
    }
    
    /**
     * Returns the number of POIs in a list of Points.
     * 
     * @param points list of Points
     * @return number of POIs
     */
    public int getNumPois(List<Point> points) {
        int numPois = 0;
        for (Point point : points) {
            if (point instanceof POI) {
                numPois++;
            }
        }
        return numPois;
    }
    
}
