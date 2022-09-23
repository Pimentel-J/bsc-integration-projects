package eapli.shopfloor.gestaoproducao.exportacao.application.material;

import eapli.framework.util.TemplateMethod;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.domain.Material;
import eapli.shopfloor.gestaoproducao.repositories.MaterialRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Serviço com um template do método para exportar o objeto Material
 *
 *João Pimentel 
 */
public class MaterialExporterService {
    private static final Logger logger = LogManager.getLogger(MaterialExporterService.class);

    private final MaterialRepository repository = PersistenceContext.repositories().materiais();

    /**
     * Exportar materiais
     * This a "template method" working in conjunction with a Strategy.
     * If the {@link ElementExporter} interface had just the export method we would be repeating the logic of
     * traversing the list in every implementation!
     *
     * @param materiais
     * @param filename
     * @param exporter
     * @throws IOException
     */
    @TemplateMethod
    public void export(Iterable<Material> materiais, String filename, ElementExporter<Material> exporter) throws IOException {
        try {
            exporter.begin(filename);

            boolean hasPrevious = false;
            for (final Material e : materiais) {
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
        XmlMaterialStringExporter exporter = new XmlMaterialStringExporter();
        Iterable<Material> materiais = repository.findAll();

        if (!materiais.iterator().hasNext()) {
            System.out.println("Não existem materiais");
            System.exit(0);
        }

        report.add(exporter.header());

        for (final Material material : materiais) {
                report.addAll(exporter.element(material));
            }

        report.add(exporter.ending());

        return report;
    }
}
