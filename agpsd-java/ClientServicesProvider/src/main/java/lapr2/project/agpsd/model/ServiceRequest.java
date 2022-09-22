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
public class ServiceRequest { 

   
    
    public  static enum RequestStatus {

        PAIRED, UNPAIRED;

    };
    private int scheduleCounter=0;
    private String requestNumber;
    private String date;
    private double total;
    private RequestStatus reqStatus;
    private PostalAddress pAddress;
    private Customer customer;
    
    private List<RequestedServiceDescription> lstReqServDesc = new ArrayList<RequestedServiceDescription>();
    private List<SchedulePreference> lstSchePref = new ArrayList<SchedulePreference>();
    private List<OtherCost> lstOtherCost;
    
    
    
    public ServiceRequest(String requestNumber, String date, double total,RequestedServiceDescription reqServDesc)
    {
        if ( (requestNumber == null) || (date == null) || (total < 0) ||
                 (reqServDesc == null) ||
                (requestNumber.isEmpty())|| (date.isEmpty()) )
            throw new IllegalArgumentException("None of the arguments can be null nor empty.");
        
        this.requestNumber = requestNumber;
        this.date = date;
        this.total = total;
        this.reqStatus = RequestStatus.UNPAIRED;
        lstReqServDesc.add(reqServDesc);
    }

    public ServiceRequest(String requestNumber, String date, double total, PostalAddress pAddress,RequestedServiceDescription reqServDesc, Customer customer) {
        if ( (requestNumber == null) || (date == null) || (total < 0) ||
                 (reqServDesc == null) ||
                (requestNumber.isEmpty())|| (date.isEmpty()) )
            throw new IllegalArgumentException("None of the arguments can be null nor empty.");
        this.requestNumber = requestNumber;
        this.date = date;
        this.total = total;
        this.pAddress = pAddress;
        this.customer = customer;
    }
    
    public ServiceRequest(String requestNumber, String date, double total, Customer customer, PostalAddress postAddress)
    {
        this.requestNumber = requestNumber;
        this.date = date;
        this.total = total;
        this.customer = customer;
        this.pAddress = postAddress;
        this.reqStatus = RequestStatus.UNPAIRED;
    }

    public ServiceRequest(Customer customer, PostalAddress pAddress) {
        this.pAddress = pAddress;
        this.customer = customer;
    }
    

    public int getScheduleCounter() {
        return scheduleCounter;
    }

    public void setScheduleCounter(int scheduleCounter) {
        this.scheduleCounter = scheduleCounter;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public RequestStatus getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(RequestStatus reqStatus) {
        this.reqStatus = reqStatus;
    }

    public PostalAddress getpAddress() {
        return pAddress;
    }

    public void setpAddress(PostalAddress pAddress) {
        this.pAddress = pAddress;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    
    public boolean addRequestedServiceDescription(RequestedServiceDescription reqServDesc)
    { 
        return this.lstReqServDesc.add(reqServDesc);
    }
    
    
    
     public boolean addSchedulePreference(SchedulePreference schePref)
    { scheduleCounter++;
        return this.lstSchePref.add(schePref);
    }
    
    
    
    public boolean addOtherCost(OtherCost otherCost)
    {
        return this.lstOtherCost.add(otherCost);
    }
    
    
   

 @Override
    public String toString()
    {
        String str = String.format("%s - %s - %s - %s -%s\n Descriptions:",
                this.requestNumber, this.date, this.total, this.reqStatus,this.pAddress.toString());
        for(RequestedServiceDescription reqServDesc:this.lstReqServDesc)
            str += "\nRequested Service :\n" + reqServDesc.toString();
        str += String.format("Additional Costs:");
        for(OtherCost otherCost:this.lstOtherCost)
            str += "\n Other Cost:\n" + otherCost.toString();
        return str;
    }
    
    public boolean saveProviderPairing(String idService, ProviderPairing oProviderPairing) {
        RequestedServiceDescription servDescr = getRequestedServDescrByServId(idService);
        if (servDescr.saveProviderPairing(oProviderPairing)) {
            return servDescr.fillProviderSchedule();
        }
        return false;
    }
    
    private RequestedServiceDescription getRequestedServDescrByServId(String idService) {
        for (RequestedServiceDescription servDescr : lstReqServDesc) {
            if (servDescr.hasServiceWithId(idService)) {
                return servDescr;
            }
        }
        return null;
    }
    
    public boolean checkIfFullyPaired() {
        if (isFullyPaired()) {
            setReqStatus(RequestStatus.PAIRED);
            return true;
        }
        return false;
    }
    
    private boolean isFullyPaired() {
        for (RequestedServiceDescription servDescr : lstReqServDesc) {
            if (servDescr.getProviderPairing() == null) {
                return false;
            }
        }
        return true;
    }
    
    public boolean schedulesAreNoLongerValid() {
        for (SchedulePreference schPref : lstSchePref) {
            if (schPref.dateHasPassed()) {
                return true;
            }
        }
        return false;
    }
    
    public List<SchedulePreference> getSchedules() {
        List<SchedulePreference> lst = new ArrayList<>();
        lst.addAll(lstSchePref);
        return lst;
    }
    
    public List<RequestedServiceDescription> getServiceDescriptions() {
        List<RequestedServiceDescription> lst = new ArrayList<>();
        lst.addAll(lstReqServDesc);
        return lst;
    }
    
    public String showServiceRequest() {
        return String.format("Number: %s - Date: %s", requestNumber, date);
    }
}

