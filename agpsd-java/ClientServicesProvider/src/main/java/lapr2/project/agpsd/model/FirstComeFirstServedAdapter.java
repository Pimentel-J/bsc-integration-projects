/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 *
 */
public class FirstComeFirstServedAdapter implements PairingAlgorithm {
    
    private FirstComeFirstServed algorithm;

    public FirstComeFirstServedAdapter() {
        this.algorithm = new FirstComeFirstServed();
    }

    @Override
    public List<Pair<String, ProviderPairing>> getProviderPairings(ServiceRequestRegistry oRequestReg, ServiceProviderRegistry oProviderReg) {
        
        System.out.println("\n\n\n ### List of Service Requests ### \n");
        for (ServiceRequest request : oRequestReg.getServiceRequests()) {
            System.out.println(request.showServiceRequest());
        }
        
        List<ServiceRequest> lstRequests = new ArrayList<>();
        List<ServiceProvider> lstProviders = oProviderReg.getServiceProviderList();
        
        for (ServiceRequest request : oRequestReg.getServiceRequests()) {
            if (request.getReqStatus() == ServiceRequest.RequestStatus.UNPAIRED &&
                    checkIfRequestSchedulesAreValid(request)) {
                lstRequests.add(request);
            }
        }
        
        
        System.out.println("\n\n ### List of Unpaired & Valid Service Requests ### \n");
        for (ServiceRequest request : lstRequests) {
            System.out.println(request.showServiceRequest());
        }
        
        System.out.println("\n\n ### List of Service Providers ### \n");
        for (ServiceProvider prov : lstProviders) {
            System.out.println(prov);
        }
        
        System.out.println("\n\n ### Results of the Provider Pairing ### \n");
        List<String> result = this.algorithm.getProviderPairings(lstRequests, lstProviders);
        if (result == null || result.isEmpty())
            return null;
        
        List<Pair<String, ProviderPairing>> processedResult = new ArrayList<>();
        for (String str : result) {
            String[] resultInfo = str.split(";");
            System.out.printf("RequestNumber: %s - ServiceId: %s - ProviderNumber: %s - Proposed date: %s - Proposed time: %s \n",
                    resultInfo[0], resultInfo[1], resultInfo[2], resultInfo[3], resultInfo[4]);
            if (resultInfo.length == 5) {
                String first = resultInfo[0] + ";" + resultInfo[1];
                ProviderPairing second = new ProviderPairing(resultInfo[3], 
                        resultInfo[4], oProviderReg.getServiceProviderByID(resultInfo[2]));
                
                processedResult.add(new Pair<>(first, second));
            }
        }
        
        return processedResult;
    }
    
    private boolean checkIfRequestSchedulesAreValid(ServiceRequest request) {
        return !(request.schedulesAreNoLongerValid());
    }
    
}
