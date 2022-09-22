/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.utils;

import java.util.List;
import lapr2.project.agpsd.model.Company;
import lapr2.project.agpsd.model.ExecutionOrder;
import lapr2.project.agpsd.model.ServiceProvider;

/**
 *
 *
 */
public class StatisticsLibrary implements StatisticsLibraryInterface {
    
    private final Company company;
    
    public StatisticsLibrary(Company company) {
        this.company = company;
    }

    @Override
    public double computeServiceProviderMean(List<ServiceProvider> listSP) {
        double mean = 0;
        for (ServiceProvider sp : listSP) {
            mean = mean + sp.getServiceProviderEvaluation().getAverage();
        }
        return mean/listSP.size();
    }
    
    @Override
    public void computeServiceProviderMean(ServiceProvider sp) {
        double mean = 0;
        List<ExecutionOrder> execOrderList = this.company.getExecutionOrderRegistry().getEvaluatedExecutionOrdersByServiceProvider(sp);
        if (!execOrderList.isEmpty()) {
            for (ExecutionOrder execOrder : execOrderList) {
                mean = mean + execOrder.getServiceRating().getValue();
            }
        }
        sp.getServiceProviderEvaluation().setAverage(mean);
    }
    
    @Override
    public double computeServiceProviderStandardDeviation(List<ServiceProvider> listSP, double populationMean) {
        double standardDeviation = 0;
        for (ServiceProvider sp : listSP) {
            standardDeviation = standardDeviation + Math.pow(Math.abs(sp.getServiceProviderEvaluation().getAverage() - populationMean), 2);
        }
        return Math.sqrt(standardDeviation/listSP.size());
    }

    @Override
    public void computeServiceProviderStandardDeviation(ServiceProvider sp) {
        double standardDeviation = 0;
        List<ExecutionOrder> execOrderList = this.company.getExecutionOrderRegistry().getEvaluatedExecutionOrdersByServiceProvider(sp);
            if (!execOrderList.isEmpty()) {
                for (ExecutionOrder execOrder : execOrderList) {
                    standardDeviation = standardDeviation + Math.pow(Math.abs(execOrder.getServiceRating().getValue() - execOrder.getSp().getServiceProviderEvaluation().getAverage()),2);
                }
            }   
        sp.getServiceProviderEvaluation().setStandardDeviation(Math.sqrt(standardDeviation/execOrderList.size()));
    }

    @Override
    public Object[][] computeAbsoluteDeviationFromPopulationMean(List<ServiceProvider> listSP, double populationMean) {
        Object[][] absoluteDeviation = new Object[listSP.size()][2];
        for (int i = 0; i < absoluteDeviation.length; ++i ) {
            absoluteDeviation[i][0] = listSP.get(i);
            absoluteDeviation[i][1] = listSP.get(i).getServiceProviderEvaluation().getAverage() - populationMean;
        }
        return absoluteDeviation;
    }
    
    @Override
    public double computeAbsoluteDeviationFromPopulationMean(ServiceProvider sp, double populationMean) {
        return sp.getServiceProviderEvaluation().getAverage() - populationMean;
    }
    
    @Override
    public int computerServiceProvidersWithSameRating(int rating, List<ServiceProvider> listSP) {
        int sameRating = 0;
        for (ServiceProvider sp : listSP) {
            if (Math.round(sp.getServiceProviderEvaluation().getAverage()) == rating) {
                ++sameRating;
            }
        }
        return sameRating;
    }
    
}
