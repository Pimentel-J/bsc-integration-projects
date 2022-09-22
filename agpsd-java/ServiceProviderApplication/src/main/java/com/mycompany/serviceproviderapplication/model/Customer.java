 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 *
 */
public class Customer
{
    private String m_strNome;
    private String m_strNIF;
    private String m_strTelefone;
    private String m_strEmail;
    private List<PostalAddress> m_lstMoradas = new ArrayList<PostalAddress>();
            
    
    public Customer(String strNome, String strNIF, String strTelefone, String strEmail, PostalAddress oMorada)
    {
        if ( (strNome == null) || (strNIF == null) || (strTelefone == null) ||
                (strEmail == null) || (oMorada == null) ||
                (strNome.isEmpty())|| (strNIF.isEmpty()) || (strTelefone.isEmpty()) || 
                (strEmail.isEmpty()))
            throw new IllegalArgumentException("Nenhum dos argumentos pode ser nulo ou vazio.");
        
        this.m_strNome = strNome;
        this.m_strEmail = strEmail;
        this.m_strNIF = strNIF;
        this.m_strTelefone = strTelefone;
        m_lstMoradas.add(oMorada);
    }
    
    public String getName()
    {
        return this.m_strNome;
    }
    
    public String getEmail()
    {
        return this.m_strEmail;
    }
    
    public boolean hasEmail(String strEmail)
    {
        return this.m_strEmail.equalsIgnoreCase(strEmail);
    }
   
    public boolean addPostalAddress(PostalAddress oMorada)
    {
        return this.m_lstMoradas.add(oMorada);
    }
    
    public boolean removePostalAddress(PostalAddress oMorada)
    {
        return this.m_lstMoradas.remove(oMorada);
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.m_strEmail);
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
        Customer obj = (Customer) o;
        return (Objects.equals(m_strEmail, obj.m_strEmail) || Objects.equals(m_strNIF, obj.m_strNIF));
    }
    
    @Override
    public String toString()
    {
        String str = String.format("%s - %s - %s - %s", this.m_strNome, this.m_strNIF, this.m_strTelefone, this.m_strEmail);
        for(PostalAddress morada:this.m_lstMoradas)
            str += "\nMorada:\n" + morada.toString();
        return str;
    }
    
    public static PostalAddress novoEnderecoPostal(String strLocal, String strCodPostal, String strLocalidade)
    {
        return new PostalAddress(strLocal,strCodPostal,strLocalidade);
    }

    
}
