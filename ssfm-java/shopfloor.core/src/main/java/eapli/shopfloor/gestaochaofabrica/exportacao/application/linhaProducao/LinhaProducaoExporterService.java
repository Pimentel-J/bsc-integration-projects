package eapli.shopfloor.gestaochaofabrica.exportacao.application.linhaProducao;

import eapli.framework.util.TemplateMethod;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.repositories.LinhaProducaoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Serviço com oum template do método para exportar o objeto Linha Produção
 *
 *Diogo
 */
public class LinhaProducaoExporterService {
    private static final Logger logger = LogManager.getLogger(LinhaProducaoExporterService.class);

    private final LinhaProducaoRepository repository = PersistenceContext.repositories().linhasProducao();

    /**
     * Exportar Linhas Producao
     * This a "template method" working in conjunction with a Strategy.
     * If the {@link ElementExporter} interface had just the export method we would be repeating the logic of
     * traversing the LinhaProducao list in every implementation!
     *
     * @param linhasProducao
     * @param filename
     * @param exporter
     * @throws IOException
     */
    @TemplateMethod
    public void export(Iterable<LinhaProducao> linhasProducao, String filename,
                       ElementExporter<LinhaProducao> exporter) throws IOException {
        try {
            exporter.begin(filename);

            boolean hasPrevious = false;
            for (final LinhaProducao e : linhasProducao) {
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
        XmlLinhaProducaoStringExporter exporter = new XmlLinhaProducaoStringExporter();
        Iterable<LinhaProducao> linhas = repository.findAll();

        if (!linhas.iterator().hasNext()) {
            System.out.println("Não existem linhas");
            System.exit(0);
        }

        report.add(exporter.header());

        for (final LinhaProducao linha : linhas) {
            report.addAll(exporter.element(linha));
        }

        report.add(exporter.ending());

        return report;
    }
}
