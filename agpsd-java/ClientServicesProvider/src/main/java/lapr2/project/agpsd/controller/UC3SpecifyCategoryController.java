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
import lapr2.project.agpsd.model.Category;
import lapr2.project.agpsd.model.Company;
import lapr2.project.agpsd.utils.Constants;
import lapr2.project.agpsd.utils.Utils;

/**
 *
 *
 */
public class UC3SpecifyCategoryController implements Initializable
{
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
            
    private Company m_oEmpresa;
    private Category m_oCategoria;
    public UC3SpecifyCategoryController()
    {
        if(!AGPSD.getInstance().getCurrentSession().isLoggedInWithRole(Constants.ROLE_ADMINISTRATOR))
            throw new IllegalStateException("Utilizador não Autorizado");
        this.m_oEmpresa = AGPSD.getInstance().getCompany();
    }
    
    
    public boolean novaCategoria(String strCodigo, String strDescricao)
    {
        try
        {
            this.m_oCategoria = this.m_oEmpresa.newCategory(strCodigo, strDescricao);
            return this.m_oEmpresa.validateCategory(this.m_oCategoria);
        }
        catch(RuntimeException ex)
        {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            this.m_oCategoria = null;
            return false;
        }
    }
   
    
    public boolean registaCategoria()
    {
        //return this.m_oEmpresa.registerCategory(this.m_oCategoria);
        return false;
    }

    public String getCategoriaString()
    {
        return this.m_oCategoria.toString();
    }
}
