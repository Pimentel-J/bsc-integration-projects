package eapli.shopfloor.gestaochaofabrica.exportacao.application.maquina;

import eapli.framework.util.TemplateMethod;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import eapli.shopfloor.gestaochaofabrica.repositories.MaquinaRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Serviço com oum template do método para exportar o objeto Maquina
 *
 *Diogo
 */
public class MaquinaExporterService {
    private static final Logger logger = LogManager.getLogger(MaquinaExporterService.class);

    private final MaquinaRepository repository = PersistenceContext.repositories().maquinas();

    /**
     * Exportar maquinas
     * This a "template method" working in conjunction with a Strategy.
     * If the {@link ElementExporter} interface had just the export method we would be repeating the logic of
     * traversing the Maquina list in every implementation!
     *
     * @param maquinas
     * @param filename
     * @param exporter
     * @throws IOException
     */
    @TemplateMethod
    public void export(Iterable<Maquina> maquinas, String filename, ElementExporter<Maquina> exporter) throws IOException {
        try {
            exporter.begin(filename);

            boolean hasPrevious = false;
            for (final Maquina e : maquinas) {
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
        XmlMaquinaStringExporter exporter = new XmlMaquinaStringExporter();
        Iterable<Maquina> maquinas = repository.findAll();

        if (!maquinas.iterator().hasNext()) {
            System.out.println("Não existem máquinas");
            System.exit(0);
        }

        report.add(exporter.header());

        for (final Maquina maquina : maquinas) {
            report.addAll(exporter.element(maquina));
        }

        report.add(exporter.ending());

        return report;
    }
}
