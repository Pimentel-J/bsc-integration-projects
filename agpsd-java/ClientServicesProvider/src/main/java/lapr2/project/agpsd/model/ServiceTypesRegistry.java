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
public class ServiceTypesRegistry 
{
    private List<ServiceType> m_lstServTypes;

    public ServiceTypesRegistry() 
    {
        this.m_lstServTypes = new ArrayList<>();
    }
    
    public boolean setServiceTypes(List<ServiceType> lstServTypes) 
    {
        if (m_lstServTypes.isEmpty()) {
            m_lstServTypes.addAll(lstServTypes);
        }
        return true;
    }
    
    public List<ServiceType> getServiceTypes() 
    {
        List<ServiceType> lstServTypes = new ArrayList<>();
        lstServTypes.addAll(m_lstServTypes);
        return lstServTypes;
    }
    
    public ServiceType getServiceTypeByDesignation(String strDesignation) 
    {
        for (ServiceType oType : m_lstServTypes) 
        {
            if (oType.hasDesignation(strDesignation)) 
            {
                return oType;
            }
        }
        return null;
    }
    
    
}
