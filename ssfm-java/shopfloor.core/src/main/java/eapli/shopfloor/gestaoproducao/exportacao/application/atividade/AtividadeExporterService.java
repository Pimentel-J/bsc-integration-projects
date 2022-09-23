package eapli.shopfloor.gestaoproducao.exportacao.application.atividade;

import eapli.framework.util.TemplateMethod;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.domain.Atividade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Serviço com oum template do método para exportar o objeto Atividade
 *
 *Diogo
 */
public class AtividadeExporterService {
    private static final Logger logger = LogManager.getLogger(AtividadeExporterService.class);

    /**
     * Exportar Atividades
     * This a "template method" working in conjunction with a Strategy.
     * If the {@link ElementExporter} interface had just the export method we would be repeating the logic of
     * traversing the Atividade list in every implementation!
     *
     * @param Atividades
     * @param filename
     * @param exporter
     * @throws IOException
     */
    @TemplateMethod
    public void export(Iterable<Atividade> Atividades, String filename, ElementExporter exporter) throws IOException {
        try {
            exporter.begin(filename);

            boolean hasPrevious = false;
            for (final Atividade e : Atividades) {
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
}
