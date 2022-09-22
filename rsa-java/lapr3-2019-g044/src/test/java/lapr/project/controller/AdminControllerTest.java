/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import lapr.project.data.*;
import lapr.project.model.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

/**
 * Test class for "AdminController".
 */
public class AdminControllerTest {

    @InjectMocks
    private AdminController example;
    @Mock
    private ParkDB mockedParkDB;
    @Mock
    private BicycleDB mockedBicycleDB;
    @Mock
    private EscooterDB mockedEscooterDB;

    private List<String> ids;
    private List<Double> latitudes;
    private List<Double> longitudes;
    private List<Integer> elevations;
    private List<String> descriptions;
    private List<Integer> maxNumsBikes;
    private List<Integer> maxNumsEscooters;
    private List<Integer> inputVoltages;
    private List<Integer> inputCurrents;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        example = new AdminController();
        example.setParkDB(mockedParkDB);
        example.setBicycleDB(mockedBicycleDB);
        example.setEscooterDB(mockedEscooterDB);
        ids = new LinkedList<>();
        latitudes = new LinkedList<>();
        longitudes = new LinkedList<>();
        elevations = new LinkedList<>();
        descriptions = new LinkedList<>();
        maxNumsBikes = new LinkedList<>();
        maxNumsEscooters = new LinkedList<>();
        inputVoltages = new LinkedList<>();
        inputCurrents = new LinkedList<>();

