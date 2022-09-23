package eapli.shopfloor.gestaoproducao.exportacao.application.movimento;

import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.domain.Movimento;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe de implementação da Strategy XML export
 *
 *Diogo
 */
public class XmlMovimentoExporter implements ElementExporter<Movimento> {

    private PrintWriter stream;

    @Override
    public void begin(String filename) throws IOException {
        stream = new PrintWriter(new FileWriter(filename));
        stream.println("<Movimentos>");
    }

    @Override
    public void element(Movimento e) {
        stream.println("<Movimento>");
        stream.printf("<tipoMovimento>%s</tipoMovimento>%n", e.tipoMovimento());
        stream.printf("<idMaquina>%s</idMaquina>%n", e.maquina().identity().codigo());
        stream.printf("<quantidade>%f</quantidade>%n", e.quantidade().quantidade());
        stream.printf("<codigoDeposito>%s</codigoDeposito>%n", e.deposito().identity().codigo());
        //to review



        stream.println("</Movimento>");
    }

    @Override
    public void elementSeparator() {
        // nothing to do
    }

    @Override
    public void end() {
        stream.println("</Movimentos>");
        stream.close();
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            stream.close();
        }
    }
}
