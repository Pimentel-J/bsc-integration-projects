package eapli.shopfloor.gestaochaofabrica.exportacao.application.maquina;

import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe de implementação da Strategy XML export
 *
 *Diogo
 */
public class XmlMaquinaExporter implements ElementExporter<Maquina> {

    private PrintWriter stream;

    @Override
    public void begin(String filename) throws IOException {
        stream = new PrintWriter(new FileWriter(filename));
        stream.println("<Maquinas>");
    }

    @Override
    public void element(Maquina e) {
        stream.println("<Maquina>");
        stream.printf("<idMaquina>%s</idMaquina>%n", e.identity());
        stream.printf("<numSerie>%s</numSerie>%n", e.numSerie());
        stream.printf("<descricao>%s</descricao>%n", e.descricaoMaquina());
        stream.printf("<dataInstalacao>%s</dataInstalacao>%n", e.dataInstalacaoMaquina());
        stream.printf("<marca>%s</marca>%n", e.marcaMaquina());
        stream.printf("<modelo>%s</modelo>%n", e.modeloMaquina());
        stream.printf("<idProtocolo>%s</idProtocolo>%n", e.idProtocolo());
        stream.printf("<sequencia>%s</sequencia>%n", e.sequenciaMaquina());
        stream.printf("<linhaProducao>%s</linhaProducao>%n", e.linhaProducaoMaquina().identity());

        stream.println("</Maquina>");
    }

    @Override
    public void elementSeparator() {
        // nothing to do
    }

    @Override
    public void end() {
        stream.println("</Maquinas>");
        stream.close();
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            stream.close();
        }
    }
}
