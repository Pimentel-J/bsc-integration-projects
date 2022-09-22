/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.util.List;
import java.util.TimerTask;
import javafx.util.Pair;

/**
 *
 *
 */
public class PairServiceProvidersTask extends TimerTask {

    private ServiceRequestRegistry m_oRequestReg;
    private ServiceProviderRegistry m_oProviderReg;
    private PairingAlgorithm m_oPairingAlg;

    public PairServiceProvidersTask(ServiceRequestRegistry oRequestReg, 
            ServiceProviderRegistry oProviderReg, PairingAlgorithm oPairingAlg) {
        this.m_oRequestReg = oRequestReg;
        this.m_oProviderReg = oProviderReg;
        this.m_oPairingAlg = oPairingAlg;
    }
    
    @Override
    public void run() {
        attributeProvidersToSubmittedRequestedServices();
    }
    
    private boolean attributeProvidersToSubmittedRequestedServices() {
        
        List<Pair<String, ProviderPairing>> resultList = 
                this.m_oPairingAlg.getProviderPairings(m_oRequestReg, m_oProviderReg);
        
        if (resultList == null || resultList.isEmpty())
            return false;
        
        for (Pair<String, ProviderPairing> pair : resultList) {
            
            String requestNum = getNumRequest(pair);
            String serviceId = getServiceId(pair);
            ProviderPairing providPairing = getProviderPairing(pair);
            
            ServiceRequest servRequest = m_oRequestReg.getServiceRequestByNum(requestNum);
            servRequest.saveProviderPairing(serviceId, providPairing);
            servRequest.checkIfFullyPaired();
        }
        
        return true;
    }
    
    private String getNumRequest(Pair<String, ProviderPairing> pair) {
        return pair.getKey().split(";")[0];
    }
    
    private String getServiceId(Pair<String, ProviderPairing> pair) {
        return pair.getKey().split(";")[1];
    }
    
    private ProviderPairing getProviderPairing(Pair<String, ProviderPairing> pair) {
        return pair.getValue();
    }
    
}
