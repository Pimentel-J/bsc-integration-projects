package eapli.shopfloor.gestaochaofabrica.importacao.application;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.importacao.ElementImporter;

/**
 *João Pimentel 
 */
public final class ImportadorConfigMaquinaFactory {

    public ElementImporter build(FileFormats format) {
        switch (format) {
            case TXT:
                return new ImportadorConfigMaquinaTxt();
        }
        throw new IllegalStateException("Unknown format");
    }
}
