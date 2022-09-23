package eapli.shopfloor.gestaoproducao.exportacao.application.atividade;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.domain.Atividade;


/**
 * Classe Factory de criação do objeto apropriado para cada formato de ficheiro
 *
 *Diogo
 */
public class AtividadeExporterFactory {

    public ElementExporter<Atividade> build(FileFormats format) {
        switch (format) {
            case XML:
                return new XmlAtividadeExporter();
        }
        throw new IllegalStateException("Unknown format");
    }

}
