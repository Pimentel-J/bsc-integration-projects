/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


/**
 * Has methods to read from or write in files.
 */
public class UtilsFiles {
    
    /**
     * Reads file passed and returns a list of String[]. Each String[] is a line 
     * split by ";". Lines with comments are not considered. Header (first line 
     * without comment) is not considered. Accounts for empty strings between 
     * ";" (ex: "3;;5") and in the end of the line (ex: "3;5;"). 
     * 
     * @param inputFile name of the file to read
     * @return list of String[] (lines from file, each one split by ";")
     */
    public static List<String[]> readFile(String inputFile) {
        List<String[]> lst = new LinkedList<>();
        boolean headerRead = false;
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            line = br.readLine();
            while(line != null) {
                if (!line.startsWith("#") && !line.startsWith("\"") && line.trim().length() > 0) {
                    if (headerRead) {
                        String[] lineData = line.trim().split(";", -1);
                        lst.add(lineData);
                    }
                    headerRead = true;
                }
                line = br.readLine();
            }
        } catch (IOException e) {
              e.printStackTrace();
        }
        return lst;
    }    
}
