/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.model;

import java.util.Objects;

/**
 *
 *
 */
public class Category
{
    private String m_strCodigo;
    private String m_strDescricao;
            
    
    public Category(String strCodigo, String strDescricao)
    {
        if ( (strCodigo == null) || (strDescricao == null) ||
                (strCodigo.isEmpty())|| (strDescricao.isEmpty()))
            throw new IllegalArgumentException("Nenhum dos argumentos pode ser nulo ou vazio.");
        
        this.m_strCodigo = strCodigo;
        this.m_strDescricao = strDescricao;
    }
    
    public boolean hasId(String strId)
    {
        return this.m_strCodigo.equalsIgnoreCase(strId);
    }
    
    public String getCategoryCode()
    {
        return this.m_strCodigo;
    }

    public String getDescription() 
    {
        return m_strDescricao;
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
    
    @Override
    public String toString()
    {
        return String.format("%s - %s ", this.m_strCodigo, this.m_strDescricao);
    }

}
