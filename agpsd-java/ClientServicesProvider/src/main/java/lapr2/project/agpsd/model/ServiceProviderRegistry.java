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
public class ServiceProviderRegistry {

    public List<ServiceProvider> getServiceProviderList() {
        return m_oServiceProviderList;
    }
    
    private List<ServiceProvider> m_oServiceProviderList;
    private ServiceProvider sp;
    
    public ServiceProviderRegistry() {
        this.m_oServiceProviderList = new ArrayList<ServiceProvider>();
    }
    
    public ServiceProvider newServiceProvider(String fullName, String shortName, String registryNumber, String email) {
        sp = new ServiceProvider(fullName, shortName, registryNumber, email);
        return sp;
    }
    
    public void registerServiceProvider() {
        boolean validated = validate();
        if (validated) {
            m_oServiceProviderList.add(sp);
        
        String pwd = generatePassword();
        AGPSD.getInstance().getCompany().getAutorizacaoFacade().registerUser(sp.getFullName(), sp.getEmail(), pwd);
        }
    }
    
    public void registerServiceProvider(String pwd) {
        boolean validated = validate();
        m_oServiceProviderList.add(sp);
        AGPSD.getInstance().getCompany().getAutorizacaoFacade().registerUser(sp.getFullName(), sp.getEmail(), pwd);
    }
    
    public ServiceProvider getServiceProviderByID(String ServiceProviderID) {
        for (ServiceProvider sp : m_oServiceProviderList) {
            if (sp.getRegistryNumber().equals(ServiceProviderID)) {
                return sp;
            }
        }
        return null;
    }
        
    public boolean validate(){
        return sp.getEmail() != null && sp.getFullName() != null && sp.getPostalAddress() != null && sp.getRegistryNumber() != null && sp.getSpCatList() != null && sp.getSpGeoAreaList() != null;
    }
    
    public String generatePassword(){
        return "pwd1";
    }
    
    public void addServiceProvider(ServiceProvider oSP) {
        this.m_oServiceProviderList.add(oSP);
    }
}
