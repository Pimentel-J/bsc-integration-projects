/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.model;

/**
 *
 *
 */
public interface Service 
{
    boolean hasOtherAttributes();
    
    String getOtherAttributes();
    
    boolean setAdditionalData(double dData);
    
    boolean validate();
    
    boolean hasId(String strId);
    
    @Override
    boolean equals(Object o);
    
    @Override
    String toString();
    
    Category getCat();
    
    ServiceType getServiceType();
    
    String getId();
    
}
