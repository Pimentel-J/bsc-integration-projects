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
public class ActsOn {
 /**
  * Object related to the Zip Code
  */   
    private ZipCode m_oZipCode;
    /**
     * double value related to the distance
     */
    private double m_dDistance;
//CONSTRUCTORS
    /**
     * Constructor with instance variables
     * @param oZipCode
     * @param dDistance 
     */
    public ActsOn(ZipCode oZipCode, double dDistance) 
    {
        if ( (oZipCode == null) || (dDistance <= 0) )
            throw new IllegalArgumentException("None of the arguments can be null or empty.");
        
        this.m_oZipCode = oZipCode;
        this.m_dDistance = dDistance;
    }
//GETTERS
    public ZipCode getZipCode() {
        return m_oZipCode;
    }

    public double getDistance() {
        return m_dDistance;
    }
 /**
  * toString Method Overwrite 
  * @return string with the content of the object
  */   
    @Override
    public String toString() {
        return String.format("ZC: %s - Km to base: %.1f km", m_oZipCode.getZipCode(), m_dDistance);
    }
    
}
