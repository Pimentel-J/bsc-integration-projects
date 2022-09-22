/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import lapr2.project.agpsd.model.AGPSD;
import lapr2.project.agpsd.model.Company;
import lapr2.project.agpsd.model.Customer;
import lapr2.project.agpsd.model.PostalAddress;
import lapr2.project.agpsd.utils.Utils;

/**
 *
 *
 */
public class UC1RegisterCustomerController implements Initializable
{
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    private AGPSD m_oApp;
    private Company m_oEmpresa;
    private Customer m_oCliente;
    private String m_strPwd;
    public UC1RegisterCustomerController()
    {
        this.m_oApp = AGPSD.getInstance();
        this.m_oEmpresa = m_oApp.getCompany();
    }
    
    
    public boolean newCustomer(String strNome, String strNIF, String strTelefone, String strEmail, String strPwd, String strLocal, String strCodPostal, String strLocalidade)
    {
        try
        {
            this.m_strPwd = strPwd;
            PostalAddress morada = Customer.newPostalAddress(strLocal, strCodPostal, strLocalidade);
            this.m_oCliente = this.m_oEmpresa.newCustomer(strNome, strNIF, strTelefone, strEmail, morada);
            return this.m_oEmpresa.validateCustomer(this.m_oCliente, this.m_strPwd);
        }
        catch(RuntimeException ex)
        {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            this.m_oCliente = null;
            return false;
        }
    }
    
    public boolean addEnderecoPostal(String strLocal, String strCodPostal, String strLocalidade)
    {
        if (this.m_oCliente != null)
        {
            try
            {
                PostalAddress morada = Customer.newPostalAddress(strLocal, strCodPostal, strLocalidade);
                return this.m_oCliente.addPostalAddress(morada);
            }
            catch(RuntimeException ex)
            {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } 
        return false;
    }
    
    public boolean registaCliente()
    {
        return this.m_oEmpresa.registerCustomer(this.m_oCliente, this.m_strPwd);
    }

    public String getClienteString()
    {
        return this.m_oCliente.toString();
    }
}
