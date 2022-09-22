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
public class RequestedServiceDescription {
    
    private double quantity;
    private String description;
    private Service service;
    private ProviderPairing oProviderPairing;
    
    public RequestedServiceDescription(double quantity, String description, Service service)
    {
        if ( (quantity < 0) || (description == null) || (service == null) 
                || (description.isEmpty() ))
            throw new IllegalArgumentException("None of the arguments can be null nor empty.");
        
        this.quantity = quantity;
        this.description = description;
        this.service = service;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
    
    public ProviderPairing getProviderPairing() {
        return this.oProviderPairing;
    }

    @Override
    public String toString() {
        return "RequestedServiceDescription{" + "quantity=" + quantity + ", description=" + description + ", service=" + service + '}';
    }
     
}
