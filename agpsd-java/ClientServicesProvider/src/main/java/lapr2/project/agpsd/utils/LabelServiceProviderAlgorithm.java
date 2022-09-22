/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.utils;

import lapr2.project.agpsd.model.AGPSD;
import lapr2.project.agpsd.model.ServiceProviderLabel;
import lapr2.project.agpsd.model.ServiceProvider;

/**
 *
 *
 */
public class LabelServiceProviderAlgorithm implements LabelServiceProviderAlgorithmInterface {

    @Override
    public ServiceProviderLabel labelServiceProvider(ServiceProvider sp, double populationMean, double populationStandardDeviation) {
        StatisticsLibrary lib = new StatisticsLibrary(AGPSD.getInstance().getCompany());
        double serviceProviderAbsoluteDeviation = lib.computeAbsoluteDeviationFromPopulationMean(sp, populationMean);
        if (serviceProviderAbsoluteDeviation < populationMean - populationStandardDeviation) {
            return ServiceProviderLabel.WORST;
        } else if (serviceProviderAbsoluteDeviation <= populationMean - populationStandardDeviation) {
            return ServiceProviderLabel.REGULAR;
        } else if (serviceProviderAbsoluteDeviation > populationMean - populationStandardDeviation ) {
            return ServiceProviderLabel.OUTSTANDING;
        } else {
            return ServiceProviderLabel.DEFAULT;
        }
    }
    
}
