/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.importacao.application;

import eapli.framework.application.UseCaseController;
import java.io.IOException;

/**
 *
 *.se.1181483
 */
@UseCaseController
public class ImportarProdutosController {
    
    private final ImportadorProdutosFactory factory = new ImportadorProdutosFactory();
    private final ImportadorProdutosService impService = new ImportadorProdutosService();
    private final ListFormatosImportacaoService formatosService = new ListFormatosImportacaoService();
    
    public void importar(String nomeFicheiro, ImportFileFormat formato, int opcao) throws IOException {
        final ImportadorProdutos importador = factory.build(formato);
        
        impService.importar(nomeFicheiro, importador, opcao);
    }

    public Iterable<ImportFileFormat> listarFormatos() {
        return this.formatosService.allFormatos();
    }
    
    
}
