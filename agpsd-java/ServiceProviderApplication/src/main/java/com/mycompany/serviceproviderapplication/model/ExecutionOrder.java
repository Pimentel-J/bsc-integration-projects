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
public class ExecutionOrder {

    public  static enum ServiceState {

        UNFINISHED,
        FINISHED,
        UNEXPECTED;

    };
    
    private int number;
    private String emissionDate;
    private double distanceToClient;
    private ServiceProvider m_oSp;
    private ServiceRating m_oServRat;
    private ServiceState status;
    private ServiceRequest servReq;
    private Service service;
    
    public ExecutionOrder(int number, String emissionDate, ServiceProvider sp, ServiceRating servRat, ServiceRequest servReq) {
        this.number = number;
        this.emissionDate = emissionDate;
        this.m_oSp = sp;
        this.m_oServRat = servRat;
        this.status = ServiceState.UNFINISHED;
        this.servReq = servReq;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }

    public void setEmissionDate(String emissionDate) {
        this.emissionDate = emissionDate;
    }

    public void setSp(ServiceProvider sp) {
        this.m_oSp = sp;
    }

    public int getNumber() {
        return number;
    }

    public String getEmissionDate() {
        return emissionDate;
    }

    public ServiceProvider getSp() {
        return m_oSp;
    }

    public ServiceRating getServiceRating() {
        return this.m_oServRat;
    }

    public ServiceState getServiceState() {
        return this.status;
    }
    
    public ServiceRequest getServiceRequest(){
        return this.servReq;
    }

    public double getDistanceToClient() {
        return distanceToClient;
    }

    public Service getService() {
        return service;
    }
    
    @Override
    public String toString() {
        return String.format(
                "Customer: %s - Distance: %s - Category: %s - Start date: %s - Start time: %s - Service type: %s - Address: [%s]", 
                servReq.getCustomer().getName(),
                distanceToClient,
                service.getCat().getDescription(),
                servReq.getServDescriptionByServId(service.getId()).getProviderPairing().getDate(),
                servReq.getServDescriptionByServId(service.getId()).getProviderPairing().getTime(),
                service.getServiceType().getDesignation(),
                servReq.getPostalAddress().getLocal()
                );
    }
    
}
