package eapli.shopfloor.infrastructure.smoketests;

import eapli.framework.actions.Action;
import eapli.framework.util.Files;
import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.application.ListRightPathService;
import eapli.shopfloor.gestaoproducao.exportacao.application.material.ExportMaterialController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *João Pimentel 
 */
public class MaterialExportSmokeTester implements Action {
    private static final Logger LOGGER = LogManager.getLogger(MaterialExportSmokeTester.class);

    final ExportMaterialController exportMateriaisController = new ExportMaterialController();

    @Override
    public boolean execute() {
        testExportTo(FileFormats.XML);

        // nothing else to do
        return true;
    }

    private void testExportTo(FileFormats format) {
        final String filename = ListRightPathService.getRightPathExportedFiles() + "materiais." + format.toString();
        try {
            exportMateriaisController.export(filename, format);
            outputExportedContent(format, filename);
        } catch (final IOException e) {
            LOGGER.error(e);
        }
    }

    private void outputExportedContent(FileFormats format, final String filename) throws IOException {
        // output the exported content
        Files.contentOf(new FileInputStream(filename));
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("-- EXPORTAR MATERIAIS PARA {} --", format);

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
