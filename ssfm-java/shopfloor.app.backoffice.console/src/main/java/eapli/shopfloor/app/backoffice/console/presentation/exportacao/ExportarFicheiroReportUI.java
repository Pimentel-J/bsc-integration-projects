package eapli.shopfloor.app.backoffice.console.presentation.exportacao;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import eapli.framework.util.Files;
import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.application.ListRightPathService;
import eapli.shopfloor.gestaoproducao.exportacao.application.chaofabricareport.ChaoFabricaReportExporterController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *João Pimentel 
 */
public class ExportarFicheiroReportUI extends AbstractUI {

    private static final Logger LOGGER = LogManager.getLogger(ExportarFicheiroReportUI.class);

    private final ChaoFabricaReportExporterController controller = new ChaoFabricaReportExporterController();

    @Override
    protected boolean doShow() {

        final Iterable<FileFormats> allFormats = this.controller.listFileFormats();

        final FileFormats selectedFormat = selectFileFormat(allFormats);

        final String nomeFicheiro = Console.readLine("Nome Ficheiro:");
        exportTo(nomeFicheiro, selectedFormat);

        System.out.println("Ficheiro exportado com sucesso");

        return false;
    }

    @Override
    public String headline() {
        return "EXPORTAR FICHEIRO";
    }

    private FileFormats selectFileFormat(Iterable<FileFormats> allFormats) {
        final SelectWidget<FileFormats> selectorFormat = new SelectWidget<>("Formatos:", allFormats,
                new ExportFileFormatPrinter());
        selectorFormat.show();
        exitOption(String.valueOf(selectorFormat.selectedOption()));
        return selectorFormat.selectedElement();
    }

    private void exitOption(String option) {
        if (option.equals("0")) {
            System.exit(0);
        }
    }

    private void exportTo(String filename, FileFormats format) {
        final String path = ListRightPathService.getRightPathExportedFiles() + filename + "." + format.toString();
        try {
            controller.export(path, format);
            outputExportedContent(format, path);
        } catch (final IOException e) {
            LOGGER.error(e);
        }
    }

    private void outputExportedContent(FileFormats format, final String filename) throws IOException {
        // output the exported content
        Files.contentOf(new FileInputStream(filename));
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("-- EXPORTAR INFO PARA {} --", format);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename),
                    "UTF" + "-8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    LOGGER.info(line);
                }
            }

            LOGGER.info("----");
        }
    }

}
