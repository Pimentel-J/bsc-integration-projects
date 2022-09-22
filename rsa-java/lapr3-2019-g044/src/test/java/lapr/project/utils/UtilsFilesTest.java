/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

/**
 * Test class for "UtilsFiles".
 */
public class UtilsFilesTest {

    List<String[]> l_final = new ArrayList<>();
    List<String[]> f_final = new ArrayList<>();

    public UtilsFilesTest() throws IOException, Exception {

        List<String> l = Files.lines(Paths.get("test_empty.txt")).collect(Collectors.toList());

        for (String string : l) {
            l_final.add(string.trim().split(";", -1));
        }

        List<String> f = Files.lines(Paths.get("test_notEmpty.txt")).collect(Collectors.toList());

        boolean headerRead = false;
        for (String string : f) {
            if (!string.startsWith("#") && string.trim().length() > 0) {
                if (headerRead) {
                    f_final.add(string.trim().split(";", -1));
                }
                headerRead = true;
            }
        }
    }

    /**
     * Test of readFile method, of class POI.
     */
    @Test
    public void testReadFile() {
        System.out.println("testReadFile");

        List<String[]> l_result = new ArrayList<>();
        List<String[]> f_result = new ArrayList<>();

        l_result = UtilsFiles.readFile("test_empty2.txt");
        assertArrayEquals(l_result.toArray(), l_final.toArray());
        
        l_result = UtilsFiles.readFile("test_empty.txt");
        assertArrayEquals(l_result.toArray(), l_final.toArray());

        f_result = UtilsFiles.readFile("test_notEmpty.txt");
        assertArrayEquals(f_result.toArray(), f_final.toArray());

        
        
    }

}
