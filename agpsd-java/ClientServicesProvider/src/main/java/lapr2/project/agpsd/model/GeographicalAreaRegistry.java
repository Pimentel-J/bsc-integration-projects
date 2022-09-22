/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class GeographicalAreaRegistry {

    private List<GeographicalArea> m_lstGeoArea;
    
    /**
     * @param m_lstGeoArea
     */
    public GeographicalAreaRegistry() {
        this.m_lstGeoArea = new ArrayList<>();
    }
    
    /**
     *
     * @return m_lstGeoArea
     */
    public List<GeographicalArea> getGeographicalAreaList(){
        return this.m_lstGeoArea;
    }
    
    /**
     *
     * @param designation
     * @return ga and/or null
     */
    public GeographicalArea getGeographicalAreaByID(String designation) {
        for (GeographicalArea ga : m_lstGeoArea) {
            if (ga.getDesignation().equals(designation)) {
                return ga;
            }
        }
        return null;
    }
    
    /**
     *
     * @param strDesignation
     * @param dTransportFee
     * @param strZipCode
     * @param dActionRadius
     * @param oExtServ
     * @return
     */
    public GeographicalArea newGeographicalArea(String strDesignation, double dTransportFee, 
            String strZipCode, double dActionRadius, ExternalService oExtServ) {
        return new GeographicalArea(strDesignation, dTransportFee, strZipCode, dActionRadius, oExtServ);
    }
    
    /**
     *
     * @param oGeoArea
     * @return true or false
     */
    public boolean registerGeographicalArea(GeographicalArea oGeoArea) {
        if (!validateGeographicalArea(oGeoArea)) {
            return false;
        }
        return addGeographicalArea(oGeoArea);
    }
    
    private boolean validateGeographicalArea(GeographicalArea oGeoArea) {
        for (GeographicalArea ga : m_lstGeoArea) {
            if (ga.equals(oGeoArea)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean addGeographicalArea(GeographicalArea oGeoArea) {
        return this.m_lstGeoArea.add(oGeoArea);
    }
    
    /**
     *
     * @param zipCode
     * @return closest
     */
    public GeographicalArea getCloserGeoArea(String zipCode) {
        GeographicalArea closest = null;
        double shortestDist = Double.POSITIVE_INFINITY;
        for (GeographicalArea geoArea : m_lstGeoArea) {
            double distance = geoArea.hasZipCode(zipCode);
            if (distance < shortestDist) {
                closest = geoArea;
                shortestDist = distance;
            }
        }
        return closest;
    }
            
}
