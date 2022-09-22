/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

/**
 * Describes a geographical point, such as a park or a POI, therefore having 
 * latitude and longitude.
 */
public interface Point {
    
    /**
     * Returns the latitude of the geographical point.
     * 
     * @return latitude
     */
    public double getLatitude();
    
    /**
     * Returns the longitude of the geographical point.
     * 
     * @return longitude
     */
    public double getLongitude();
    
    /**
     * Returns the longitude of the geographical point.
     * 
     * @return longitude
     */
    public int getElevation();
}
