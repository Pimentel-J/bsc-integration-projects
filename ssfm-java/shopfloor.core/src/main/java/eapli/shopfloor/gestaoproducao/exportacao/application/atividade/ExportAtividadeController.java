package eapli.shopfloor.gestaoproducao.exportacao.application.atividade;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.application.ListAtividadesService;
import eapli.shopfloor.gestaoproducao.domain.Atividade;

import java.io.IOException;

/**
 *Diogo
 */
public class ExportAtividadeController {

    private final ListAtividadesService listSvc = new ListAtividadesService();
    private final AtividadeExporterFactory factory = new AtividadeExporterFactory();
    private final AtividadeExporterService exportSvc = new AtividadeExporterService();

    public void export(String filename, FileFormats format) throws IOException {
        final ElementExporter exporter = factory.build(format);

        final Iterable<Atividade> Atividades = listSvc.allAtividades();
        exportSvc.export(Atividades, filename, exporter);
    }

}
