/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lapr.project.data.PathDB;
import lapr.project.model.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 *
 */
public class PathControllerTest {

    @InjectMocks
    private PathController controller;

    @Mock
    private PathDB mockedPathDB;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new PathController();
        controller.setPathDB(mockedPathDB);
    }

    /**
     * Test of AddPath method, of class PathController.
     */
    @Test
    public void testAddPath() {
        System.out.println("AddPath");

        Path path = new Path();

        when(mockedPathDB.getPath(12.1, 12.1, 12.3, 12.3)).thenReturn(path);
        assertFalse(controller.addPath(12.1, 12.1, 12.3, 12.3, 0.01, 3, 20.1f));

        when(mockedPathDB.getPath(12.1, 12.1, 12.3, 12.3)).thenThrow(new IllegalArgumentException());

        doThrow(new IllegalArgumentException()).when(mockedPathDB).addPath(new Path(12.1, 12.1, 12.3, 12.3, 0.01, 3, 20.1f));
        assertFalse(controller.addPath(12.1, 12.1, 12.3, 12.3, 0.01, 3, 20.1f));

        doNothing().when(mockedPathDB).addPath(new Path(12.1, 12.1, 12.3, 12.3, 0.01, 3, 20.1f));
        assertTrue(controller.addPath(12.1, 12.1, 12.3, 12.3, 0.01, 3, 20.1f));

        assertFalse(controller.addPath(1234, 12.1, 12.3, 12.3, 0.01, 3, 20.1f));
    }

    /**
     * Test of AddPaths method, of class PathController.
     */
    @Test
    public void testAddPaths() {
        System.out.println("AddPaths");

        Path path;
        List<Double> latA = new LinkedList<>(), longA = new LinkedList<>(),
                latB = new LinkedList<>(), longB = new LinkedList<>(), k_coeffic = new LinkedList<>();
        List<Integer> wind_dir = new LinkedList<>();
        List<Float> wind_speed = new LinkedList<>();
        List<Path> paths = new LinkedList<>();

        assertTrue(controller.addPaths(latA, longA, latB, longB, k_coeffic, wind_dir, wind_speed));
        latA.add(230.3);
        latB.add(12.3);
        longA.add(12.4);
        longB.add(12.4);
        k_coeffic.add(0.01);
        wind_dir.add(3);
        wind_speed.add(12.1f);

        assertFalse(controller.addPaths(latA, longA, latB, longB, k_coeffic,
                wind_dir, wind_speed));

        latA.remove(0);
        latA.add(12.3);

        path = new Path(12.3, 12.4, 12.3, 12.4, 0.01, 3, 12.1f);
        paths.add(path);

        doThrow(new IllegalArgumentException()).when(mockedPathDB).addPaths(paths);
        assertFalse(controller.addPaths(latA, longA, latB, longB, k_coeffic, wind_dir, wind_speed));

        doNothing().when(mockedPathDB).addPaths(paths);
        doThrow(new IllegalArgumentException()).when(mockedPathDB).getPath(12.3, 12.4, 12.3, 12.4);
        assertTrue(controller.addPaths(latA, longA, latB, longB, k_coeffic, wind_dir, wind_speed));
        doThrow(new IllegalArgumentException()).when(mockedPathDB).addPaths(paths);
        assertFalse(controller.addPaths(latA, longA, latB, longB, k_coeffic, wind_dir, wind_speed));

    }

    @Test
    void testGetAllPaths() {
        System.out.println("testGetAllPaths");
        int expResult = 2;
        List<Path> paths = new ArrayList<>();
        paths.add(new Path(12.3, 12.4, 12.3, 12.4, 0.01, 3, 12.1f));
        paths.add(new Path(9.3, 11.7, 14.5, 10.4, 0.03, 5, 11.1f));

        when(mockedPathDB.getAllPaths()).thenReturn(paths);

        int result = controller.getAllPaths().size();
        assertEquals(expResult, result);
        verify(mockedPathDB, times(1)).getAllPaths();
    }
}
