package eapli.shopfloor.gestaoproducao.exportacao.application.chaofabricareport;

import eapli.framework.util.TemplateMethod;
import eapli.shopfloor.general.exportacao.ElementExporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Serviço com um template do método para exportar a informação referente ao chão de fábrica
 *
 *João Pimentel 
 */
public class ChaoFabricaReportExporterService {
    private static final Logger logger = LogManager.getLogger(ChaoFabricaReportExporterService.class);

    /**
     * Exportar materiais
     * This a "template method" working in conjunction with a Strategy.
     * If the {@link ElementExporter} interface had just the export method we would be repeating the logic of
     * traversing the list in every implementation!
     *
     * @param filename
     * @param exporter
     * @throws IOException
     */
    @TemplateMethod
    public void export(String filename, ElementExporter<String> exporter, Iterable<String> allChaoFabricaInfo,
                       Iterable<String> allGestaoProducaoInfo) throws IOException {

        if (!allGestaoProducaoInfo.iterator().hasNext() || !allChaoFabricaInfo.iterator().hasNext()) {
            System.out.println("Dados para exportação incompletos");
            System.exit(0);
        }

        try {
            exporter.begin(filename);

            for (final String cf : allChaoFabricaInfo) {
                exporter.element(cf);
            }
            exporter.elementSeparator();
            for (final String gp : allGestaoProducaoInfo) {
                exporter.element(gp);
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
