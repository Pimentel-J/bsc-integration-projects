/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.controller;

import lapr2.project.agpsd.model.AGPSD;
import lapr2.project.agpsd.model.Company;
import lapr2.project.agpsd.model.ExternalService;
import lapr2.project.agpsd.model.GeographicalArea;
import lapr2.project.agpsd.model.GeographicalAreaRegistry;
import lapr2.project.agpsd.utils.Constants;

/**
 *
 *
 */
public class UC5SpecifyGeographicalAreaController 
{
    
    private final Company m_oEmpresa;
    private GeographicalAreaRegistry m_oGeoAreaRegistry;
    private GeographicalArea m_oGeoArea;
    
    public UC5SpecifyGeographicalAreaController()
    {
        if(!AGPSD.getInstance().getCurrentSession().isLoggedInWithRole(Constants.ROLE_ADMINISTRATOR))
            throw new IllegalStateException("Non Authorized User!");
        
        this.m_oEmpresa = AGPSD.getInstance().getCompany();
    }
    
    public void newGeographicalArea(String strDesignation, double dTransportFee, 
            String strZipCode, double dActionRadius) 
    {
        this.m_oGeoAreaRegistry = m_oEmpresa.getGeographicalAreaRegistry();
        ExternalService oExtServ = m_oEmpresa.getExternalService();
        this.m_oGeoArea = m_oGeoAreaRegistry.newGeographicalArea(strDesignation, dTransportFee, strZipCode, dActionRadius, oExtServ);
    }
    
    public boolean registerGeographicalArea() 
    {
        return this.m_oGeoAreaRegistry.registerGeographicalArea(m_oGeoArea);
    }
    
    public GeographicalArea getGeographicalArea() 
    {
        return this.m_oGeoArea;
    }
    
}
