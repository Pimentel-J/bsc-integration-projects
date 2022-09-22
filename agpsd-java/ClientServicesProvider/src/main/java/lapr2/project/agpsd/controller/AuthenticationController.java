/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import lapr2.project.agpsd.model.AGPSD;
import lapr2.project.userandauth.UserRole;

/**
 *
 * * 
 */
public class AuthenticationController implements Initializable
{
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    private AGPSD m_oApp;
    
    public AuthenticationController()
    {
        this.m_oApp = AGPSD.getInstance();
    }
    
    public boolean doLogin(String strId, String strPwd)
    {
        return this.m_oApp.doLogin(strId, strPwd);
    }
    
    public List<UserRole> getUserRoles()
    {
        if (this.m_oApp.getCurrentSession().isLoggedIn())
        {
            return this.m_oApp.getCurrentSession().getUserRoles();
        }
        return null;
    }

    public void doLogout()
    {
        this.m_oApp.doLogout();
    }
}
