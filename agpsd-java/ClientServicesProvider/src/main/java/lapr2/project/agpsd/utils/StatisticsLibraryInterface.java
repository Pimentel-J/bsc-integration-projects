/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.utils;

import java.util.List;
import lapr2.project.agpsd.model.ServiceProvider;

/**
 *
 *
 */
public interface StatisticsLibraryInterface {
    
    public double computeServiceProviderMean(List<ServiceProvider> listSP);
    
    public void computeServiceProviderMean(ServiceProvider sp);
        
    public double computeServiceProviderStandardDeviation(List<ServiceProvider> listSP, double populationMean);
 
    public void computeServiceProviderStandardDeviation(ServiceProvider sp);    
    
    public Object[][] computeAbsoluteDeviationFromPopulationMean(List<ServiceProvider> listSP, double populationMean);
    
    public double computeAbsoluteDeviationFromPopulationMean(ServiceProvider sp, double populationMean);
    
    public int computerServiceProvidersWithSameRating(int rating, List<ServiceProvider> listSP);
    
}
