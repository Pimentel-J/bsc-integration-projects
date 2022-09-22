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
public enum ServiceProviderLabel {
    
    //inspired by https://www.mkyong.com/java/java-enum-example/
    
    WORST("Worst Providers"),
    REGULAR("Regular Providers"),
    OUTSTANDING("Outstanding Providers"),
    DEFAULT("");
    
    private String label;
    
    ServiceProviderLabel(String label) {
        this.label = label;
    }
    
    public static ServiceProviderLabel fromString(String labelText){
        for (ServiceProviderLabel labelObj : ServiceProviderLabel.values()) {
            if (labelObj.label.equals(labelText)) { 
                return labelObj; 
            }
        }
        return null;
    }
    
    @Override
    public String toString(){
        return this.label;
    }
    
}
