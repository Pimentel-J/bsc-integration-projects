/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.mainpackage.controller;

import com.mycompany.serviceproviderapplication.mainpackage.SPGPSD;
import com.mycompany.serviceproviderapplication.model.DescribingField;
import com.mycompany.serviceproviderapplication.model.ExecutionOrder;
import com.mycompany.serviceproviderapplication.model.ExecutionOrderRegistry;
import com.mycompany.serviceproviderapplication.model.ServiceProvider;
import java.util.List;

/**
 *
 *
 */
public class UC2AnalyzeExecutionOrdersController {
    
    private final ServiceProvider m_oProvider;
    private ExecutionOrderRegistry eor;
    
    public UC2AnalyzeExecutionOrdersController()
    {
        this.m_oProvider = SPGPSD.getInstance().getServiceProvider();
    }
    
    public List<ExecutionOrder> getExecutionOrders() {
        eor = m_oProvider.getExecutionOrderRegistry();
        return eor.getExecutionOrders();
    }
    
    public List<ExecutionOrder> sortExecutionOrders(DescribingField criteria) {
        return eor.sortExecutionOrders(criteria);
    }
    
}
