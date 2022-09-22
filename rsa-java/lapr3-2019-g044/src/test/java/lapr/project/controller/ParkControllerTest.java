/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import lapr.project.data.ParkDB;
import lapr.project.model.Park;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 *
 */
public class ParkControllerTest {

    @InjectMocks
    private ParkController controller;
    @Mock
    private ParkDB mockedParkDb;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new ParkController();
        controller.setParkDb(mockedParkDb);

    }

    /**
     * Test of getDistanceFromUserToPark2 method, of class ParkController.
     */
    @Test
    public void testGetDistanceFromUserToPark1() {
        System.out.println("getDistanceFromUserToPark1");

        ParkDB pdb = mock(ParkDB.class);//Criação da classe mock
        controller.setParkDb(pdb);//set da database mock no controller para teste

        Park p1 = new Park();
        p1.setId("1");
        p1.setDescription("parque1");
        p1.setLatitude(37.24003);
        p1.setLongitude(40.134159);
        p1.setMaxNumberBicycles(50);
        p1.setMaxNumberEscooters(10);

        when(pdb.getPark("1")).thenReturn(p1);//quando o controller chamar a função da DB, usa a DB mock e retorna o resultado p1
        double expResult = 4275566.15;
        double result = controller.getDistanceFromUserToPark(10.0, 10.0, "1");
        assertEquals(expResult, result, 0.01);
        verify(pdb).getPark("1");//verifica se a DB mock foi usada
    }

    /**
     * Test of getDistanceFromUserToPark2 method, of class ParkController.
     */
    @Test
    public void testGetDistanceFromUserToPark2() {
        System.out.println("getDistanceFromUserToPark2");

        ParkDB pdb = mock(ParkDB.class);//Criação da classe mock
        controller.setParkDb(pdb);//set da database mock no controller para teste
        // Utils calc = mock (Utils.class);

        Park p2 = new Park();
        p2.setId("2");
        p2.setDescription("parque2");
        p2.setLatitude(2);
        p2.setLongitude(-2);
        p2.setMaxNumberBicycles(50);
        p2.setMaxNumberEscooters(10);

        when(pdb.getPark("2")).thenThrow(new IllegalArgumentException());//quando o controller chamar a função da DB, usa a DB mock e retorna o resultado p1
        double expResult = 0.0;
        double result = controller.getDistanceFromUserToPark(1.0, 1.0, "2");
        assertEquals(expResult, result);
        verify(pdb).getPark("2");//verifica se a DB mock foi usada

    }

    /**
     * Test of getParkByCoordinates method, of class ParkController.
     */
    @Test
    public void testGetParkByCoordinates() {
        System.out.println("getParkByCoordinates");
        double lat = 2;
        double longti = -2.0;

        ParkDB udb = mock(ParkDB.class);

        controller.setParkDb(udb);

        Park p2 = new Park();
        p2.setId("2");
        p2.setDescription("parque2");
        p2.setLatitude(2);
        p2.setLongitude(-2);
        p2.setMaxNumberBicycles(50);
        p2.setMaxNumberEscooters(10);

        when(udb.getParkByCoordinates(lat, longti)).thenReturn(p2);

        Park expResult = p2;
        Park result = controller.getParkByCoordinates(lat, longti);
        assertEquals(expResult, result);
        verify(udb).getParkByCoordinates(lat, longti);
    }

    /**
     * Test of getDistanceFromUserToPark2 method, of class ParkController.
     */
    @Test
    public void testGetDistanceFromUserToPark3() {
        System.out.println("getDistanceFromUserToPark3");

        ParkDB pdb = mock(ParkDB.class);//Criação da classe mock
        controller = new ParkController();//criação do controller em teste
        controller.setParkDb(pdb);//set da database mock no controller para teste

        when(pdb.getPark("1")).thenReturn(null);//quando o controller chamar a função da DB, usa a DB mock e retorna o resultado p1
        double expResult = 0.0;
        double result = controller.getDistanceFromUserToPark(10.0, 10.0, "1");
        assertEquals(expResult, result, 0.01);
        verify(pdb).getPark("1");//verifica se a DB mock foi usada
    }
    
    @Test
    public void testGetParkByID() {
        System.out.println("GetParkByID");

        
        when(mockedParkDb.getPark("12")).thenReturn(null);
        
        assertEquals(null, controller.getParkById("12"));
    }
}
