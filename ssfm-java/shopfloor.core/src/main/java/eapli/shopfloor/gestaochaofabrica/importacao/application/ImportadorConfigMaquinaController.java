package eapli.shopfloor.gestaochaofabrica.importacao.application;

import eapli.framework.application.UseCaseController;
import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.application.ListFileFormatService;
import eapli.shopfloor.general.importacao.ElementImporter;
import eapli.shopfloor.gestaochaofabrica.application.ListMaquinasService;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;

import java.io.IOException;

/**
 *João Pimentel 
 */
@UseCaseController
public class ImportadorConfigMaquinaController {

    private final ListFileFormatService listFormatSvc = new ListFileFormatService();
    private final ListMaquinasService listMaqSvc = new ListMaquinasService();
    private final ImportadorConfigMaquinaFactory factory = new ImportadorConfigMaquinaFactory();
    private final ImportadorConfigMaquinaService importSvc = new ImportadorConfigMaquinaService();

    public void importar(String filename, FileFormats format, Maquina maquina) throws IOException {
        final ElementImporter importador = factory.build(format);
        if (maquina == null) {
            throw new IllegalArgumentException();
        }
        importSvc.importar(filename, importador, maquina);
    }

    public Iterable<Maquina> listMaquinas() { return this.listMaqSvc.allMaquinas(); }

    public Iterable<FileFormats> listFileFormats() { return this.listFormatSvc.importConfigMaquinaFileFormats(); }
}
