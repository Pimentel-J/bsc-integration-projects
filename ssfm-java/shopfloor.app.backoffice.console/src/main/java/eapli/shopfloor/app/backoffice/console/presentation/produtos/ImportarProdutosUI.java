/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.app.backoffice.console.presentation.produtos;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import eapli.shopfloor.gestaoproducao.importacao.application.ImportFileFormat;
import eapli.shopfloor.gestaoproducao.importacao.application.ImportarProdutosController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *.se.1181483
 */
public class ImportarProdutosUI extends AbstractUI {
    
    private final ImportarProdutosController controller = new ImportarProdutosController();

    @Override
    protected boolean doShow() {

        
        final Iterable<ImportFileFormat> formatos = this.controller.listarFormatos();
        final SelectWidget<ImportFileFormat> selectorFormatos = new SelectWidget<>("Formatos de importação:", formatos, new ImportFormatPrinter());
        selectorFormatos.show();
        final String formatoFicheiro = selectorFormatos.selectedElement().toString();
        
        final String nomeFicheiro = Console.readLine("Insira o nome do ficheiro:");
        final int opcao = Console.readInteger("Pretende: (1)Alterar os produtos existentes    (2)Manter os registos já existentes e inserir apenas novos");
        
        try {
            controller.importar(nomeFicheiro, ImportFileFormat.valueOf(formatoFicheiro), opcao);
        } catch (IOException ex) {
            Logger.getLogger(ImportarProdutosUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public String headline() {
        return "Importar Catálogo de Produtos";
    }

}
