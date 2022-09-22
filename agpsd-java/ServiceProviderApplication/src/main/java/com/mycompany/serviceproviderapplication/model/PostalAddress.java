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
public class PostalAddress
{
    private String m_strLocal;
    private ZipCode m_oZipCode;
    private String m_strLocalidade;
    
            
    
    public PostalAddress(String strLocal , String strLocalidade)
    {
        if ( (strLocal == null) ||  (strLocalidade == null) ||
                (strLocal.isEmpty())|| (strLocalidade.isEmpty()))
            throw new IllegalArgumentException("Nenhum dos argumentos pode ser nulo ou vazio.");
        
        this.m_strLocal = strLocal;
        this.m_strLocalidade = strLocalidade;
        this.m_oZipCode = new ZipCode();
    }
    
    public PostalAddress(String strLocal , String zipCode, String strLocalidade)
    {
        if ( (strLocal == null) ||  (strLocalidade == null) ||
                (strLocal.isEmpty())|| (strLocalidade.isEmpty()))
            throw new IllegalArgumentException("Nenhum dos argumentos pode ser nulo ou vazio.");
        
        this.m_strLocal = strLocal;
        this.m_strLocalidade = strLocalidade;
        this.m_oZipCode = new ZipCode(zipCode);
    }

    public String getLocal() {
        return m_strLocal;
    }
    
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 23 * hash + Objects.hash(this.m_strLocal, this.m_strLocalidade);
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
        PostalAddress obj = (PostalAddress) o;
        return (Objects.equals(m_strLocal, obj.m_strLocal) && 
                Objects.equals(m_strLocalidade, obj.m_strLocalidade));
    }
    
    @Override
    public String toString()
    {
        return String.format("%s \n %s - %s", this.m_strLocal, this.m_strLocalidade);
    }
    
}
