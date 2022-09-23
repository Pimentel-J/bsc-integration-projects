package eapli.shopfloor.gestaoproducao.exportacao.application.produto;

import eapli.framework.util.TemplateMethod;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.domain.Produto;
import eapli.shopfloor.gestaoproducao.repositories.ProdutoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Serviço com oum template do método para exportar o objeto Produto
 *
 *João Pimentel 
 */
public class ProdutoExporterService {
    private static final Logger logger = LogManager.getLogger(ProdutoExporterService.class);

    private final ProdutoRepository repository = PersistenceContext.repositories().produtos();

    /**
     * Exportar produtos
     * This a "template method" working in conjunction with a Strategy.
     * If the {@link ElementExporter} interface had just the export method we would be repeating the logic of
     * traversing the list in every implementation!
     *
     * @param produtos
     * @param filename
     * @param exporter
     * @throws IOException
     */
    @TemplateMethod
    public void export(Iterable<Produto> produtos, String filename, ElementExporter<Produto> exporter) throws IOException {
        try {
            exporter.begin(filename);

            boolean hasPrevious = false;
            for (final Produto e : produtos) {
                if (hasPrevious) {
                    exporter.elementSeparator();
                }

                exporter.element(e);
                hasPrevious = true;
            }

            exporter.end();
        } catch (final IOException e) {
            logger.error("Um problema ocorreu na exportação do ficheiro", e);
            throw e;
        } finally {
            exporter.cleanup();
        }
    }

    public Collection<String> exportString() {
        Collection<String> report = new ArrayList<>();
        XmlProdutoStringExporter exporter = new XmlProdutoStringExporter();
        Iterable<Produto> produtos = repository.findAll();

        if (!produtos.iterator().hasNext()) {
            System.out.println("Não existem produtos");
            System.exit(0);
        }

        report.add(exporter.header());

        for (final Produto produto : produtos) {
            report.addAll(exporter.element(produto));
        }

        report.add(exporter.ending());

        return report;
    }
}
