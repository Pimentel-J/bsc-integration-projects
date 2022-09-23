package eapli.shopfloor.gestaochaofabrica.exportacao.application.linhaProducao;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;


/**
 * Classe Factory de criação do objeto apropriado para cada formato de ficheiro
 *
 *Diogo
 */
public class LinhaProducaoExporterFactory {

    public ElementExporter<LinhaProducao> build(FileFormats format) {
        switch (format) {
            case XML:
                return new XmlLinhaProducaoExporter();
        }
        throw new IllegalStateException("Unknown format");
    }

}
