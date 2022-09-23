package eapli.shopfloor.gestaoproducao.exportacao.application.ordemProducao;

import eapli.framework.util.TemplateMethod;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;
import eapli.shopfloor.gestaoproducao.repositories.OrdemProducaoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Serviço com oum template do método para exportar o objeto OrdemProducao
 *
 *Diogo
 */
public class OrdemProducaoExporterService {
    private static final Logger logger = LogManager.getLogger(OrdemProducaoExporterService.class);

    private final OrdemProducaoRepository repository = PersistenceContext.repositories().ordensProducao();

    /**
     * Exportar OrdemsProducao
     * This a "template method" working in conjunction with a Strategy.
     * If the {@link ElementExporter} interface had just the export method we would be repeating the logic of
     * traversing the OrdemProducao list in every implementation!
     *
     * @param OrdensProducao
     * @param filename
     * @param exporter
     * @throws IOException
     */
    @TemplateMethod
    public void export(Iterable<OrdemProducao> OrdensProducao, String filename,
                       ElementExporter<OrdemProducao> exporter) throws IOException {
        try {
            exporter.begin(filename);

            boolean hasPrevious = false;
            for (final OrdemProducao e : OrdensProducao) {
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
        XmlOrdemProducaoStringExporter exporter = new XmlOrdemProducaoStringExporter();
        Iterable<OrdemProducao> ordens = repository.findAll();

        if (!ordens.iterator().hasNext()) {
            System.out.println("Não existem máquinas");
            System.exit(0);
        }

        report.add(exporter.header());

        for (final OrdemProducao ordem : ordens) {
            report.addAll(exporter.element(ordem));
        }

        report.add(exporter.ending());

        return report;
    }
}
