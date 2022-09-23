package eapli.shopfloor.gestaoproducao.exportacao.application.produto;

import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.domain.Produto;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe de implementação da Strategy XML export
 *
 * Diogo
 */
public class XmlProdutoExporter implements ElementExporter<Produto> {

    private PrintWriter stream;

    @Override
    public void begin(String filename) throws IOException {
        stream = new PrintWriter(new FileWriter(filename));
        
    }

    @Override
    public void element(Produto e) {
        stream.println("<produto>");

        stream.printf("<codigoFabrico>%s</codigoFabrico>%n", e.identity().codigo());
        stream.printf("<codigoComercial>%s</codigoComercial>%n", e.codigoComercial().codigo());
        stream.printf("<descricaoBreve>%s</descricaoBreve>%n", e.descricaoBreve().descricaoBreve());
        stream.printf("<descricaoCompleta>%s</descricaoCompleta>%n", e.descricaoCompleta().descricao());
        stream.printf("<unidadeProduto>%s</unidadeProduto>%n", e.unidade());

        stream.println("<categoriaProduto>");
        stream.printf("<codigoCategoria>%s</codigoCategoria>%n", e.categoriaProduto().CodigoCategoria());
        stream.printf("<descricaoBreve>%s</descricaoBreve>%n", e.categoriaProduto().DescricaoCategoria());
        stream.println("</categoriaProduto>");

        stream.println("</produto>");
    }

    @Override
    public void elementSeparator() {
        // nothing to do
    }

    @Override
    public void end() {
        stream.println("</Produtos>");
        stream.close();
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            stream.close();
        }
    }
}
