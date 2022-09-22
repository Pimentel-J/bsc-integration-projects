/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.util.ArrayList;
import java.util.List;
import lapr.project.data.BicycleDB;
import lapr.project.data.EscooterDB;
import lapr.project.model.Bicycle;
import lapr.project.model.Escooter;
import lapr.project.model.Escooter.Type;
import lapr.project.model.Park;
import lapr.project.model.Vehicle;
import lapr.project.utils.Utils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 *
 */
public class VehicleControllerTest {

    @InjectMocks
    private VehicleController example;
    @Mock
    private BicycleDB mockedBicycleDB;
    @Mock
    private EscooterDB mockedEscooterDB;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        example = new VehicleController();
        example.setBicycleDB(mockedBicycleDB);
        example.setEscooterDB(mockedEscooterDB);

    }

    @Test
    void testGetAllBikes() {
        System.out.println("GetAllBikes");

        List<Bicycle> result;
        List<Bicycle> allBicycles = new ArrayList<>();
        List<Bicycle> expResult = new ArrayList<>();

        Park p1 = new Park("1", 2, -2, 0, "Test1", 10, 10, 100, 4);
        Bicycle b1 = new Bicycle("B050", 15, 2, -2, 1.2f, 0.5f, 18);
        Bicycle b2 = new Bicycle("B051", 12, 21.001, 31.001, 2.2f, 0.7f, 21);
        Bicycle b3 = new Bicycle("B052", 15, 2, -2, 1.2f, 0.5f, 23);
        Bicycle b4 = new Bicycle("B053", 12, 2, -2, 2.2f, 0.7f, 26);
        Bicycle b5 = new Bicycle("B054", 12, 2, 31.001, 2.2f, 0.7f, 18);
        Bicycle b6 = new Bicycle("B055", 12, 21.001, -2, 2.2f, 0.7f, 15);
        allBicycles.add(b1);
        allBicycles.add(b2);
        allBicycles.add(b3);
        allBicycles.add(b4);
        allBicycles.add(b5);
        allBicycles.add(b6);
        expResult.add(b1);
        expResult.add(b3);
        expResult.add(b4);

        when(mockedBicycleDB.getAllBicycles()).thenReturn(allBicycles);

        result = example.getAllBikes(p1);

        assertEquals(result, expResult);

    }

    @Test
    void testGetAllEscooters() {
        System.out.println("getAllEscooters");

        List<Escooter> result;
        List<Escooter> allEscooters = new ArrayList<>();
        List<Escooter> expResult = new ArrayList<>();

        Park p1 = new Park("1", 2, -2, 0, "Test1", 10, 10, 100, 4);
        Escooter e1 = new Escooter("S050", 25, Escooter.Type.CITY, 25.001, 35.001, 0.15f, 80, 1.10f, 0.3f, 150);
        Escooter e2 = new Escooter("S051", 22, Escooter.Type.OFFROAD, 27.001, 37.001, 0.12f, 90, 2.20f, 0.7f, 250);
        Escooter e3 = new Escooter("S052", 25, Escooter.Type.CITY, 2, -2, 0.15f, 75, 1.10f, 0.3f, 350);
        Escooter e4 = new Escooter("S053", 22, Escooter.Type.OFFROAD, 2, -2, 0.12f, 65, 2.20f, 0.7f, 200);
        Escooter e5 = new Escooter("S054", 22, Escooter.Type.OFFROAD, 2, 37.001, 0.12f, 40, 2.20f, 0.7f, 300);
        Escooter e6 = new Escooter("S055", 22, Escooter.Type.OFFROAD, 27.001, -2, 0.12f, 45, 2.20f, 0.7f, 190);

        allEscooters.add(e1);
        allEscooters.add(e2);
        allEscooters.add(e3);
        allEscooters.add(e4);
        allEscooters.add(e5);
        allEscooters.add(e6);
        expResult.add(e3);
        expResult.add(e4);

        when(mockedEscooterDB.getAllEscooters()).thenReturn(allEscooters);

        result = example.getAllEscooters(p1);

        assertEquals(result, expResult);

    }
  
    @Test
    void testGetBikesDesc() {
        System.out.println("GetBikesDesc");
        Bicycle bike = new Bicycle();

        when(mockedBicycleDB.getBicycle("B010")).thenReturn(null);
        assertEquals(null, example.getBikeByDesc("B010"));
        when(mockedBicycleDB.getBicycle("B010")).thenReturn(bike);
        assertEquals(bike, example.getBikeByDesc("B010"));
    }
    
    @Test
    void testUpdateVehicle() {
        System.out.println("UpdateVehicle");
        
        assertFalse(example.updateVehicle("S010", 1, -1, -1, 2, 3, Utils.NO_TYPE, 
                1.5f, 75, 14, 3.5f));
        assertFalse(example.updateVehicle("S010", 1, -1, -1, 2, 3, Utils.NO_TYPE, 
                Utils.NO_MAX_BATTERY, 75, 14, 3.5f));
        assertFalse(example.updateVehicle("S010", 1, -1, -1, 2, 3, Utils.NO_TYPE, 
                Utils.NO_MAX_BATTERY, Utils.NO_ACT_BATTERY, 14, 3.5f));
        when(mockedBicycleDB.updateBicycle(new Bicycle("S010", 1, -1, -1, 2, 3, 14))).thenReturn(true);
        assertTrue(example.updateVehicle("S010", 1, -1, -1, 2, 3, Utils.NO_TYPE, 
                Utils.NO_MAX_BATTERY, Utils.NO_ACT_BATTERY, 14, Utils.NO_MOTOR));
        
        assertFalse(example.updateVehicle("E010", 1, -1, -1, 2, 3, Utils.NO_TYPE, 1.5f, 75, 14, 0));
        assertFalse(example.updateVehicle("E010", 1, -1, -1, 2, 3, Type.OFFROAD, -1, 75, 14, 0));
        assertFalse(example.updateVehicle("E010", 1, -1, -1, 2, 3, Type.OFFROAD, 1.5f, -10, 14, 0));
        assertFalse(example.updateVehicle("E010", 1, -1, -1, 2, 3, Type.OFFROAD, 1.5f, 75, 14, -3.5f));
        assertFalse(example.updateVehicle("E010", 1, -1, -1, 2, 3, Type.OFFROAD, 1.5f, 75, 14, 3.5f));
        
        when(mockedEscooterDB.updateEscooter(new Escooter("E010", 1, Type.OFFROAD,
                -1, -1, 1.5f, 75, 2, 3, 3.5f))).thenReturn(true);
        assertTrue(example.updateVehicle("E010", 1, -1, -1, 2, 3, Type.OFFROAD, 
                1.5f, 0, Utils.NO_WHEEL_SIZE, 3.5f));
        when(mockedEscooterDB.updateEscooter(new Escooter("E010", 1, Type.OFFROAD,
                -1, -1, 1.5f, 75, 2, 3, 3.5f))).thenReturn(true);
        assertTrue(example.updateVehicle("E010", 1, -1, -1, 2, 3, Type.OFFROAD, 
                1.5f, 0, Utils.NO_WHEEL_SIZE, 3.5f));
    }
    
    /**
     * Test of getVehicle method, of class VehicleController.
     */
    @Test
    public void testGetVehicleReturnsBicycle() {
        System.out.println("testGetVehicleReturnsBicycle");
        Bicycle vehicle = new Bicycle();
        String description = "PT";
        boolean expResult = true;
        when(mockedBicycleDB.getBicycle(description)).thenReturn(vehicle);
        Vehicle result = example.getVehicle(description);
        assertEquals(expResult, (result instanceof Bicycle));
        verify(mockedBicycleDB, times(1)).getBicycle(any(String.class));
        verify(mockedEscooterDB, never()).getEscooter(any(String.class));
    }
    
    /**
     * Test of getVehicle method, of class VehicleController.
     */
    @Test
    public void testGetVehicleReturnsEscooter() {
        System.out.println("testGetVehicleReturnsEscooter");
        Escooter vehicle = new Escooter();
        String description = "ePT";
        boolean expResult = true;
        when(mockedBicycleDB.getBicycle(description)).thenThrow(IllegalArgumentException.class);
        when(mockedEscooterDB.getEscooter(description)).thenReturn(vehicle);
        Vehicle result = example.getVehicle(description);
        assertEquals(expResult, (result instanceof Escooter));
        verify(mockedBicycleDB, times(1)).getBicycle(any(String.class));
        verify(mockedEscooterDB, times(1)).getEscooter(any(String.class));
    }
    
    /**
     * Test of getVehicle method, of class VehicleController.
     */
    @Test
    public void testGetVehicleReturnsNull() {
        System.out.println("testGetVehicleReturnsEscooter");
        String description = "aaa";
        Vehicle expResult = null;
        when(mockedBicycleDB.getBicycle(description)).thenThrow(IllegalArgumentException.class);
        when(mockedEscooterDB.getEscooter(description)).thenThrow(IllegalArgumentException.class);
        Vehicle result = example.getVehicle(description);
        assertEquals(expResult, result);
        verify(mockedBicycleDB, times(1)).getBicycle(any(String.class));
        verify(mockedEscooterDB, times(1)).getEscooter(any(String.class));
    }
    
    /**
     * Test of getNumberOfBikesFromPark method, of class VehicleController.
     */
    @Test
    public void testGetNumberOfBikesFromPark() {
        System.out.println("testGetNumberOfBikesFromPark");
        Park park = new Park();
        ArrayList<Bicycle> lst = new ArrayList<>();
        lst.add(new Bicycle());
        int expResult = 1;
        when(example.getAllBikes(park)).thenReturn(lst);
        int result = example.getNumberOfBikesFromPark(park);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getNumberOfEscootersFromPark method, of class VehicleController.
     */
    @Test
    public void testGetNumberOfEscootersFromPark() {
        System.out.println("testGetNumberOfEscootersFromPark");
        Park park = new Park();
        ArrayList<Escooter> lst = new ArrayList<>();
        lst.add(new Escooter());
        int expResult = 1;
        when(example.getAllEscooters(park)).thenReturn(lst);
        int result = example.getNumberOfEscootersFromPark(park);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBikesByDesc() {
        System.out.println("GetBikesByDesc");
        
        when(mockedBicycleDB.getBicycle("12")).thenThrow(new IllegalArgumentException());
        assertEquals(null, example.getBikeByDesc("12"));
    }
    
    @Test
    public void testGetEscooterByDesc() {
        System.out.println("GetEscooterByDesc");
        
        Escooter escooter = new Escooter();
        
        when(mockedEscooterDB.getEscooter("12")).thenReturn(escooter);
        assertEquals(escooter, example.getEscooterByDesc("12"));
        
        when(mockedEscooterDB.getEscooter("12")).thenThrow(new IllegalArgumentException());
        assertEquals(null, example.getEscooterByDesc("12"));
    }
}
