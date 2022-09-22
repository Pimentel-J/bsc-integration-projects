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
public class ServiceProviderGeographicalAreaList {
    
    private List<GeographicalArea> m_oGeoAreaList;
    
    public ServiceProviderGeographicalAreaList(){
        m_oGeoAreaList = new ArrayList<>();
    }
    
    public void addGeographicalArea(GeographicalArea ga) {
        boolean validated = validate(ga);
        if (validated) {
            m_oGeoAreaList.add(ga);
        }
    }
    
    public boolean validate(GeographicalArea ga) {
        return true;
    }
    
    public List<GeographicalArea> getGeographicalAreas() {
        List<GeographicalArea> lstGeoAreas = new ArrayList<>();
        lstGeoAreas.addAll(m_oGeoAreaList);
        return lstGeoAreas;
    }
    
}
