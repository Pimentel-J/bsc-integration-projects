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
public class ServiceRequestRegistry {
     
    private List<ServiceRequest> lstServReq;

    public ServiceRequestRegistry() {
        this.lstServReq = new ArrayList<>();
    }
   
    public List<ServiceRequest> getServiceRequests() 
    {
        List<ServiceRequest> lstServiceRequest = new ArrayList<>();
        lstServiceRequest.addAll(lstServReq);
        return lstServiceRequest;
    } 
    
    public ServiceRequest newServiceRequest(Customer customer,PostalAddress pAddress) {
        ServiceRequest servReq = new ServiceRequest(customer,pAddress);
        return servReq;
        
    }
    
    public boolean addServiceRequest(ServiceRequest servReq)
    { 
        return this.lstServReq.add(servReq);
    }
     
    public ServiceRequest getServiceRequestByNum(String requestNum) {
        for (ServiceRequest servRequest : lstServReq) {
            if (requestNum.equals(servRequest.getRequestNumber())) {
                return servRequest;
            }
        }
        return null;
    } 
    
    
}
