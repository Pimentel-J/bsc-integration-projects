/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.util.LinkedList;
import java.util.List;
import lapr.project.data.POIDB;
import lapr.project.model.POI;
import lapr.project.model.Park;
import lapr.project.model.Point;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

/**
 * Test class for "POIController".
 */
public class POIControllerTest {
    
    @InjectMocks
    private POIController controller;
    @Mock
    private POIDB mockedPoiDb;
    private List<Double> latitudes;
    private List<Double> longitudes;
    private List<Integer> elevations;
    private List<String> descriptions;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new POIController();
        controller.setPoiDb(mockedPoiDb);
        latitudes = new LinkedList<>();
        longitudes = new LinkedList<>();
        elevations = new LinkedList<>();
        descriptions = new LinkedList<>();
        
        latitudes.add(Double.valueOf(5));
        longitudes.add(Double.valueOf(-5));
        elevations.add(200);
        descriptions.add("Clerigos");
    }

    /**
     * Test of addPoi method, of class POIController. Ensures poi is saved.
     */
    @Test
    public void testAddPoi() {
        System.out.println("testAddPoi");
        boolean expResult = true;
        when(mockedPoiDb.getPoi(any(double.class), any(double.class))).thenThrow(new IllegalArgumentException());
        doNothing().when(mockedPoiDb).addPoi(any(POI.class));
        boolean result = controller.addPoi(5, -5, 200, "Clerigos");
        assertEquals(expResult, result);
        verify(mockedPoiDb, times(1)).getPoi(any(double.class), any(double.class));
        verify(mockedPoiDb, times(1)).addPoi(any(POI.class));
    }

    /**
     * Test of addPoi method, of class POIController. Ensures poi is not
     * saved, since it already exists.
     */
    @Test
    public void testAddPoiUnsuccessfulAlreadyExists() {
        System.out.println("testAddPoiUnsuccessfulAlreadyExists");
        boolean expResult = false;
        double latitude = 5, longitude = -5;
        when(mockedPoiDb.getPoi(latitude, longitude)).thenReturn(new POI());
        boolean result = controller.addPoi(latitude, longitude, 200, "Clerigos");
        assertEquals(expResult, result);
        verify(mockedPoiDb, times(1)).getPoi(any(double.class), any(double.class));
        verify(mockedPoiDb, never()).addPoi(any(POI.class));
    }

    /**
     * Test of addPoi method, of class POIController. Ensures poi is not
     * saved, since data is invalid.
     */
    @Test
    public void testAddPoiUnsuccessfulInvalidData() {
        System.out.println("testAddPoiUnsuccessfulInvalidData");
        boolean expResult = false;
        boolean result = controller.addPoi(-270, 0, 0, "");
        assertEquals(expResult, result);
    }

    /**
     * Test of addPoi method, of class POIController. Ensures poi is not
     * saved, since operation was unsuccessful in database.
     */
    @Test
    public void testAddPoiUnsuccessfulOperationNotPossible() {
        System.out.println("testAddPoiUnsuccessfulOperationNotPossible");
        boolean expResult = false;
        double latitude = 5, longitude = -5;
        when(mockedPoiDb.getPoi(latitude, longitude)).thenThrow(new IllegalArgumentException());
        doThrow(new IllegalArgumentException()).when(mockedPoiDb).addPoi(any(POI.class));
        boolean result = controller.addPoi(latitude, longitude, 200, "Clerigos");
        assertEquals(expResult, result);
        verify(mockedPoiDb, times(1)).getPoi(any(double.class), any(double.class));
        verify(mockedPoiDb, times(1)).addPoi(any(POI.class));
    }
    
    /**
     * Test of addPois method, of class POIController. Ensures pois are saved.
     */
    @Test
    public void testAddPois() {
        System.out.println("testAddPois");
        boolean expResult = true;
        when(mockedPoiDb.getPoi(any(double.class), any(double.class))).thenThrow(new IllegalArgumentException());
        doNothing().when(mockedPoiDb).addPois(anyList());
        boolean result = controller.addPois(latitudes, longitudes, elevations, descriptions);
        assertEquals(expResult, result);
        verify(mockedPoiDb, times(1)).getPoi(any(double.class), any(double.class));
        verify(mockedPoiDb, times(1)).addPois(anyList());
    }

    /**
     * Test of addPois method, of class POIController. Ensures pois are not
     * saved, since at least one already exists.
     */
    @Test
    public void testAddPoisUnsuccessfulAlreadyExists() {
        System.out.println("testAddPoisUnsuccessfulAlreadyExists");
        boolean expResult = false;
        when(mockedPoiDb.getPoi(5, -5)).thenReturn(new POI());
        boolean result = controller.addPois(latitudes, longitudes, elevations, descriptions);
        assertEquals(expResult, result);
        verify(mockedPoiDb, times(1)).getPoi(any(double.class), any(double.class));
        verify(mockedPoiDb, never()).addPois(anyList());
    }

    /**
     * Test of addPois method, of class POIController. Ensures pois are not
     * saved, since data of at least one poi is invalid.
     */
    @Test
    public void testAddPoisUnsuccessfulInvalidData() {
        System.out.println("testAddPoisUnsuccessfulInvalidData");
        boolean expResult = false;
        List<Double> invalidLats = new LinkedList<>();
        invalidLats.add(Double.valueOf(-270));
        boolean result = controller.addPois(invalidLats, longitudes, elevations, descriptions);
        assertEquals(expResult, result);
    }

    /**
     * Test of addPois method, of class POIController. Ensures pois are not
     * saved, since operation was unsuccessful in database.
     */
    @Test
    public void testAddPoisUnsuccessfulOperationNotPossible() {
        System.out.println("testAddPoisUnsuccessfulOperationNotPossible");
        boolean expResult = false;
        when(mockedPoiDb.getPoi(5, -5)).thenThrow(new IllegalArgumentException());
        doThrow(new IllegalArgumentException()).when(mockedPoiDb).addPois(anyList());
        boolean result = controller.addPois(latitudes, longitudes, elevations, descriptions);
        assertEquals(expResult, result);
        verify(mockedPoiDb, times(1)).getPoi(any(double.class), any(double.class));
        verify(mockedPoiDb, times(1)).addPois(anyList());
    }
    
    /**
     * Test of getAllPois method, of class POIController.
     */
    @Test
    void testGetAllPois() {
        System.out.println("testGetAllPois");
        int expResult = 2;
        List<POI> pois = new LinkedList<>();
        pois.add(new POI());
        pois.add(new POI());

        when(mockedPoiDb.getAllPois()).thenReturn(pois);

        int result = controller.getAllPois().size();
        assertEquals(expResult, result);
        verify(mockedPoiDb, times(1)).getAllPois();
    }
    
    /**
     * Test of getNumPois method, of class POIController.
     */
    @Test
    void testGetNumPois() {
        System.out.println("testGetNumPois");
        int expResult = 2;
        List<Point> points = new LinkedList<>();
        points.add(new POI());
        points.add(new POI());
        points.add(new Park());
        int result = controller.getNumPois(points);
        assertEquals(expResult, result);
    }    
    
}
