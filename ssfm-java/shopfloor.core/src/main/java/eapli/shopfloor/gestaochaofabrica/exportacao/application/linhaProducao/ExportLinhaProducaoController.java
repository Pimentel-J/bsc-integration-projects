package eapli.shopfloor.gestaochaofabrica.exportacao.application.linhaProducao;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaochaofabrica.application.ListLinhasProducaoService;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;


import java.io.IOException;

/**
 *Diogo
 */
public class ExportLinhaProducaoController {

    private final ListLinhasProducaoService listSvc = new ListLinhasProducaoService();
    private final LinhaProducaoExporterFactory factory = new LinhaProducaoExporterFactory();
    private final LinhaProducaoExporterService exportSvc = new LinhaProducaoExporterService();

    public void export(String filename, FileFormats format) throws IOException {
        final ElementExporter exporter = factory.build(format);

        final Iterable<LinhaProducao> LinhasProducao = listSvc.allLinhasProducao();
        exportSvc.export(LinhasProducao, filename, exporter);
    }

}
