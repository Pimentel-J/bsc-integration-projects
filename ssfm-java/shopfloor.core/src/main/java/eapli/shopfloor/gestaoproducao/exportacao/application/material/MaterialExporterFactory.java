package eapli.shopfloor.gestaoproducao.exportacao.application.material;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.domain.Material;

/**
 * Classe Factory de criação do objeto apropriado para cada formato de ficheiro
 *
 *João Pimentel 
 */
public class MaterialExporterFactory {

    public ElementExporter<Material> build(FileFormats format) {
        switch (format) {
            case XML:
                return new XmlMaterialExporter();
        }
        throw new IllegalStateException("Unknown format");
    }

}
