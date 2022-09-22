/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.mainpackage;

import com.mycompany.serviceproviderapplication.model.ServiceProvider;

/**
 *
 *
 */
public class SPGPSD {
    
    private final ServiceProvider m_oProvider;

    private static SPGPSD singleton = null;

    private SPGPSD() {
        this.m_oProvider = new ServiceProvider();
        bootstrap();
        setDataOfServiceProvider();
    }

    public static SPGPSD getInstance() {
        if (singleton == null) {
            synchronized (SPGPSD.class) {
                singleton = new SPGPSD();
            }
        }
        return singleton;
    }
    
    public ServiceProvider getServiceProvider() {
        return this.m_oProvider;
    }
    
    private void bootstrap() {
        
    }
    
    private void setDataOfServiceProvider() {
        
    }
    
}
