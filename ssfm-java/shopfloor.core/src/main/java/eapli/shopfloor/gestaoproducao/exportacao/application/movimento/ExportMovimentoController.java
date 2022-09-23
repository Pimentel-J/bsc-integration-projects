package eapli.shopfloor.gestaoproducao.exportacao.application.movimento;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.application.ListMovimentosService;
import eapli.shopfloor.gestaoproducao.domain.Movimento;

import java.io.IOException;

/**
 *Diogo
 */
public class ExportMovimentoController {

    private final ListMovimentosService listSvc = new ListMovimentosService();
    private final MovimentoExporterFactory factory = new MovimentoExporterFactory();
    private final MovimentoExporterService exportSvc = new MovimentoExporterService();

    public void export(String filename, FileFormats format) throws IOException {
        final ElementExporter exporter = factory.build(format);

        final Iterable<Movimento> Movimentos = listSvc.allMovimentos();
        exportSvc.export(Movimentos, filename, exporter);
    }

}
