/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.app.backoffice.console.presentation.produtos;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import eapli.shopfloor.gestaoproducao.application.AdicionarProdutoController;
import eapli.shopfloor.gestaoproducao.domain.Unidade;


/**
 *
 *.se.1181483
 */
public class AdicionarProdutoUI extends AbstractUI{

    private final AdicionarProdutoController controller = new AdicionarProdutoController();
    
    @Override
    protected boolean doShow() {
            
            final String codigoFabrico = Console.readLine("Código Fabrico:");
            final String codigoComercial = Console.readLine("Código Comercial:");
            final String descricaoBreve = Console.readLine("Descrição Breve:");
            final String descricaoCompleta = Console.readLine("Descrição Completa:");
            
            final Iterable<Unidade> unidades = this.controller.listarUnidades();
            final SelectWidget<Unidade> selectorUnidades = new SelectWidget<>("Unidades:", unidades,
                new UnidadePrinter());
            selectorUnidades.show();
            final Unidade unidadeRef = selectorUnidades.selectedElement();
            
            final CategoriaProdutoDataWidget categoriaProduto = new CategoriaProdutoDataWidget();
            
            categoriaProduto.show();
            
        try{
            controller.adicionarProduto(codigoFabrico, codigoComercial, descricaoBreve,
                    descricaoCompleta, unidadeRef.toString(), categoriaProduto.codigoCategoria()
                    , categoriaProduto.descricaoCategoria());
            System.out.println("Produto corretamente adicionado.");
            
        }catch (@SuppressWarnings("unused") final IntegrityViolationException ex) {
            System.out.println("Já existe um produto com esse código/descrição.");
    }
        return false;
    }
    
    @Override
    public String headline() {
        return "Adicionar Produto";
    }
    
    
}
