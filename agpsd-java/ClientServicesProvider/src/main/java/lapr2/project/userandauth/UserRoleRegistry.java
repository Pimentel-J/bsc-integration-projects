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
public class UserRoleRegistry
{
    private Set<UserRole> m_lstPapeis = new HashSet<UserRole>();
    
    public UserRole newUserRole(String strPapel)
    {
        return new UserRole(strPapel);
    }
    
    public UserRole newUserRole(String strPapel, String strDescricao)
    {
        return new UserRole(strPapel,strDescricao);
    }
    
    public boolean addRole(UserRole papel)
    {
        if (papel != null)
            return this.m_lstPapeis.add(papel);
        return false;
    }
    
    public boolean removeRole(UserRole papel)
    {
        if (papel != null)
            return this.m_lstPapeis.remove(papel);
        return false;
    }
    
    public UserRole getRoleByRoleID(String strPapel)
    {
        for(UserRole p: this.m_lstPapeis)
        {
            if(p.hasId(strPapel))
                return p;
        }
        return null;
    }
    
    public boolean hasRole(String strPapel)
    {
        UserRole papel = getRoleByRoleID(strPapel);
        if (papel != null)
            return this.m_lstPapeis.contains(papel);
        return false;
    }
    
    public boolean hasRole(UserRole papel)
    {
        return this.m_lstPapeis.contains(papel);
    }
}
