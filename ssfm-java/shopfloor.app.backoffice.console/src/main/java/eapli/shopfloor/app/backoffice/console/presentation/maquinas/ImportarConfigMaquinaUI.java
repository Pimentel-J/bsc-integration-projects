package eapli.shopfloor.app.backoffice.console.presentation.maquinas;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import eapli.shopfloor.gestaochaofabrica.importacao.application.ImportadorConfigMaquinaController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 *João Pimentel 
 */
public class ImportarConfigMaquinaUI extends AbstractUI {

    private static final Logger LOGGER = LogManager.getLogger(ImportarConfigMaquinaUI.class);

    private final ImportadorConfigMaquinaController controller = new ImportadorConfigMaquinaController();

    @Override
    protected boolean doShow() {

        final Iterable<Maquina> allMaquinas = this.controller.listMaquinas();
        final Iterable<FileFormats> allFormats = this.controller.listFileFormats();

        if (!allMaquinas.iterator().hasNext()) {
            System.out.println("Não existem máquinas!");
        } else {

            final FileFormats selectedFormat = selectFileFormat(allFormats);

            final Maquina selectedMaquina = selectMaquinas(allMaquinas);

            try {
                final String nomeFicheiro = Console.readLine("Nome Ficheiro:");
                this.controller.importar(nomeFicheiro, selectedFormat, selectedMaquina);
                System.out.println("Configurações carregadas com sucesso");
            } catch (final IntegrityViolationException | IOException ex) {
                if (ex instanceof IntegrityViolationException) {
                    LOGGER.error("Error performing the operation", ex);
                    System.out.println(
                            "Unfortunately there was an unexpected error in the application. Please try again " +
                                    "and if the problem persists, contact your system administrator.");
                } else {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public String headline() {
        return "Novo Ficheiro Config. Máquina";
    }

    private void exitOption(String option) {
        if (option.equals("0")) {
            System.exit(0);
        }
    }

    private FileFormats selectFileFormat(Iterable<FileFormats> allFormats) {
        final SelectWidget<FileFormats> selectorFormat = new SelectWidget<>("Formatos:", allFormats,
                new ImportFileFormatPrinter());
        selectorFormat.show();
        exitOption(String.valueOf(selectorFormat.selectedOption()));
        return selectorFormat.selectedElement();
    }

    private Maquina selectMaquinas(Iterable<Maquina> allMaquinas) {
        final SelectWidget<Maquina> selectorMaquina = new SelectWidget<>("Máquinas:", allMaquinas,
                new MaquinaPrinter());
        selectorMaquina.show();
        exitOption(String.valueOf(selectorMaquina.selectedOption()));
        return selectorMaquina.selectedElement();
    }

}
