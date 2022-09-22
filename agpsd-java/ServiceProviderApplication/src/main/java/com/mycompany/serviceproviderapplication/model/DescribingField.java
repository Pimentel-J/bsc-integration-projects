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
public enum DescribingField {
    
    CUSTOMER_NAME("Customer's name"),
    DISTANCE_TO_CUSTOMER("Distance to customer"),
    SERVICE_CATEGORY("Service category"),
    SERVICE_START_DATE_AND_TIME("Service start date and time"),
    SERVICE_TYPE("Service type"),
    CUSTOMER_ADDRESS("Customer's address");
    
    private final String fieldString;
    
    DescribingField(String fieldString) {
        this.fieldString = fieldString;
    }
    
    @Override
    public String toString() {
        return this.fieldString;
    }
    
}
