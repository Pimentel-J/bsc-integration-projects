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
public class ServiceRequest {

    private Customer customer;
    private String date;
    private PostalAddress postalAddress;
    private List<RequestedServiceDescription> lstServDesc;

    public ServiceRequest() {
    }
    
    public String getDate() {
        return date;
    }
    
    public Customer getCustomer() {
        return this.customer;
    }

    public PostalAddress getPostalAddress() {
        return postalAddress;
    }
    
    public RequestedServiceDescription getServDescriptionByServId(String servId) {
        for (RequestedServiceDescription servDesc : lstServDesc) {
            if (servDesc.getService().hasId(servId)) {
                return servDesc;
            }
        }
        return null;
    }
    
}
