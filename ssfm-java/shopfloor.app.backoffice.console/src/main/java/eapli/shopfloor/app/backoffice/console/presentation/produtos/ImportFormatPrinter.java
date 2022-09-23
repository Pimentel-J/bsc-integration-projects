/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.app.backoffice.console.presentation.produtos;

import eapli.framework.visitor.Visitor;
import eapli.shopfloor.gestaoproducao.importacao.application.ImportFileFormat;

/**
 *
 *.se.1181483
 */
public class ImportFormatPrinter implements Visitor<ImportFileFormat>{

    @Override
    public void visit(ImportFileFormat visitee) {
        System.out.printf("%-5s", visitee.toString());
    }
    
}
