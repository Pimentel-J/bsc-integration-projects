package eapli.shopfloor.gestaoproducao.exportacao.application.ordemProducao;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.application.ListOrdensProducaoService;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;

import java.io.IOException;

/**
 *Diogo
 */
public class ExportOrdemProducaoController {

    private final ListOrdensProducaoService listSvc = new ListOrdensProducaoService();
    private final OrdemProducaoExporterFactory factory = new OrdemProducaoExporterFactory();
    private final OrdemProducaoExporterService exportSvc = new OrdemProducaoExporterService();

    public void export(String filename, FileFormats format) throws IOException {
        final ElementExporter exporter = factory.build(format);

        final Iterable<OrdemProducao> OrdensProducao = listSvc.allOrdensProducao();
        exportSvc.export(OrdensProducao, filename, exporter);
    }

}
