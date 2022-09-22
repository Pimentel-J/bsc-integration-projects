/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.model;

import java.util.List;

/**
 *
 *
 */
public class ServiceProvider {

    private String registryNumber;
    private String fullName;
    private String shortName;
    private String instEmail;
    private ServiceProviderCategoryList m_oSpCatList;
    private ServiceProviderGeographicalAreaList m_oSpGeoAreaList;
    private ServiceProviderEvaluation m_oSpEval;
    private PostalAddress m_oPostAddress;
    //SP now holds the ExecutionOrder registry
    private ExecutionOrderRegistry m_oExecOrderRegistry;
    
    public ServiceProvider(String registryNumber, String fullName, String shortName, String instEmail) {
        
        this.registryNumber = registryNumber;
        this.fullName = fullName;
        this.shortName = shortName;
        this.instEmail = instEmail;
        this.m_oSpCatList = new ServiceProviderCategoryList();
        this.m_oSpGeoAreaList = new ServiceProviderGeographicalAreaList();
        this.m_oSpEval = new ServiceProviderEvaluation();
        this.m_oPostAddress = null;
        //SP now holds the ExecutionOrder registry
        this.m_oExecOrderRegistry = new ExecutionOrderRegistry();
        
    }
    
    public ServiceProvider() {
        this.m_oSpCatList = new ServiceProviderCategoryList();
        this.m_oSpGeoAreaList = new ServiceProviderGeographicalAreaList();
        this.m_oSpEval = new ServiceProviderEvaluation();
        this.m_oExecOrderRegistry = new ExecutionOrderRegistry();
    }
    
    public void setSpCatList(ServiceProviderCategoryList m_oSpCatList) {
        this.m_oSpCatList = m_oSpCatList;
    }

    public void setSpGeoAreaList(ServiceProviderGeographicalAreaList m_oSpGeoAreaList) {
        this.m_oSpGeoAreaList = m_oSpGeoAreaList;
    }

    public ServiceProviderCategoryList getSpCatList() {
        return m_oSpCatList;
    }

    public ServiceProviderGeographicalAreaList getSpGeoAreaList() {
        return m_oSpGeoAreaList;
    }

    public String getRegistryNumber(){
        return this.registryNumber;
    }
    
    public ServiceProviderLabel getLabel(){
        return m_oSpEval.getLabel();
    }
    
    public ServiceProviderEvaluation getServiceProviderEvaluation(){
        return this.m_oSpEval;
    }
    
    public void setLabel(ServiceProviderLabel label){
        this.m_oSpEval.setLabel(label);
    }
    
    public String getFullName(){
        return this.fullName;
    }
    
    public String getEmail(){
        return this.instEmail;
    }
    
    public void addCategoryList(List<Category> catList) {
        m_oSpCatList.addCategoryList(catList);
    }
    
    public void addCategory(Category cat){
        boolean validated = validate(cat);
        if (validated) {
            this.m_oSpCatList.addCategory(cat);
        }
    }
    
    public void addGeographicalArea(GeographicalArea ga) {
        boolean validated = validate(ga);
        if (validated) {
            this.m_oSpGeoAreaList.addGeographicalArea(ga);
        }
    }
    
    public PostalAddress getPostalAddress() {
        return this.m_oPostAddress;
    }
    
    public void addPostalAddress(String place, String zipCode, String location) {
        PostalAddress postAddress = new PostalAddress(place, zipCode, location);
        boolean validated = validate(postAddress);
        if (validated) {
            add(postAddress);
        }
    }
    
    public void add(PostalAddress postAddress) {
        this.m_oPostAddress = postAddress;
    }
    
    public boolean validate(PostalAddress postAddress) {
        return true;
    }
    
    public boolean validate(Category cat){
        return (cat != null);
    }
    
    public boolean validate(GeographicalArea ga) {
        return (ga != null);
    }
    
    //SP now holds the ExecutionOrder registry
    public ExecutionOrderRegistry getExecutionOrderRegistry() {
        return this.m_oExecOrderRegistry;
    }

}
