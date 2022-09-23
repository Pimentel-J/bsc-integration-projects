package eapli.shopfloor.gestaoproducao.exportacao.application.categoriamaterial;

import eapli.shopfloor.gestaoproducao.domain.CategoriaMaterial;
import eapli.shopfloor.gestaoproducao.repositories.CategoriaMaterialRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Serviço com um template do método para exportar o objeto Categoria Material
 *
 *João Pimentel 
 */
public class CategoriaMaterialExporterService {

    private final CategoriaMaterialRepository repository = PersistenceContext.repositories().categoriasMaterial();

    public Collection<String> exportString() {
        Collection<String> report = new ArrayList<>();
        XmlCategoriaMaterialStringExporter exporter = new XmlCategoriaMaterialStringExporter();
        Iterable<CategoriaMaterial> categorias = repository.findAll();

        if (!categorias.iterator().hasNext()) {
            System.out.println("Não existem materiais");
            System.exit(0);
        }

        report.add(exporter.header());

        for (final CategoriaMaterial categoria : categorias) {
            report.addAll(exporter.element(categoria));
        }

        report.add(exporter.ending());

        return report;
    }
}
