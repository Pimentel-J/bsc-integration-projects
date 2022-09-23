package eapli.shopfloor.gestaoproducao.exportacao.application.atividade;

import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.domain.Atividade;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe de implementação da Strategy XML export
 *
 *Diogo
 */
public class XmlAtividadeExporter implements ElementExporter<Atividade> {

    private PrintWriter stream;

    @Override
    public void begin(String filename) throws IOException {
        stream = new PrintWriter(new FileWriter(filename));
        stream.println("<Atividades>");
    }

    @Override
    public void element(Atividade e) {
        stream.println("<Atividade>");

        stream.printf("<idMaquina>%s</idMaquina>%n", e.maquina().identity().codigo());
        stream.printf("<timeStampFim>%s</timeStampFim>%n", e.timeStampFim().toString());
        stream.printf("<timeStampFim>%s</timeStampFim>%n", e.timeStampFim().toString());
        stream.println("</Atividade>");
    }

    @Override
    public void elementSeparator() {
        // nothing to do
    }

    @Override
    public void end() {
        stream.println("</Atividades>");
        stream.close();
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            stream.close();
        }
    }
}
