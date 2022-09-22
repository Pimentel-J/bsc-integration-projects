/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.model;

/**
 *
 *
 */
public class ProviderPairing {
    
    private String m_strDate;
    private String m_strTime;

    public ProviderPairing(String strDate, String strTime) {
        this.m_strDate = strDate;
        this.m_strTime = strTime;
    }

    public String getDate() {
        return m_strDate;
    }

    public String getTime() {
        return m_strTime;
    }
    
}
