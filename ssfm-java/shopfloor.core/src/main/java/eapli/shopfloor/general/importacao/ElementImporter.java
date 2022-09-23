package eapli.shopfloor.general.importacao;

import eapli.framework.util.Strategy;

import java.io.IOException;
import java.util.List;

/**
 * Interface para importação de ficheiros
 *
 *João Pimentel 
 */
@Strategy
public interface ElementImporter {

    /**
     * Inicia a importação dos dados do ficheiro
     *
     * @param inputFile
     */
    void begin(String inputFile) throws IOException;

    /**
     * Importa o conteúdo total do ficheiro
     *
     * @param inputFile
     */
    List<String> readFile(String inputFile);

    /**
     * Importa um único elemento
     */
    String element();

    /**
     * Separador para criar mais vários elementos
     */
    String elementSeparator();

    /**
     * Permite terminar a importação
     */
    void end();

    /**
     * "Limpeza" na implementação, caso ocorra uma exceção
     */
    void cleanup();

}
