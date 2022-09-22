/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.userandauth;

import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 */
public class UserRegistry
{
    private Set<User> m_lstUtilizadores = new HashSet<User>();
    
    
    public User newUser(String strNome, String strEmail, String strPassword)
    {
        return new User(strNome,strEmail,strPassword);
    }
    
    public boolean addUser(User utlz)
    {
        if (utlz != null)
            return this.m_lstUtilizadores.add(utlz);
        return false;
    }
    
    public boolean removeUser(User utlz)
    {
        if (utlz != null)
            return this.m_lstUtilizadores.remove(utlz);
        return false;
    }
    
    public User getUserByStrID(String strId)
    {
        for(User utlz: this.m_lstUtilizadores)
        {
            if(utlz.hasId(strId))
                return utlz;
        }
        return null;
    }
    
    public boolean hasUser(String strId)
    {
        User utlz = getUserByStrID(strId);
        if (utlz != null)
            return this.m_lstUtilizadores.contains(utlz);
        return false;
    }
    
    public boolean hasUser(User utlz)
    {
        return this.m_lstUtilizadores.contains(utlz);
    }
}
