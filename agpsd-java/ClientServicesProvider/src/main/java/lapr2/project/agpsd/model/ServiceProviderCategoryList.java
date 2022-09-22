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
public class ServiceProviderCategoryList {
    private List<Category> m_oCatList;
    
    public ServiceProviderCategoryList(){
        this.m_oCatList = new ArrayList<>();
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
    
    public List<Category> getCategories() {
        List<Category> lst = new ArrayList<>();
        lst.addAll(m_oCatList);
        return lst;
    }
}
