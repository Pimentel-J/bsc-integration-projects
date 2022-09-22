/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

/**
 *
 *
 */
public class ServiceProviderEvaluation {

    /**
     *
     * @return label
     */
    public ServiceProviderLabel getLabel() {
        return label;
    }

    /**
     *
     * @return average
     */
    public double getAverage() {
        return average;
    }

    /**
     *
     * @param label
     */
    public void setLabel(ServiceProviderLabel label) {
        this.label = label;
    }

    /**
     *
     * @param average
     */
    public void setAverage(double average) {
        this.average = average;
    }
    
    /**
     *
     * @param standardDeviation
     */
    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public ServiceProviderEvaluation() {
        this.label = ServiceProviderLabel.REGULAR;
        this.average = 3; //CHANGE THIS LITERAL TO A CONSTANT!
        this.standardDeviation = 0; //CHANGE THIS LITERAL TO A CONSTANT!
        
    }
    
    private ServiceProviderLabel label;
    private double average;
    private double standardDeviation;
}
