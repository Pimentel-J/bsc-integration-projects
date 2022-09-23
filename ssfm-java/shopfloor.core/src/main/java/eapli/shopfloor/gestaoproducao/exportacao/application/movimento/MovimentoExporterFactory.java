package eapli.shopfloor.gestaoproducao.exportacao.application.movimento;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.domain.Movimento;


/**
 * Classe Factory de criação do objeto apropriado para cada formato de ficheiro
 *
 *Diogo
 */
public class MovimentoExporterFactory {

    public ElementExporter<Movimento> build(FileFormats format) {
        switch (format) {
            case XML:
                return new XmlMovimentoExporter();
        }
        throw new IllegalStateException("Unknown format");
    }

}
