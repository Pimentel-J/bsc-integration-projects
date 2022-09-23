package eapli.shopfloor.gestaoproducao.exportacao.application.produto;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.domain.Produto;

/**
 * Classe Factory de criação do objeto apropriado para cada formato de ficheiro
 *
 *João Pimentel 
 */
public class ProdutoExporterFactory {

    public ElementExporter<Produto> build(FileFormats format) {
        switch (format) {
            case XML:
                return new XmlProdutoExporter();
        }
        throw new IllegalStateException("Unknown format");
    }

}
