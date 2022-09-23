package eapli.shopfloor.gestaochaofabrica.exportacao.application.maquina;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;

/**
 * Classe Factory de criação do objeto apropriado para cada formato de ficheiro
 *
 *Diogo
 */
public class MaquinaExporterFactory {

    public ElementExporter<Maquina> build(FileFormats format) {
        switch (format) {
            case XML:
                return new XmlMaquinaExporter();
        }
        throw new IllegalStateException("Unknown format");
    }


}
