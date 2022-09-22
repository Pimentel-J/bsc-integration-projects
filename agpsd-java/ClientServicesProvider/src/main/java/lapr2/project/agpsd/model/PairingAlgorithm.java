/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.util.List;
import javafx.util.Pair;

/**
 *
 *
 */
public interface PairingAlgorithm {
    
    List<Pair<String, ProviderPairing>> getProviderPairings(
            ServiceRequestRegistry oRequestReg, ServiceProviderRegistry oProviderReg);
    
}
