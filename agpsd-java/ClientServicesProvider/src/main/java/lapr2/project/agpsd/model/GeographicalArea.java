/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class GeographicalArea {
    
    private String m_strDesignation;
    private ZipCode m_oZipCode;
    private double m_dActionRadius;
    private double m_dTransportFee;
    private List<ActsOn> m_lstActsOn;
    
    /**
     *
     * @param strDesignation
     * @param dTransportFee
     * @param strZipCode
     * @param dActionRadius
     * @param oExtServ
     */
    public GeographicalArea(String strDesignation, double dTransportFee, 
            String strZipCode, double dActionRadius, ExternalService oExtServ)
    {
        if ( (strZipCode == null) || (oExtServ == null) ||
                (strZipCode.isEmpty()) )
            throw new IllegalArgumentException("None of the arguments can be null or empty.");
        
        ZipCode oZipCode = new ZipCode(strZipCode);
        List<ActsOn> lstActsOn = oExtServ.getActivity(oZipCode, dActionRadius);
        setData(strDesignation, dTransportFee, oZipCode, dActionRadius, lstActsOn);
    }
    
    /**
     *
     * @return m_strDesignation
     */
    public String getDesignation()
    {
        return this.m_strDesignation;
    }
    
    /**
     *
     * @return m_lstActsOn
     */
    public List<ActsOn> getActsOnList()
    {
        return this.m_lstActsOn;
    }
    
    /**
     *
     * @param o
     * @return true or false
     */
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
        if (getClass() != o.getClass())
            return false;
        // field comparison
        GeographicalArea obj = (GeographicalArea) o;
        return (m_strDesignation.equals(obj.getDesignation()));
    }
    
    @Override
    public String toString()
    {
        return String.format("Name: %s - Zip code: %s - Radius: %.1f km - Fee: %.0f €", 
                m_strDesignation, m_oZipCode.getZipCode(), m_dActionRadius, m_dTransportFee);
    }
    
    /**
     *
     * @param zipCode
     * @return POSITIVE_INFINITY
     */
    public double hasZipCode(String zipCode) {
        for (ActsOn actsOn : m_lstActsOn) {
            if (zipCode.equalsIgnoreCase(actsOn.getZipCode().getZipCode())) {
                return actsOn.getDistance();
            }
        }
        return Double.POSITIVE_INFINITY;
    }
    
    private void setData(String strDesignation, double dTransportFee, ZipCode oZipCode, double dActionRadius, List<ActsOn> lstActsOn) 
    {
        if ( (strDesignation == null) || (oZipCode == null) || (lstActsOn == null) ||
                (dTransportFee <= 0) || (dActionRadius <= 0) ||
                (strDesignation.isEmpty()) || (lstActsOn.isEmpty()) )
            throw new IllegalArgumentException("None of the arguments can be null or empty.");
        
        this.m_strDesignation = strDesignation;
        this.m_dTransportFee = dTransportFee;
        this.m_oZipCode = oZipCode;
        this.m_dActionRadius = dActionRadius;
        this.m_lstActsOn = new ArrayList<>(lstActsOn);
    }
    
}
