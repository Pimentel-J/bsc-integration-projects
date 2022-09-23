package eapli.shopfloor.general.exportacao;

import eapli.framework.util.Strategy;

import java.io.IOException;
import java.util.Collection;

/**
 * Interface para exportação de ficheiros
 *
 *João Pimentel 
 */
@Strategy
public interface ElementStringExporter<T> {

    String header() throws IOException;

    Collection<String> element(T e);

    String ending();
}
