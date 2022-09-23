package eapli.shopfloor.gestaoproducao.exportacao.application.ordemProducao;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;


/**
 * Classe Factory de criação do objeto apropriado para cada formato de ficheiro
 *
 *Diogo
 */
public class OrdemProducaoExporterFactory {

    public ElementExporter<OrdemProducao> build(FileFormats format) {
        switch (format) {
            case XML:
                return new XmlOrdemProducaoExporter();
        }
        throw new IllegalStateException("Unknown format");
    }

}
