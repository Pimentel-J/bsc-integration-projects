/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.controller;

import java.util.List;
import lapr2.project.agpsd.model.AGPSD;
import lapr2.project.agpsd.model.CategoriesRegistry;
import lapr2.project.agpsd.model.Category;
import lapr2.project.agpsd.model.Company;
import lapr2.project.agpsd.model.Service;
import lapr2.project.agpsd.model.ServiceType;
import lapr2.project.agpsd.model.ServiceTypesRegistry;
import lapr2.project.agpsd.model.ServicesRegistry;
import lapr2.project.agpsd.utils.Constants;

/**
 *
 *
 */
public class UC4SpecifyServiceController
{
    
    private final Company m_oEmpresa;
    private ServiceTypesRegistry m_oServTypesRegistry;
    private ServicesRegistry m_oServRegistry;
    private CategoriesRegistry m_oCatRegistry;
    private ServiceType m_oServType;
    private Service m_oService;
    
    public UC4SpecifyServiceController()
    {
        if(!AGPSD.getInstance().getCurrentSession().isLoggedInWithRole(Constants.ROLE_ADMINISTRATOR))
            throw new IllegalStateException("Non Authorized User!");
        
        this.m_oEmpresa = AGPSD.getInstance().getCompany();
    }
    
    public List<ServiceType> getServiceTypes() 
    {
        this.m_oServTypesRegistry = m_oEmpresa.getServiceTypesRegistry();
        return m_oServTypesRegistry.getServiceTypes();
    }
    
    public void setServiceType(ServiceType oServType) 
    {
        this.m_oServType = oServType;
    }
    
    public List<Category> getCategories()
    {
        this.m_oCatRegistry = m_oEmpresa.getCategoriesRegistry();
        return m_oCatRegistry.getCategories();
    }
    
    public boolean newService(String strId, String strShortDesc, String strLongDesc, double dCost, Category oCat)
    {
        this.m_oService = m_oServType.newService(strId, strShortDesc, strLongDesc, dCost, oCat);
        return m_oService.hasOtherAttributes();
    }
    
    public String getOtherAttributes() 
    {
        return m_oService.getOtherAttributes();
    }
    
    public boolean setAdditionalData(double dData) 
    {
        return m_oService.setAdditionalData(dData);
    }
    
    public boolean validate() 
    {
        this.m_oServRegistry = m_oEmpresa.getServicesRegistry();
        return m_oServRegistry.validateService(m_oService);
    }
    
    public boolean registerService() 
    {
        return m_oServRegistry.registerService(m_oService);
    }
    
    public Service getService() 
    {
        return this.m_oService;
    }

}
