package eapli.shopfloor.gestaochaofabrica.importacao.application;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.util.TemplateMethod;
import eapli.shopfloor.general.importacao.ElementImporter;
import eapli.shopfloor.gestaochaofabrica.domain.FicheiroConfigMaquina;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import eapli.shopfloor.gestaochaofabrica.repositories.MaquinaRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *João Pimentel 
 */
public class ImportadorConfigMaquinaService {

    private static final Logger logger = LogManager.getLogger(ImportadorConfigMaquinaService.class);
    private final MaquinaRepository repository = PersistenceContext.repositories().maquinas();

    @TemplateMethod
    public Maquina importar(String nomeFicheiro, ElementImporter importador, Maquina maquina) throws IOException {

        String file = "no file type";

        if (importador.toString().contains("Txt")) {
            file = String.join("", nomeFicheiro, ".txt");
        } else {
            System.out.println("Formato ficheiro não definido");
        }
        try {
            try {
                importador.begin(file);
            } catch (FileNotFoundException fex) {
                System.out.println(
                        "O sistema não conseguiu localizar o ficheiro especificado. Por favor tente " + "novamente.");
                importador.begin(file);
            }
            List<String> linhas = importador.readFile(file);
            String totalSemEspaco = linhas.toString();

            final FicheiroConfigMaquina novoFicheiro = new FicheiroConfigMaquina(nomeFicheiro, totalSemEspaco);

            maquina.addFicheiros(novoFicheiro);

            return this.repository.save(maquina);

        } catch (final IntegrityViolationException | IOException ex) {
            if (ex instanceof IntegrityViolationException) {
                logger.error("Error performing the operation", ex);
                throw ex;
            } else {
                logger.error("Um erro ocorreu na importação do ficheiro!", ex);
                throw ex;
            }
        } finally {
            importador.cleanup();
        }
    }
}
