package eapli.shopfloor.gestaoproducao.exportacao.application.movimento;

import eapli.framework.util.TemplateMethod;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.domain.Movimento;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Serviço com oum template do método para exportar o objeto Movimento
 *
 *Diogo
 */
public class MovimentoExporterService {
    private static final Logger logger = LogManager.getLogger(MovimentoExporterService.class);

    /**
     * Exportar Movimentos
     * This a "template method" working in conjunction with a Strategy.
     * If the {@link ElementExporter} interface had just the export method we would be repeating the logic of
     * traversing the Movimento list in every implementation!
     *
     * @param Movimentos
     * @param filename
     * @param exporter
     * @throws IOException
     */
    @TemplateMethod
    public void export(Iterable<Movimento> Movimentos, String filename, ElementExporter exporter) throws IOException {
        try {
            exporter.begin(filename);

            boolean hasPrevious = false;
            for (final Movimento e : Movimentos) {
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