        ids.add("Trindade");
        latitudes.add(Double.valueOf(5));
        longitudes.add(Double.valueOf(-5));
        elevations.add(200);
        descriptions.add("Parque da Trindade");
        maxNumsBikes.add(10);
        maxNumsEscooters.add(7);
        inputVoltages.add(2);
        inputCurrents.add(2);
    }

    /**
     * Test of addPark method, of class AdminController. Ensures park is saved.
     */
    @Test
    public void testAddPark() {
        System.out.println("testAddPark");
        boolean expResult = true;
        when(mockedParkDB.getPark(any(String.class))).thenThrow(new IllegalArgumentException());
        doNothing().when(mockedParkDB).addPark(any(Park.class));
        boolean result = example.addPark("1", 5, -5, 200, "Porto", 10, 12, 16, 2);
        assertEquals(expResult, result);
        verify(mockedParkDB, times(1)).getPark(any(String.class));
        verify(mockedParkDB, times(1)).addPark(any(Park.class));
    }

    /**
     * Test of addPark method, of class AdminController. Ensures park is not
     * saved, since it already exists.
     */
    @Test
    public void testAddParkUnsuccessfulAlreadyExists() {
        System.out.println("testAddParkUnsuccessfulAlreadyExists");
        boolean expResult = false;
        String id = "1";
        when(mockedParkDB.getPark(id)).thenReturn(new Park());
        boolean result = example.addPark(id, 5, -5, 200, "Porto", 10, 12, 16, 2);
        assertEquals(expResult, result);
        verify(mockedParkDB, times(1)).getPark(any(String.class));
        verify(mockedParkDB, never()).addPark(any(Park.class));
    }

    /**
     * Test of addPark method, of class AdminController. Ensures park is not
     * saved, since data is invalid.
     */
    @Test
    public void testAddParkUnsuccessfulInvalidData() {
        System.out.println("testAddParkUnsuccessfulInvalidData");
        boolean expResult = false;
        boolean result = example.addPark("", 0, 0, 0, "", 0, 0, 0, 0);
        assertEquals(expResult, result);
    }

    /**
     * Test of addPark method, of class AdminController. Ensures park is not
     * saved, since it already exists.
     */
    @Test
    public void testAddParkUnsuccessfulOperationNotPossible() {
        System.out.println("testAddParkUnsuccessfulOperationNotPossible");
        boolean expResult = false;
        String id = "1";
        when(mockedParkDB.getPark(id)).thenThrow(new IllegalArgumentException());
        doThrow(new IllegalArgumentException()).when(mockedParkDB).addPark(any(Park.class));
        boolean result = example.addPark(id, 5, -5, 200, "Porto", 10, 12, 16, 2);
        assertEquals(expResult, result);
        verify(mockedParkDB, times(1)).getPark(any(String.class));
        verify(mockedParkDB, times(1)).addPark(any(Park.class));
    }

    /**
     * Test of addParks method, of class AdminController. Ensures parks are
     * saved.
     */
    @Test
    public void testAddParks() {
        System.out.println("testAddParks");
        boolean expResult = true;
        when(mockedParkDB.getPark(any(String.class))).thenThrow(new IllegalArgumentException());
        doNothing().when(mockedParkDB).addParks(anyList());
        boolean result = example.addParks(ids, latitudes, longitudes, elevations,
                descriptions, maxNumsBikes, maxNumsEscooters, inputVoltages, inputCurrents);
        assertEquals(expResult, result);
        verify(mockedParkDB, times(1)).getPark(any(String.class));
        verify(mockedParkDB, times(1)).addParks(anyList());
    }

    /**
     * Test of addParks method, of class AdminController. Ensures parks are not
     * saved, since at least one already exists.
     */
    @Test
    public void testAddParksUnsuccessfulAlreadyExists() {
        System.out.println("testAddParksUnsuccessfulAlreadyExists");
        boolean expResult = false;
        when(mockedParkDB.getPark("Trindade")).thenReturn(new Park());
        boolean result = example.addParks(ids, latitudes, longitudes, elevations,
                descriptions, maxNumsBikes, maxNumsEscooters, inputVoltages, inputCurrents);
        assertEquals(expResult, result);
        verify(mockedParkDB, times(1)).getPark(any(String.class));
        verify(mockedParkDB, never()).addParks(anyList());
    }

    /**
     * Test of addParks method, of class AdminController. Ensures parks are not
     * saved, since data of at least one park is invalid.
     */
    @Test
    public void testAddParksUnsuccessfulInvalidData() {
        System.out.println("testAddParksUnsuccessfulInvalidData");
        boolean expResult = false;
        List<Double> invalidLats = new LinkedList<>();
        invalidLats.add(Double.valueOf(-270));
        boolean result = example.addParks(ids, invalidLats, longitudes, elevations,
                descriptions, maxNumsBikes, maxNumsEscooters, inputVoltages, inputCurrents);
        assertEquals(expResult, result);
    }

    /**
     * Test of addParks method, of class AdminController. Ensures parks are not
     * saved, since operation was unsuccessful in database.
     */
    @Test
    public void testAddParksUnsuccessfulOperationNotPossible() {
        System.out.println("testAddParksUnsuccessfulOperationNotPossible");
        boolean expResult = false;
        when(mockedParkDB.getPark("Trindade")).thenThrow(new IllegalArgumentException());
        doThrow(new IllegalArgumentException()).when(mockedParkDB).addParks(anyList());
        boolean result = example.addParks(ids, latitudes, longitudes, elevations,
                descriptions, maxNumsBikes, maxNumsEscooters, inputVoltages, inputCurrents);
        assertEquals(expResult, result);
        verify(mockedParkDB, times(1)).getPark(any(String.class));
        verify(mockedParkDB, times(1)).addParks(anyList());
    }

    @Test
    void testUpdatePark() {
        assertEquals("", example.updatePark("1", 5, -5, 200, "Coimbra", 10, 12, 16, 2));

        Park park = new Park("1", 5, -5, 200, "Coimbra", 10, 12, 16, 2);

        when(mockedParkDB.getPark("1")).thenReturn(park);
        when(mockedParkDB.updatePark(park)).thenReturn("1");
        assertEquals(null, example.updatePark("1", 5, -5, 200, "Coimbra", 10, 12, 16, 2));

        when(mockedParkDB.getPark("1")).thenThrow(new IllegalArgumentException());
        assertEquals("", example.updatePark("1", 5, -5, 200, "Coimbra", 10, 12, 16, 2));
    }

    @Test
    void testDeletePark() {
        assertEquals("", example.deletePark("1"));

        Park park = new Park("1", 5, -5, 200, "Coimbra", 10, 12, 16, 2);

        when(mockedParkDB.getPark("1")).thenReturn(park);
        when(mockedParkDB.deletePark("1")).thenReturn("1");
        assertEquals("1", example.deletePark("1"));

        when(mockedParkDB.getPark("1")).thenThrow(new IllegalArgumentException());
        assertEquals("", example.deletePark("1"));
    }

    /**
     * Test of addBicycle method, of class AdminController. Ensures a bicycle is
     * saved.
     */
    @Test
    public void testAddBicycle() {
        System.out.println("testAddBicycle");
        boolean expResult = true;
        when(mockedBicycleDB.getBicycle(any(String.class))).thenThrow(new IllegalArgumentException());
        doNothing().when(mockedBicycleDB).addBicycle(any(Bicycle.class));
        boolean result = example.addBicycle("B050", 5, 25.0, 35.0, 1.1f, 0.5f, 26);
        assertEquals(expResult, result);
        verify(mockedBicycleDB, times(1)).getBicycle(any(String.class));
        verify(mockedBicycleDB, times(1)).addBicycle(any(Bicycle.class));
    }

    /**
     * Test of addBicycle method, of class AdminController. Ensures bicycle is
     * not saved, since it already exists.
     */
    @Test
    public void testAddBicycleUnsuccessfulAlreadyExists() {
        System.out.println("testAddBicycleUnsuccessfulAlreadyExists");
        boolean expResult = false;
        String descr = "B050";
        when(mockedBicycleDB.getBicycle(descr)).thenReturn(new Bicycle());
        boolean result = example.addBicycle(descr, 5, 25.0, 35.0, 1.1f, 0.5f, 26);
        assertEquals(expResult, result);
        verify(mockedBicycleDB, times(1)).getBicycle(any(String.class));
        verify(mockedBicycleDB, never()).addBicycle(any(Bicycle.class));
    }

    /**
     * Test of addBicycle method, of class AdminController. Ensures a bicycle is
     * not saved, since data is invalid.
     */
    @Test
    public void testAddBicycleUnsuccessfulInvalidData() {
        System.out.println("testAddBicycleUnsuccessfulInvalidData");
        boolean expResult = false;
        boolean result = example.addBicycle("", 0, 0.0, 0.0, .0f, .0f, 0);
        assertEquals(expResult, result);
    }

    /**
     * Test of addBicycle method, of class AdminController. Ensures bicycle is
     * not saved, since it already exists.
     */
    @Test
    public void testAddBicycleUnsuccessfulOperationNotPossible() {
        System.out.println("testAddBicycleUnsuccessfulOperationNotPossible");
        boolean expResult = false;
        String descr = "B050";
        when(mockedBicycleDB.getBicycle(descr)).thenThrow(new IllegalArgumentException());
        doThrow(new IllegalArgumentException()).when(mockedBicycleDB).addBicycle(any(Bicycle.class));
        boolean result = example.addBicycle(descr, 5, 25.0, 35.0, 1.1f, 0.5f, 26);
        assertEquals(expResult, result);
        verify(mockedBicycleDB, times(1)).getBicycle(any(String.class));
        verify(mockedBicycleDB, times(1)).addBicycle(any(Bicycle.class));
    }

    @Test
    void testDeleteBicycle() {
        String descr = "B007";
        assertEquals("", example.deleteBicycle(descr));

        Bicycle bicycle = new Bicycle(descr, 5, 25.0, 35.0, 1.1f, 0.5f, 26);

        when(mockedBicycleDB.getBicycle(descr)).thenReturn(bicycle);
        when(mockedBicycleDB.deleteBicycle(descr)).thenReturn(descr);
        assertEquals(descr, example.deleteBicycle(descr));

        when(mockedBicycleDB.getBicycle(descr)).thenThrow(new IllegalArgumentException());
        assertEquals("", example.deleteBicycle(descr));
    }

    @Test
    void testDeleteLockedBicycle() {
        String descr = "B007";
        assertEquals("", example.deleteBicycle(descr));

        Bicycle bicycle = new Bicycle(descr, 5, 0.0, 0.0, 1.1f, 0.5f, 26);

        when(mockedBicycleDB.getBicycle(descr)).thenReturn(bicycle);
        when(mockedBicycleDB.deleteBicycle(descr)).thenReturn(descr);
        assertEquals("", example.deleteBicycle(descr));

        when(mockedBicycleDB.getBicycle(descr)).thenThrow(new IllegalArgumentException());
        assertEquals("", example.deleteBicycle(descr));
    }

    @Test
    void testDeleteLockedBicycle2() {
        String descr = "B007";
        assertEquals("", example.deleteBicycle(descr));

        Bicycle bicycle = new Bicycle(descr, 5, 25.0, 0.0, 1.1f, 0.5f, 26);

        when(mockedBicycleDB.getBicycle(descr)).thenReturn(bicycle);
        when(mockedBicycleDB.deleteBicycle(descr)).thenReturn(descr);
        assertEquals("", example.deleteBicycle(descr));

        when(mockedBicycleDB.getBicycle(descr)).thenThrow(new IllegalArgumentException());
        assertEquals("", example.deleteBicycle(descr));
    }

    /**
     * Test of addEscooter method, of class AdminController. Ensures a escooter
     * is saved.
     */
    @Test
    public void testAddEscooter() {
        System.out.println("testAddEscooter");
        boolean expResult = true;
        when(mockedEscooterDB.getEscooter(any(String.class))).thenThrow(new IllegalArgumentException());
        doNothing().when(mockedEscooterDB).addEscooter(any(Escooter.class));
        boolean result = example.addEscooter("S049", 5, Escooter.Type.CITY, 25.0,
                35.0, 0.25f, 75, 1.1f, 0.5f, 250);
        assertEquals(expResult, result);
        verify(mockedEscooterDB, times(1)).getEscooter(any(String.class));
        verify(mockedEscooterDB, times(1)).addEscooter(any(Escooter.class));
    }

    /**
     * Test of addEscooter method, of class AdminController. Ensures escooter is
     * not saved, since it already exists.
     */
    @Test
    public void testAddEscooterUnsuccessfulAlreadyExists() {
        System.out.println("testAddEscooterUnsuccessfulAlreadyExists");
        boolean expResult = false;
        String descr = "S049";
        when(mockedEscooterDB.getEscooter(descr)).thenReturn(new Escooter());
        boolean result = example.addEscooter(descr, 5, Escooter.Type.CITY, 25.0,
                35.0, 0.25f, 75, 1.1f, 0.5f, 250);
        assertEquals(expResult, result);
        verify(mockedEscooterDB, times(1)).getEscooter(any(String.class));
        verify(mockedEscooterDB, never()).addEscooter(any(Escooter.class));
    }

    /**
     * Test of addEscooter method, of class AdminController. Ensures a escooter
     * is not saved, since data is invalid.
     */
    @Test
    public void testAddEscooterUnsuccessfulInvalidData() {
        System.out.println("testAddEscooterUnsuccessfulInvalidData");
        boolean expResult = false;
        boolean result = example.addEscooter("", 0, Escooter.Type.CITY, 0.0, 0.0,
                .0f, 0, .0f, .0f, 0);
        assertEquals(expResult, result);
    }

    /**
     * Test of addEscooter method, of class AdminController. Ensures escooter is
     * not saved, since it already exists.
     */
    @Test
    public void testAddEscooterUnsuccessfulOperationNotPossible() {
        System.out.println("testAddEscooterUnsuccessfulOperationNotPossible");
        boolean expResult = false;
        String descr = "S049";
        when(mockedEscooterDB.getEscooter(descr)).thenThrow(new IllegalArgumentException());
        doThrow(new IllegalArgumentException()).when(mockedEscooterDB).addEscooter(any(Escooter.class));
        boolean result = example.addEscooter(descr, 5, Escooter.Type.CITY, 25.0,
                35.0, 0.25f, 75, 1.1f, 0.5f, 250);
        assertEquals(expResult, result);
        verify(mockedEscooterDB, times(1)).getEscooter(any(String.class));
        verify(mockedEscooterDB, times(1)).addEscooter(any(Escooter.class));
    }

    @Test
    void testDeleteEscooter() {
        System.out.println("testDeleteEscooter");
        String descr = "S007";
        assertEquals("", example.deleteEscooter(descr));

        Escooter escooter = new Escooter(descr, 5, Escooter.Type.CITY, 25.0,
                35.0, 0.25f, 75, 1.1f, 0.5f, 250);

        when(mockedEscooterDB.getEscooter(descr)).thenReturn(escooter);
        when(mockedEscooterDB.deleteEscooter(descr)).thenReturn(descr);
        assertEquals(descr, example.deleteEscooter(descr));

        when(mockedEscooterDB.getEscooter(descr)).thenThrow(new IllegalArgumentException());
        assertEquals("", example.deleteEscooter(descr));
    }

    @Test
    void testDeleteLockedEscooter() {
        System.out.println("testDeleteLockedEscooter");
        String descr = "S007";
        assertEquals("", example.deleteEscooter(descr));

        Escooter escooter = new Escooter(descr, 5, Escooter.Type.CITY, 0.0,
                0.0, 0.25f, 75, 1.1f, 0.5f, 250);

        when(mockedEscooterDB.getEscooter(descr)).thenReturn(escooter);
        when(mockedEscooterDB.deleteEscooter(descr)).thenReturn(descr);
        assertEquals("", example.deleteEscooter(descr));

        when(mockedEscooterDB.getEscooter(descr)).thenThrow(new IllegalArgumentException());
        assertEquals("", example.deleteEscooter(descr));
    }

    @Test
    void testDeleteLockedEscooter2() {
        System.out.println("testDeleteLockedEscooter2");
        String descr = "S007";
        assertEquals("", example.deleteEscooter(descr));

        Escooter escooter = new Escooter(descr, 5, Escooter.Type.CITY, 20.0,
                0.0, 0.25f, 75, 1.1f, 0.5f, 250);

        when(mockedEscooterDB.getEscooter(descr)).thenReturn(escooter);
        when(mockedEscooterDB.deleteEscooter(descr)).thenReturn(descr);
        assertEquals("", example.deleteEscooter(descr));

        when(mockedEscooterDB.getEscooter(descr)).thenThrow(new IllegalArgumentException());
        assertEquals("", example.deleteEscooter(descr));
    }

    @Test
    void testGetAllVehicles() {
        System.out.println("testGetAllVehicles");
        int expResult = 2;
        List<Bicycle> bicycles = new ArrayList<>();
        List<Escooter> escooters = new ArrayList<>();
        bicycles.add(new Bicycle("B050", 5, 25.0, 35.0, 1.1f, 0.5f, 26));
        escooters.add(new Escooter("S049", 5, Escooter.Type.CITY, 25.0, 35.0, 0.25f, 75, 1.1f, 0.5f, 250));

        when(mockedBicycleDB.getAllBicycles()).thenReturn(bicycles);
        when(mockedEscooterDB.getAllEscooters()).thenReturn(escooters);

        int result = example.getAllVehicles().size();
        assertEquals(expResult, result);
        verify(mockedBicycleDB, times(1)).getAllBicycles();
        verify(mockedEscooterDB, times(1)).getAllEscooters();
    }

    @Test
    void testGetAllParks() {
        System.out.println("testGetAllParks");
        int expResult = 2;
        List<Park> parks = new ArrayList<>();
        parks.add(new Park("1", 5, -5, 200, "Porto", 10, 12, 16, 2));
        parks.add(new Park("2", 7, -7, 100, "Coimbra", 15, 11, 15, 3));

        when(mockedParkDB.getAllParks()).thenReturn(parks);

        int result = example.getAllParks().size();
        assertEquals(expResult, result);
        verify(mockedParkDB, times(1)).getAllParks();
    }

    /**
     * Test of addBicycles method, of class AdminController. Ensures bicycles
     * are saved.
     */
    @Test
    public void testAddBicycles() {
        System.out.println("testAddBicycles");
        boolean expResult = true;
        when(mockedBicycleDB.getBicycle(any(String.class))).thenThrow(new IllegalArgumentException());
        doNothing().when(mockedBicycleDB).addBicycles(anyList());
        boolean result = example.addBicycles(Arrays.asList("B201", "B202"), Arrays.asList(25, 22),
                Arrays.asList(2.0, 2.0), Arrays.asList(-2.0, -2.0), Arrays.asList(0.35f, 0.25f),
                Arrays.asList(1.10f, 2.20f), Arrays.asList(21, 26));
        assertEquals(expResult, result);
        verify(mockedBicycleDB, times(2)).getBicycle(any(String.class));
        verify(mockedBicycleDB, times(1)).addBicycles(anyList());
    }

    /**
     * Test of addBicycle method, of class AdminController. Ensures bicycles are
     * not saved, since one already exists.
     */
    @Test
    public void testAddBicyclesUnsuccessfulAlreadyExists() {
        System.out.println("testAddBicyclesUnsuccessfulAlreadyExists");
        boolean expResult = false;
        when(mockedBicycleDB.getBicycle("B201")).thenReturn(new Bicycle());
        boolean result = example.addBicycles(Arrays.asList("B201"), Arrays.asList(25),
                Arrays.asList(2.0), Arrays.asList(-2.0), Arrays.asList(0.35f),
                Arrays.asList(1.10f), Arrays.asList(21));
        assertEquals(expResult, result);
        verify(mockedBicycleDB, times(1)).getBicycle(any(String.class));
        verify(mockedBicycleDB, never()).addBicycles(anyList());
    }

    /**
     * Test of addBicycles method, of class AdminController. Ensures bicycles
     * are not saved, since data is invalid.
     */
    @Test
    public void testAddBicyclesUnsuccessfulInvalidData() {
        System.out.println("testAddBicyclesUnsuccessfulInvalidData");
        boolean expResult = false;
        boolean result = example.addBicycles(Arrays.asList(""), Arrays.asList(0),
                Arrays.asList(0.0), Arrays.asList(0.0), Arrays.asList(.0f),
                Arrays.asList(.0f), Arrays.asList(0));
        assertEquals(expResult, result);
    }

    /**
     * Test of addBicycle method, of class AdminController. Ensures bicycles are
     * not saved, since operation was unsuccessful in database.
     */
    @Test
    public void testAddBicyclesUnsuccessfulOperationNotPossible() {
        System.out.println("testAddBicyclesUnsuccessfulOperationNotPossible");
        boolean expResult = false;
        when(mockedBicycleDB.getBicycle("B205")).thenThrow(new IllegalArgumentException());
        doThrow(new IllegalArgumentException()).when(mockedBicycleDB).addBicycles(anyList());
        boolean result = example.addBicycles(Arrays.asList("B205"), Arrays.asList(25),
                Arrays.asList(2.0), Arrays.asList(-2.0), Arrays.asList(0.35f),
                Arrays.asList(1.10f), Arrays.asList(21));
        assertEquals(expResult, result);
        verify(mockedBicycleDB, times(1)).getBicycle(any(String.class));
        verify(mockedBicycleDB, times(1)).addBicycles(anyList());
    }

    /**
     * Test of addEscooters method, of class AdminController. Ensures escooters
     * are saved.
     */
    @Test
    public void testAddEscooters() {
        System.out.println("testAddEscooters");
        boolean expResult = true;
        when(mockedEscooterDB.getEscooter(any(String.class))).thenThrow(new IllegalArgumentException());
        doNothing().when(mockedEscooterDB).addEscooters(anyList());
        boolean result = example.addEscooters(Arrays.asList("S201", "S202"), Arrays.asList(25, 22),
                Arrays.asList(Escooter.Type.CITY, Escooter.Type.OFFROAD), Arrays.asList(2.0, 2.0),
                Arrays.asList(-2.0, -2.0), Arrays.asList(0.35f, 0.25f), Arrays.asList(100, 90),
                Arrays.asList(1.10f, 2.20f), Arrays.asList(0.3f, 0.7f), Arrays.asList(250f, 220f));
        assertEquals(expResult, result);
        verify(mockedEscooterDB, times(2)).getEscooter(any(String.class));
        verify(mockedEscooterDB, times(1)).addEscooters(anyList());
    }

    /**
     * Test of addEscooter method, of class AdminController. Ensures escooters
     * are not saved, since one already exists.
     */
    @Test
    public void testAddEscootersUnsuccessfulAlreadyExists() {
        System.out.println("testAddEscootersUnsuccessfulAlreadyExists");
        boolean expResult = false;
        when(mockedEscooterDB.getEscooter("S201")).thenReturn(new Escooter());
        boolean result = example.addEscooters(Arrays.asList("S201"), Arrays.asList(25),
                Arrays.asList(Escooter.Type.CITY), Arrays.asList(2.0),
                Arrays.asList(-2.0), Arrays.asList(0.35f), Arrays.asList(100),
                Arrays.asList(1.10f), Arrays.asList(0.3f), Arrays.asList(250f));
        assertEquals(expResult, result);
        verify(mockedEscooterDB, times(1)).getEscooter(any(String.class));
        verify(mockedEscooterDB, never()).addEscooters(anyList());
    }

    /**
     * Test of addEscooters method, of class AdminController. Ensures escooters
     * are not saved, since data is invalid.
     */
    @Test
    public void testAddEscootersUnsuccessfulInvalidData() {
        System.out.println("testAddEscootersUnsuccessfulInvalidData");
        boolean expResult = false;
        boolean result = example.addEscooters(Arrays.asList(""), Arrays.asList(0),
                Arrays.asList(Escooter.Type.CITY), Arrays.asList(0.0),
                Arrays.asList(0.0), Arrays.asList(.0f), Arrays.asList(0),
                Arrays.asList(.0f), Arrays.asList(.0f), Arrays.asList(.0f));
        assertEquals(expResult, result);
    }

    /**
     * Test of addEscooter method, of class AdminController. Ensures escooters
     * are not saved, since operation was unsuccessful in database.
     */
    @Test
    public void testAddEscootersUnsuccessfulOperationNotPossible() {
        System.out.println("testAddEscootersUnsuccessfulOperationNotPossible");
        boolean expResult = false;
        String descr = "S205";
        when(mockedEscooterDB.getEscooter(descr)).thenThrow(new IllegalArgumentException());
        doThrow(new IllegalArgumentException()).when(mockedEscooterDB).addEscooters(anyList());
        boolean result = example.addEscooters(Arrays.asList(descr), Arrays.asList(5),
                Arrays.asList(Escooter.Type.CITY), Arrays.asList(25.0),
                Arrays.asList(35.0), Arrays.asList(0.25f), Arrays.asList(75),
                Arrays.asList(1.1f), Arrays.asList(0.5f), Arrays.asList(250f));
        assertEquals(expResult, result);
        verify(mockedEscooterDB, times(1)).getEscooter(any(String.class));
        verify(mockedEscooterDB, times(1)).addEscooters(anyList());
    }

    /**
     * Test of addBicycles method, of class AdminController. Ensures bicycles
     * are saved.
     */
    @Test
    public void testAddBicyclesExistingPark() {
        System.out.println("testAddBicyclesExistingPark");
        boolean expResult = false;
        when(mockedBicycleDB.getBicycle(any(String.class))).thenThrow(new IllegalArgumentException());
        doNothing().when(mockedBicycleDB).addBicycles(anyList());
        when(mockedParkDB.getParkByCoordinates(any(Double.class), any(Double.class))).thenThrow(new IllegalArgumentException());
        doThrow(new IllegalArgumentException()).when(mockedParkDB).getParkByCoordinates(3.0, -3.0);
        example.addPark("1", 2.0, -2.0, 200, "Porto", 10, 12, 16, 2);
        boolean result = example.addBicycles(Arrays.asList("B205"), Arrays.asList(25),
                Arrays.asList(3.0), Arrays.asList(-3.0), Arrays.asList(0.35f),
                Arrays.asList(1.10f), Arrays.asList(21));
        assertEquals(expResult, result);
        verify(mockedBicycleDB, times(1)).getBicycle(any(String.class));
        verify(mockedParkDB, times(1)).getParkByCoordinates(any(Double.class), any(Double.class));
    }
    
    /**
     * Test of addEscooters method, of class AdminController. Ensures bicycles
     * are saved.
     */
    @Test
    public void testAddEscootersExistingPark() {
        System.out.println("testAddEscootersExistingPark");
        boolean expResult = false;
        when(mockedEscooterDB.getEscooter(any(String.class))).thenThrow(new IllegalArgumentException());
        doNothing().when(mockedEscooterDB).addEscooters(anyList());
        when(mockedParkDB.getParkByCoordinates(any(Double.class), any(Double.class))).thenThrow(new IllegalArgumentException());
        doThrow(new IllegalArgumentException()).when(mockedParkDB).getParkByCoordinates(3.0, -3.0);
        example.addPark("1", 2.0, -2.0, 200, "Porto", 10, 12, 16, 2);
        boolean result = example.addEscooters(Arrays.asList("S201"), Arrays.asList(25),
                Arrays.asList(Escooter.Type.CITY), Arrays.asList(3.0),
                Arrays.asList(-3.0), Arrays.asList(0.35f), Arrays.asList(100),
                Arrays.asList(1.10f), Arrays.asList(0.3f), Arrays.asList(250f));
        assertEquals(expResult, result);
        verify(mockedEscooterDB, times(1)).getEscooter(any(String.class));
        verify(mockedParkDB, times(1)).getParkByCoordinates(any(Double.class), any(Double.class));
    }
    
    @Test
    void testGetParkChargingReport() {
        System.out.println("testGetParkChargingReport");
        int expResult = 3;
        List<Escooter> escooters = new LinkedList<>();
        escooters.add(new Escooter("S049", 5, Escooter.Type.CITY, 2.0, -2.0, 0.25f, 75, 1.1f, 0.5f, 250, 0));
        escooters.add(new Escooter("S050", 6, Escooter.Type.CITY, 2.0, -2.0, 0.15f, 85, 1.3f, 0.7f, 220, 0));
        escooters.add(new Escooter("S051", 7, Escooter.Type.CITY, 2.0, -2.0, 0.25f, 75, 1.2f, 0.6f, 210, 0));
        Park park = new Park("1", 2, -2, 200, "Coimbra", 10, 12, 16, 2);        
        
        when(mockedEscooterDB.getAllNotFullEscootersFromPark(2, -2)).thenReturn(escooters);
        when(mockedParkDB.getPark("1")).thenReturn(park);
        
        int result = example.getParkChargingReport("1").size();
        assertEquals(expResult, result);
        verify(mockedEscooterDB, times(1)).getAllNotFullEscootersFromPark(2, -2);
    }
    
    @Test
    void testGetParkChargingReportCalc() {
        System.out.println("testGetParkChargingReportCalc");
        int expResult = 2727;
        Escooter escooter = new Escooter("S049", 5, Escooter.Type.CITY, 2.0, -2.0, 0.25f, 75, 1.1f, 0.5f, 250, 0);
        escooter.setTotalChargeTime(2727);
        int result = escooter.getTotalChargeTime();
        assertEquals(expResult, result);
    }
    
    @Test
    void testCompare() {
        System.out.println("testCompare");
        List<Escooter> escooters = new LinkedList<>();
        escooters.add(new Escooter("S049", 5, Escooter.Type.CITY, 2.0, -2.0, 0.95f, 75, 1.1f, 0.5f, 250, 0));
        escooters.add(new Escooter("S050", 6, Escooter.Type.CITY, 2.0, -2.0, 0.65f, 70, 1.3f, 0.7f, 220, 0));
        escooters.add(new Escooter("S051", 7, Escooter.Type.CITY, 2.0, -2.0, 0.35f, 65, 1.2f, 0.6f, 210, 0));
        Escooter expResult = escooters.get(0);
                
        //to test inner class
        
        Escooter result = escooters.get(0);
        assertEquals(expResult, result);
    }
}
