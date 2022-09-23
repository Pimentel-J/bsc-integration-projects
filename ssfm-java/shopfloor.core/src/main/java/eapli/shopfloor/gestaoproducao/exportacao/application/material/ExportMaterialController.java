package eapli.shopfloor.gestaoproducao.exportacao.application.material;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.application.ListMateriaisService;
import eapli.shopfloor.gestaoproducao.domain.Material;

import java.io.IOException;

/**
 *João Pimentel 
 */
public class ExportMaterialController {

    private final ListMateriaisService listSvc = new ListMateriaisService();
    private final MaterialExporterFactory factory = new MaterialExporterFactory();
    private final MaterialExporterService exportSvc = new MaterialExporterService();

    public void export(String filename, FileFormats format) throws IOException {
        final ElementExporter<Material> exporter = factory.build(format);

        final Iterable<Material> materiais = listSvc.allMateriais();
        exportSvc.export(materiais, filename, exporter);
    }
}
