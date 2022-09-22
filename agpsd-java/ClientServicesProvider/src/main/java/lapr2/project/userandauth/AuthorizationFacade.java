/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.userandauth;

/**
 *
 *
 */
public class AuthorizationFacade
{
    private UserSession m_oSessao = null;
    
    private final UserRoleRegistry m_oPapeis = new UserRoleRegistry();
    private final UserRegistry m_oUtilizadores = new UserRegistry();
    
    public boolean registerUserRole(String strPapel)
    {
        UserRole papel = this.m_oPapeis.newUserRole(strPapel);
        return this.m_oPapeis.addRole(papel);
    }
    
    public boolean registerUserRole(String strPapel, String strDescricao)
    {
        UserRole papel = this.m_oPapeis.newUserRole(strPapel,strDescricao);
        return this.m_oPapeis.addRole(papel);
    }
    
    public boolean registerUser(String strNome, String strEmail, String strPassword)
    {
        User utlz = this.m_oUtilizadores.newUser(strNome,strEmail,strPassword);
        return this.m_oUtilizadores.addUser(utlz);
    }
    
    public boolean registerUserWithRole(String strNome, String strEmail, String strPassword, String strPapel)
    {
        UserRole papel = this.m_oPapeis.getRoleByRoleID(strPapel);
        User utlz = this.m_oUtilizadores.newUser(strNome,strEmail,strPassword);
        utlz.addRole(papel);
        return this.m_oUtilizadores.addUser(utlz);
    }
    
    public boolean registerUserWithRoles(String strNome, String strEmail, String strPassword, String[] papeis)
    {
        User utlz = this.m_oUtilizadores.newUser(strNome,strEmail,strPassword);
        for (String strPapel: papeis)
        {
            UserRole papel = this.m_oPapeis.getRoleByRoleID(strPapel);
            utlz.addRole(papel);
        }
        
        return this.m_oUtilizadores.addUser(utlz);
    }
    
    public boolean userExistsBoolean(String strId)
    {
        return this.m_oUtilizadores.hasUser(strId);
    }
    
    
    public UserSession doLogin(String strId, String strPwd)
    {
        User utlz = this.m_oUtilizadores.getUserByStrID(strId);
        if (utlz != null)
        {
            if (utlz.hasPassword(strPwd)){
                this.m_oSessao = new UserSession(utlz);
            }
        }
        return getCurrentSession();
    }
    
    public UserSession getCurrentSession()
    {
        return this.m_oSessao;
    }
    
    public void doLogout()
    {
        if (this.m_oSessao != null)
            this.m_oSessao.doLogout();
        this.m_oSessao = null;
    }
}
