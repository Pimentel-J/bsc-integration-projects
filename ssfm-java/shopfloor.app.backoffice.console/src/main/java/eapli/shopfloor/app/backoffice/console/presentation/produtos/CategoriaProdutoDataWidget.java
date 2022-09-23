/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.app.backoffice.console.presentation.produtos;

import eapli.framework.util.Console;
/**
 *
 *.se.1181483
 */
public class CategoriaProdutoDataWidget {
    
    private String codigoCategoria;
    private String descricaoCategoria;
    
    public void show(){
        this.codigoCategoria = Console.readLine("Código Categoria: ");
        this.descricaoCategoria = Console.readLine("Descrição Categoria: ");
    }
    
    
    public String codigoCategoria(){
        return this.codigoCategoria;
    }
    
    public String descricaoCategoria(){
        return this.descricaoCategoria;
    }
}
