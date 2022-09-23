/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.importacao.application;

import eapli.framework.util.Strategy;
import java.io.IOException;

/**
 *
 *.se.1181483
 */
@Strategy
public interface ImportadorProdutos {
    /**
     * Initiate the import process. The implementation should open the underlying resource (e.g., file) and skip the first line.
     *
     * @param filename
     * @throws java.io.IOException
     */
    void begin(String filename) throws IOException;

    /**
     * Import one single element.
     *
     * @param e
     */
    String element();

    /**
     * Indicates that a new element will be created.
     */
    String elementSeparator();

    /**
     * Indicates that there are no more elements to import. The implementation should create any "document closing"
     * element it might need and close the underlying resource.
     */
    void end();

    /**
     * Gives the exporter implementation a change to cleanup in case some exception has occurred.
     */
    void cleanup();
}
