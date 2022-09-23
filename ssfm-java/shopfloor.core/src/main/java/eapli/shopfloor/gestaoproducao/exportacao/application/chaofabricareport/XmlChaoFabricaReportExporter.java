package eapli.shopfloor.gestaoproducao.exportacao.application.chaofabricareport;

import eapli.shopfloor.general.exportacao.ElementExporter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe de implementação da Strategy XML export
 *
 *João Pimentel 
 */
public class XmlChaoFabricaReportExporter implements ElementExporter<String> {

    private PrintWriter stream;

    @Override
    public void begin(String filename) throws IOException {
        stream = new PrintWriter(new FileWriter(filename));
        stream.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        stream.println("<ShopFloor_Report xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xsi:noNamespaceSchemaLocation=\"xsd/main.xsd\">");
        stream.println("<GestaoChaoFabrica>");
    }

    @Override
    public void element(String e) {
        stream.printf(e);
    }

    @Override
    public void elementSeparator() {
        stream.println("</GestaoChaoFabrica>");
        stream.println("<GestaoProducao>");
    }

    @Override
    public void end() {
        stream.println("</GestaoProducao>");
        stream.println("</ShopFloor_Report>");
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            stream.close();
        }
    }
}
