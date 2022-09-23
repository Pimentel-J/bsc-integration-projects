package eapli.shopfloor.gestaoproducao.exportacao.application.chaofabricareport;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.application.ListFileFormatService;
import eapli.shopfloor.general.exportacao.ElementExporter;

import java.io.IOException;

/**
 *João Pimentel 
 */
public class ChaoFabricaReportExporterController {

    private final ChaoFabricaReportExporterFactory exportFactory = new ChaoFabricaReportExporterFactory();
    private final ChaoFabricaReportExporterService exportSvc = new ChaoFabricaReportExporterService();
    private final ListFileFormatService listFormatSvc = new ListFileFormatService();

    public void export(String filename, FileFormats format) throws IOException {
        final ElementExporter<String> exporter = exportFactory.build(format);
        exportSvc.export(filename, exporter, exportFactory.allChaoFabricaInfo(), exportFactory.allGestaoProducaoInfo());
    }

    public Iterable<FileFormats> listFileFormats() { return this.listFormatSvc.exportGestaoProducaoFileFormats(); }

}
