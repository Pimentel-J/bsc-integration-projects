package eapli.shopfloor.general;

/**
 * Formatos de ficheiros
 *
 *João Pimentel 
 */
public enum FileFormats {

    CSV,

    TXT,

    XML;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

}
