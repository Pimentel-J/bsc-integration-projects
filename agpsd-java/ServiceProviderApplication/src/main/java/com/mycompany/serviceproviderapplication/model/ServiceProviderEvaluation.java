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
public class ServiceProviderEvaluation {

    public ServiceProviderLabel getLabel() {
        return label;
    }

    public double getAverage() {
        return average;
    }

    public void setLabel(ServiceProviderLabel label) {
        this.label = label;
    }

    public void setAverage(double average) {
        this.average = average;
    }
    
    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public ServiceProviderEvaluation() {
        this.label = ServiceProviderLabel.REGULAR;
        this.average = 3; //CHANGE THIS LITERAL TO A CONSTANT!
        this.standardDeviation = 0; //CHANGE THIS LITERAL TO A CONSTANT!
        
    }
    
    private ServiceProviderLabel label;
    private double average;
    private double standardDeviation;
}
