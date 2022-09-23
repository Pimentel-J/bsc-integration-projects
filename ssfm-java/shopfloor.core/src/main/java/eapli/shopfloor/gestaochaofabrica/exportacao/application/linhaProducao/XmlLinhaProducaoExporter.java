package eapli.shopfloor.gestaochaofabrica.exportacao.application.linhaProducao;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe de implementação da Strategy XML export
 *
 *Diogo
 */
public class XmlLinhaProducaoExporter implements ElementExporter<LinhaProducao> {

    private PrintWriter stream;

    @Override
    public void begin(String filename) throws IOException {
        stream = new PrintWriter(new FileWriter(filename));
        stream.println("<LinhaProducaos>");
    }

    @Override
    public void element(LinhaProducao e) {
        stream.println("<LinhaProducao>");
        stream.printf("<idLinhaProducao>%s</idLinhaProducao>%n", e.identity());
        stream.println("</LinhaProducao>");
    }

    @Override
    public void elementSeparator() {
        // nothing to do
    }

    @Override
    public void end() {
        stream.println("</LinhaProducaos>");
        stream.close();
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            stream.close();
        }
    }
}
