/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.importacao.application;

import eapli.shopfloor.gestaoproducao.domain.Produto;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *.se.1181483
 */
public class ImportadorProdutosCSV implements ImportadorProdutos {

    private final String SEPARADOR = ";";
    
    private BufferedReader stream;
    
    public ImportadorProdutosCSV() {
    }

    @Override
    public void begin(String filename) throws IOException {
        stream = new BufferedReader(new FileReader(filename));
        String headerLine = stream.readLine();
    }

    @Override
    public String element() {
        try {
            return stream.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ImportadorProdutosCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String elementSeparator() {
        return SEPARADOR;
    }

    @Override
    public void end() {
        try {
            stream.close();
        } catch (IOException ex) {
            Logger.getLogger(ImportadorProdutosCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(ImportadorProdutosCSV.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
