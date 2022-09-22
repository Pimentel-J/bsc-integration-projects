/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.utils;

import lapr2.project.agpsd.model.ServiceProviderLabel;
import lapr2.project.agpsd.model.ServiceProvider;

/**
 *
 *
 */
public interface LabelServiceProviderAlgorithmInterface {
    
    public ServiceProviderLabel labelServiceProvider(ServiceProvider sp, double populationMean, double populationStandardDeviation);
    
}
