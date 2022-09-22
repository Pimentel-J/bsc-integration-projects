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
public class ServicesRegistry {
    
    private List<Service> m_lstServices;

    public ServicesRegistry() 
    {
        this.m_lstServices = new ArrayList<>();
    }
    
    public boolean validateService(Service oServ) 
    {
        if (!oServ.validate()) 
        {
            return false;
        }
        return verifyService(oServ);
    }
    
    public boolean registerService(Service oServ) 
    {
        if (!validateService(oServ)) 
        {
            return false;
        }
        return addService(oServ);
    }
    
    private boolean verifyService(Service oServ) 
    {
        for (Service oService : m_lstServices) {
            if (oService.equals(oServ)) 
            {
                return false;
            }
        }
        return true;
    }
    
    private boolean addService(Service oServ) 
    {
        return m_lstServices.add(oServ);
    }
    
    public Service getServiceById(String serviceId) 
    {
        for (Service oService : m_lstServices) {
            if (oService.hasId(serviceId)) 
            {
                return oService;
            }
        }
        return null;
    }
    
}
