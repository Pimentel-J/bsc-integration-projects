/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.model;

import java.util.List;

/**
 *
 *
 */
public class ServiceProviderCategoryList {
    private List<Category> m_oCatList;
    
    public ServiceProviderCategoryList(){
        
    }
    
    public void addCategoryList(List<Category> catList) {
        this.m_oCatList = catList;
    }
    
    public void addCategory(Category cat) {
        boolean validar = validate(cat);
        if (validar) {
            m_oCatList.add(cat);
        }
    }
    
    public boolean validate(Category cat){
        return true;
    }
}
