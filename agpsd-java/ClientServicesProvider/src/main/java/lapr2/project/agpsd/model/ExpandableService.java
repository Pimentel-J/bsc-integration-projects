/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.util.Objects;

/**
 *
 *
 */
public class ExpandableService implements Service
{
    /**
     * String related to the service ID
     */
    private String m_strId;
    /**
     * String related to the brief description of the service
     */
    private String m_strBriefDescription;
    /**
     * String related to the complete description of the service
     */
    private String m_strCompleteDescription;
    /**
     * double related to the hourly fee of the service
     */
    private double m_dHourlyFee;
    /**
     * Object of the Category type related to the concerned servi e
     */
    private Category m_oCategory;
    /**
     * Object of the Servicetype type related to the Service type
     */
    private ServiceType m_oServType;
/**
 * Constructor with the instance variables
 * @param strId String related to the service's ID
 * @param strBriefDescription String related to the service's brief description
 * @param strCompleteDescription String related to the service's complete description
 * @param dHourlyFee double related to the service's hourly fee
 * @param oCategory object of the Category type related to the category associated to the service
 * @param oServType object of the ServiceType type related to the service type
 */
    public ExpandableService(String strId, String strBriefDescription, String strCompleteDescription, 
            double dHourlyFee, Category oCategory, ServiceType oServType) 
    {
        if ( (strId == null) || (strBriefDescription == null) || (strCompleteDescription == null) ||
                (dHourlyFee < 0) || (oCategory == null) || (oServType == null) ||
                (strId.isEmpty()) || (strBriefDescription.isEmpty()) || (strCompleteDescription.isEmpty()))
            throw new IllegalArgumentException("None of the arguments can be null or empty.");
        
        this.m_strId = strId;
        this.m_strBriefDescription = strBriefDescription;
        this.m_strCompleteDescription = strCompleteDescription;
        this.m_dHourlyFee = dHourlyFee;
        this.m_oCategory = oCategory;
        this.m_oServType = oServType;
    }
    
    @Override
    public boolean hasOtherAttributes() 
    {
        return false;
    }
    
    @Override
    public String getOtherAttributes() 
    {
        throw new UnsupportedOperationException("Not supported in this class.");
    }
    
    @Override
    public boolean setAdditionalData(double dData) 
    {
        throw new UnsupportedOperationException("Not supported in this class.");
    }
    
    @Override
    public boolean validate() 
    {
        //insert validation code here
        
        //
        return true;
    }
    
    @Override
    public boolean hasId(String strId)
    {
        return this.m_strId.equalsIgnoreCase(strId);
    }
    
    @Override
    public boolean equals(Object o) {
        // Inspirado em https://www.sitepoint.com/implement-javas-equals-method-correctly/
        
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (!(o instanceof Service))
            return false;
        // field comparison
        Service obj = (Service) o;
        return (obj.hasId(m_strId));
    }
   /**
    * Overwrite of the toString method
    * @return String with the content of an object of the ExpandableService type
    */ 
    @Override
    public String toString()
    {
        return String.format("ID: %s - Descr: %s - Cost/hour: %.1f € - Type: %s - Cat: [%s]", 
                m_strId, m_strBriefDescription, m_dHourlyFee, m_oServType.getDesignation(), m_oCategory);
    }
    //GETTERS
    
    @Override
    public Category getCategory() {
        return this.m_oCategory;
    }
    
    @Override
    public String getId() {
        return this.m_strId;
    }
    
}
