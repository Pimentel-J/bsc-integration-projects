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
public class Category
{
    /**
     * String related to the code of the category
     */
    private String m_strCodigo;
    /**
     * String related to the description of the category
     */
    private String m_strDescricao;
            
    /**
     * Constructor with the instance variables
     * @param strCodigo related to the category ID
     * @param strDescricao related to the category description
     */
    public Category(String strCodigo, String strDescricao)
    {
        if ( (strCodigo == null) || (strDescricao == null) ||
                (strCodigo.isEmpty())|| (strDescricao.isEmpty()))
            throw new IllegalArgumentException("None fo the arguments can be null or invalid.");
        
        this.m_strCodigo = strCodigo;
        this.m_strDescricao = strDescricao;
    }
    /**
     * method to verify if there is a certain category ID
     * @param strId related to the category ID
     * @return boolean confirming or not the presence of the mentioned category
     */
    public boolean hasId(String strId)
    {
        return this.m_strCodigo.equalsIgnoreCase(strId);
    }
    /**
     * Method to obtain a category ID
     * @return String with the category ID
     */
    public String getCategoryCode()
    {
        return this.m_strCodigo;
    }
   
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.m_strCodigo);
        return hash;
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
        if (getClass() != o.getClass())
            return false;
        // field comparison
        Category obj = (Category) o;
        return (Objects.equals(m_strCodigo, obj.m_strCodigo));
    }
    /**
     * Overwrite of the toString method
     * @return String with the content of an object of the category type
     */
    @Override
    public String toString()
    {
        return String.format("%s - %s ", this.m_strCodigo, this.m_strDescricao);
    }

}
