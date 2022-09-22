/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.userandauth;

import java.util.List;

/**
 *
 *
 */
public class UserSession
{
    private User m_oUtilizador = null;
    
    private UserSession()
    {
    }
    
    public UserSession(User oUtilizador)
    {
        if (oUtilizador == null)
            throw new IllegalArgumentException("Argumento não pode ser nulo.");
        this.m_oUtilizador = oUtilizador;
    }
    
    public void doLogout()
    {
        this.m_oUtilizador = null;
    }
    
    public boolean isLoggedIn()
    {
        return this.m_oUtilizador != null;
    }
    
    public boolean isLoggedInWithRole(String strPapel)
    {
        if (isLoggedIn())
        {
            return this.m_oUtilizador.hasRole(strPapel);
        }
        return false;
    }
    
    public String getUserName()
    {
        return (isLoggedIn()) ? this.m_oUtilizador.getNome() : null;
    }
    
    public String getUserID()
    {
        return (isLoggedIn()) ? this.m_oUtilizador.getId() : null;
    }
    
    public String getUserEmail()
    {
        return (isLoggedIn()) ? this.m_oUtilizador.getEmail() : null;
    }
    
    public List<UserRole> getUserRoles()
    {
        return this.m_oUtilizador.getRoles();
    }
}
