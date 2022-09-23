package eapli.shopfloor.gestaochaofabrica.importacao.application;

import eapli.shopfloor.general.application.ListRightPathService;
import eapli.shopfloor.general.importacao.ElementImporter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Importador para ficheiros .txt
 *
 *João Pimentel 
 */
public class ImportadorConfigMaquinaTxt implements ElementImporter {

    private BufferedReader stream;

    public ImportadorConfigMaquinaTxt() {
    }

    @Override
    public void begin(String inputFile) throws IOException {
        String inputFileRightPath = ListRightPathService.getRightPathConfigFiles() + inputFile;
        stream = new BufferedReader(new FileReader(inputFileRightPath));
    }

    @Override
    public List<String> readFile(String inputFile) {
        List<String> lst = new LinkedList<>();
        String line;
        try {
            line = stream.readLine();
            while (line != null) {
                String lineData = line.trim();
                lst.add(lineData);
                line = stream.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(ImportadorConfigMaquinaTxt.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            end();
        }
        return lst;
    }

    @Override
    public String element() {
        try {
            return stream.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ImportadorConfigMaquinaTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String elementSeparator() {
        return null;
    }

    @Override
    public void end() {
        try {
            stream.close();
        } catch (IOException ex) {
            Logger.getLogger(ImportadorConfigMaquinaTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(ImportadorConfigMaquinaTxt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
