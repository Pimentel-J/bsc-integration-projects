/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 *
 */
public class ServiceType 
{
    private String m_strDesignation;
    private String m_strServiceClass;

    /**
     *
     * @param strDesignation
     * @param strServiceClass
     */
    public ServiceType(String strDesignation, String strServiceClass) 
    {
        if ( (strDesignation == null) || (strServiceClass == null) ||
                (strDesignation.isEmpty()) || (strServiceClass.isEmpty()) )
            throw new IllegalArgumentException("None of the arguments can be null or empty.");
        
        this.m_strDesignation = strDesignation;
        this.m_strServiceClass = strServiceClass;
    }

    /**
     *
     * @return m_strDesignation
     */
    public String getDesignation() 
    {
        return m_strDesignation;
    }
    
    /**
     *
     * @param strId
     * @param strBriefDesc
     * @param strComplDesc
     * @param dCost
     * @param oCat
     * @return oServ or null
     */
    public Service newService(String strId, String strBriefDesc, String strComplDesc, Double dCost, Category oCat) 
    {
        try {
            Class<?> oClass = Class.forName(m_strServiceClass);
            Class[] argsClasses = new Class[] { String.class, String.class, String.class, 
                double.class, Category.class, ServiceType.class };
            Constructor constructor = oClass.getConstructor(argsClasses);
            Object[] argsValues = new Object[] { strId, strBriefDesc, strComplDesc, dCost, oCat, this };
            Service oServ = (Service) constructor.newInstance(argsValues);
            return oServ;
            
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | 
                IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            return null;
        }    
    }
    
    /**
     *
     * @param strDesignation
     * @return boolean
     */
    public boolean hasDesignation(String strDesignation) 
    {
        return m_strDesignation.equals(strDesignation);
    }
    
    /**
     *
     * @return m_strDesignation as String
     */
    @Override
    public String toString() 
    {
        return String.format("%s", m_strDesignation);
    }
    
}
