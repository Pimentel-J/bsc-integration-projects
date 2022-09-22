/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

/**
 * Describes a point of interest (POI).
 */
public class POI implements Point {
    
    /**
     * Latitude of the POI.
     */
    private double latitude;
    /**
     * Longitude of the POI.
     */
    private double longitude;
    /**
     * Elevation/altitude (in metres) of the POI.
     */
    private int elevation;
    /**
     * Descriptive name for the POI.
     */
    private String description;
    
    /**
     * Default POI's latitude.
     */
    private final static double DEFAULT_LATITUDE = 0;
    /**
     * Default POI's longitude.
     */
    private final static double DEFAULT_LONGITUDE = 0;
    /**
     * Default POI's elevation.
     */
    private final static int DEFAULT_ELEVATION = 0;
    /**
     * Default POI's description.
     */
    private final static String DEFAULT_DESCRIPTION = null;
    
    /**
     * Empty constructor.
     */
    public POI() {
        this.latitude = DEFAULT_LATITUDE;
        this.longitude = DEFAULT_LONGITUDE;
        this.elevation = DEFAULT_ELEVATION;
        this.description = DEFAULT_DESCRIPTION;
    }

    /**
     * Full constructor.
     *
     * @param latitude latitude of the POI.
     * @param longitude longitude of the POI.
     * @param elevation elevation/altitude (in metres) of the POI.
     * @param description descriptive name for the POI.
     */
    public POI(double latitude, double longitude, int elevation, String description) {

        setLatitude(latitude);
        setLongitude(longitude);
        setElevation(elevation);
        setDescription(description);
    }

    /**
     * Returns the latitude of the POI.
     *
     * @return latitude (double)
     */
    @Override
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns the longitude of the POI.
     *
     * @return longitude (double)
     */
    @Override
    public double getLongitude() {
        return longitude;
    }

    /**
     * Returns the elevation of the POI.
     *
     * @return elevation (int)
     */
    @Override
    public int getElevation() {
        return elevation;
    }

    /**
     * Returns the description of the POI.
     *
     * @return description (String)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets POI's latitude to the value passed as parameter, if valid.
     *
     * @param latitude new latitude of the POI.
     */
    public final void setLatitude(double latitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Invalid POI latitude!");
        }
        this.latitude = latitude;
    }

    /**
     * Sets POI's longitude to the value passed as parameter, if valid.
     *
     * @param longitude new longitude of the POI.
     */
    public final void setLongitude(double longitude) {
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Invalid POI longitude!");
        }
        this.longitude = longitude;
    }

    /**
     * Sets POI's elevation to the value passed as parameter, if valid.
     *
     * @param elevation new elevation/altitude (in metres) of the POI.
     */
    public final void setElevation(int elevation) {
        this.elevation = elevation;
    }

    /**
     * Sets POI's description to the value passed as parameter, if valid.
     *
     * @param description new descriptive name for the POI.
     */
    public final void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns hashcode based on latitude and longitude.
     * 
     * @return hashcode (int)
     */
    @Override
    public int hashCode() {
        Double lat = this.latitude;
        Double longit = this.latitude;
        int hash = 17;
        hash = hash * 31 + lat.hashCode();
        hash = hash * 31 + longit.hashCode();
        return hash;
    }
    
    /**
     * Compares *this* object with the one passed as parameter.
     * 
     * @param obj object to compare
     * @return true if object passed is a POI and has same latitude and 
     * longitude as *this*; false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        POI poi = (POI) obj;
        Double lat1 = (Double) this.latitude, long1 = (Double) this.longitude;
        Double lat2 = (Double) poi.latitude, long2 = (Double) poi.longitude;
        return lat1.equals(lat2) && long1.equals(long2);
    }
    
    /**
     * Returns a String decsribing the POI.
     *
     * @return string
     */
    @Override
    public String toString() {
        return String.format("POI: [lat=%.6f], [long=%.6f], [alt=%dm], [descr=%s]\n",
                latitude, longitude, elevation, description);
    }
    
}
