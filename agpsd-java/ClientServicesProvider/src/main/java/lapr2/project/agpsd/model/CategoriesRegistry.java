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
public class CategoriesRegistry {
/**
 * declaration of the category list
 */    
    private List<Category> m_lstCategories;
/**
 * starts the category registry
 */
    public CategoriesRegistry() 
    {
        this.m_lstCategories = new ArrayList<>();
    }
/**
 * method to obtain the list of categories
 * @return list of categories
 */    
    public List<Category> getCategories() 
    {
        List<Category> lstCategories = new ArrayList<>();
        lstCategories.addAll(m_lstCategories);
        return lstCategories;
    }
 /**
  * Method to obtain a certain category through its ID
  * @param strId related to the category ID
  * @return object of the category type
  */   
    public Category getCategoryById(String strId) 
    {
        for (Category oCat : m_lstCategories) 
        {
            if (oCat.hasId(strId)) 
            {
                return oCat;
            }
        }
        return null;
    }
/**
 * Method to register a category
 * @param oCat object of the category type
 */
    public void registerCategory(Category oCat) 
    {
        this.m_lstCategories.add(oCat);
    }
    
}
