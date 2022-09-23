package eapli.shopfloor.app.backoffice.console.presentation.exportacao;

import eapli.framework.visitor.Visitor;
import eapli.shopfloor.general.FileFormats;

/**
 *João Pimentel 
 */
public class ExportFileFormatPrinter implements Visitor<FileFormats>{

    @Override
    public void visit(FileFormats visitee) {
        System.out.printf("%-5s", visitee);
    }
    
}
