package eapli.shopfloor.gestaochaofabrica.exportacao.application.maquina;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaochaofabrica.application.ListMaquinasService;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;

import java.io.IOException;

/**
 *Diogo
 */
public class ExportMaquinaController {

    private final ListMaquinasService listSvc = new ListMaquinasService();
    private final MaquinaExporterFactory factory = new MaquinaExporterFactory();
    private final MaquinaExporterService exportSvc = new MaquinaExporterService();

    public void export(String filename, FileFormats format) throws IOException {
        final ElementExporter<Maquina> exporter = factory.build(format);

        final Iterable<Maquina> maquinas = listSvc.allMaquinas();
        exportSvc.export(maquinas, filename, exporter);
    }

}
