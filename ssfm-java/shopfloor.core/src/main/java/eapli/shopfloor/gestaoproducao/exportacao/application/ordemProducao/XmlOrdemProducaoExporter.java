package eapli.shopfloor.gestaoproducao.exportacao.application.ordemProducao;

import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe de implementação da Strategy XML export
 *
 *Diogo
 */
public class XmlOrdemProducaoExporter implements ElementExporter<OrdemProducao> {

    private PrintWriter stream;

    @Override
    public void begin(String filename) throws IOException {
        stream = new PrintWriter(new FileWriter(filename));
        stream.println("<OrdemProducao>");
    }

    @Override
    public void element(OrdemProducao e) {
        stream.println("<OrdemProducao>");
        stream.printf("<idOrdem>%s</idOrdem>%n", e.identity().codigo());
        stream.printf("<dataEmissao>%s</dataEmissao>%n", e.dataEmissao().toString());
        stream.printf("<dataPrevistaExec>%s</dataPrevistaExec>%n", e.dataPrevistaExec().toString());
        stream.printf("<codigoFabProduto>%s</codigoFabProduto>%n", e.produto().identity().codigo());
        stream.printf("<quantidade>%s</quantidade>%n", e.quantidade());
        stream.printf("<estadoOrdem>%s</estadoOrdem>%n", e.estado());
        stream.printf("<idLinhaProducao>%s</idLinhaProducao>%n", e.linhaProducao().identity().codigo());
        stream.printf("<idLinhaProducao>%s</idLinhaProducao>%n", e.linhaProducao().identity().codigo());
// to review




        stream.println("</OrdemProducao>");
    }

    @Override
    public void elementSeparator() {
        // nothing to do
    }

    @Override
    public void end() {
        stream.println("</OrdemProducao>");
        stream.close();
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            stream.close();
        }
    }
}
