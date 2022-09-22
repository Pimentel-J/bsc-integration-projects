/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.util.LinkedList;
import java.util.List;
import lapr.project.data.BicycleDB;
import lapr.project.data.EscooterDB;
import lapr.project.data.ParkDB;
import lapr.project.data.TripDB;
import lapr.project.data.UserDB;
import lapr.project.model.Bicycle;
import lapr.project.model.Escooter;
import lapr.project.model.POI;
import lapr.project.model.Park;
import lapr.project.model.Trip;
import lapr.project.model.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

/**
 *
 *
 */
public class TripControllerTest {

    @InjectMocks
    private TripController example;
    @Mock
    private ParkDB mockedParkDB;
    @Mock
    private BicycleDB mockedBicycleDB;
    @Mock
    private EscooterDB mockedEscooterDB;
    @Mock
    private UserDB mockedUserDB;
    @Mock
    private TripDB mockedTripDB;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        example = new TripController();
        example.setParkDB(mockedParkDB);
        example.setBicycleDB(mockedBicycleDB);
        example.setEscooterDB(mockedEscooterDB);
        example.setUserDB(mockedUserDB);
        example.setTripDB(mockedTripDB);
    }

    /**
     * Test of testUnlockBicycle method, of class TripController.
     */
    @Test
    public void testUnlockBicycle() {
        System.out.println("UnlockBicycle");
        String userName = "Paulo";
        String descr = "B060";
        double lat = -1;
        double lon = -1;

        User user = new User();
        Bicycle bike = new Bicycle();
        Park park = new Park();
        park.setId("1");
        Trip trip = new Trip(0, descr, userName, "1", "", "2019-12-27 15:05:00", "", 0,0, 0);

        when(mockedUserDB.getUserByUserName(userName)).thenReturn(null);
        assertEquals(null, example.unlockBicycle(descr, userName, lat, lon));

        when(mockedUserDB.getUserByUserName(userName)).thenReturn(user);
        when(mockedParkDB.getParkByCoordinates(lat, lon)).thenReturn(null);
        assertEquals(null, example.unlockBicycle(descr, userName, lat, lon));

        when(mockedUserDB.getUserByUserName(userName)).thenReturn(user);
        when(mockedParkDB.getParkByCoordinates(lat, lon)).thenReturn(park);
        when(mockedBicycleDB.getBicycle(descr)).thenReturn(null);
        assertEquals(null, example.unlockBicycle(descr, userName, lat, lon));

        when(mockedUserDB.getUserByUserName(userName)).thenReturn(user);
        when(mockedParkDB.getParkByCoordinates(lat, lon)).thenReturn(park);
        when(mockedBicycleDB.getBicycle(descr)).thenReturn(bike);

        when(mockedTripDB.getOnGoingTrips(userName)).thenReturn(trip);
        assertEquals(null, example.unlockBicycle(descr, userName, lat, lon));

        when(mockedTripDB.getOnGoingTrips(userName)).thenReturn(null);

        Trip result = example.unlockBicycle(descr, userName, lat, lon);
        result.setStartTime("2019-12-27 15:05:00");
        assertEquals(trip, result);

        when(mockedBicycleDB.updateBicycle(any(Bicycle.class))).thenThrow(new IllegalArgumentException());
        assertEquals(example.unlockBicycle(descr, userName, lat, lon), null);

    }

    /**
     * Test of testUnlockBicycle method, of class TripController.
     */
    @Test
    public void testUnlockEscooter() {
        System.out.println("unlockEscooter");
        String userName = "Paulo";
        String descr = "S060";
        double lat = -1;
        double lon = -1;

        User user = new User();
        Escooter eScooter = new Escooter();
        Park park = new Park();
        park.setId("1");
        Trip trip = new Trip(0, descr, userName, "1", "", "2019-12-27 15:05:00", "", 0,0, 0);

        when(mockedUserDB.getUserByUserName(userName)).thenReturn(null);
        assertEquals(null, example.unlockEscooter(descr, userName, lat, lon));

        when(mockedUserDB.getUserByUserName(userName)).thenReturn(user);
        when(mockedParkDB.getParkByCoordinates(lat, lon)).thenReturn(null);
        assertEquals(null, example.unlockEscooter(descr, userName, lat, lon));

        when(mockedUserDB.getUserByUserName(userName)).thenReturn(user);
        when(mockedParkDB.getParkByCoordinates(lat, lon)).thenReturn(park);
        when(mockedEscooterDB.getEscooter(descr)).thenReturn(null);
        assertEquals(null, example.unlockEscooter(descr, userName, lat, lon));

        when(mockedUserDB.getUserByUserName(userName)).thenReturn(user);
        when(mockedParkDB.getParkByCoordinates(lat, lon)).thenReturn(park);
        when(mockedEscooterDB.getEscooter(descr)).thenReturn(eScooter);

        when(mockedTripDB.getOnGoingTrips(userName)).thenReturn(trip);
        assertEquals(null, example.unlockEscooter(descr, userName, lat, lon));

        when(mockedTripDB.getOnGoingTrips(userName)).thenReturn(null);

        Trip result = example.unlockEscooter(descr, userName, lat, lon);
        result.setStartTime("2019-12-27 15:05:00");
        assertEquals(trip, result);

        when(mockedEscooterDB.updateEscooter(any(Escooter.class))).thenThrow(new IllegalArgumentException());
        assertEquals(example.unlockEscooter(descr, userName, lat, lon), null);

    }

    /**
     * Test of energyRequiredToTravelAtoB method, of class TripController.
     */
    @Test
    public void testEnergyRequiredToTravelAtoB() {
        System.out.println("energyRequiredToTravelAtoB");
        double latA = 40.76;
        double longA = -73.984;
        double latB = 40.77;
        double longB = -74.1;
        //distance 9832 m
        String descr = "S050";
        Escooter escooter = new Escooter();
        escooter.setDescr(descr);
        escooter.setMaxBattery(3);
        escooter.setActualBattery(75);
        escooter.setMotor(0.350f);
        Trip trip = new Trip();
        trip.setVehicleDescription(descr);
        
        when(mockedTripDB.getTrip(any(String.class))).thenReturn(null);
        assertEquals(-1, example.energyRequiredToTravelAtoB(latA, longA, latB, longB, descr));

        when(mockedTripDB.getOnGoingTrips(descr)).thenReturn(trip);
        when(mockedEscooterDB.getEscooter(descr)).thenReturn(escooter);
        double expResult = 625.67;
        double result = example.energyRequiredToTravelAtoB(latA, longA, latB, longB, descr);
        assertEquals(expResult, result, 0.1);

        }

    /**
     * Test of getActiveTrip method, of class TripController.
     */
    @Test
    public void testGetActiveTrip() {
        System.out.println("testGetActiveTrip");
        String username = "aaa";
        int tripId = 1;
        Trip trip = new Trip();
        trip.setTripId(tripId);

        when(mockedTripDB.getOnGoingTrips(username)).thenReturn(trip);
        Trip result = example.getActiveTrip(username);
        assertEquals(tripId, result.getTripId());

        when(mockedTripDB.getOnGoingTrips(username)).thenThrow(IllegalArgumentException.class);
        result = example.getActiveTrip(username);
        assertEquals(null, result);
    }

    /**
     * Test of getLoanedVehicle method, of class TripController.
     */
    @Test
    public void testGetLoanedVehicle() {
        System.out.println("testGetLoanedVehicle");
        String username = "aaa";
        int tripId = 1;
        Trip trip = new Trip();
        trip.setUserName(username);
        String expResult = "vehicle";
        trip.setVehicleDescription(expResult);

        when(example.getActiveTrip(username)).thenReturn(trip);
        String result = example.getLoanedVehicle(username);
        assertEquals(expResult, result);

        when(example.getActiveTrip(username)).thenReturn(null);
        result = example.getLoanedVehicle(username);
        assertEquals(null, result);
    }

    @Test
    public void testLockBicycle() {
        System.out.println("LockBicycle");
        
        Bicycle bike = new Bicycle();
        Trip trip = new Trip();
        User user = new User();
        Park park = new Park();
        park.setElevation(0);
        trip.setStartTime("2019-12-22 12:30:00");
        trip.setEndTime("2019-12-22 14:30:00");
        trip.setStartParkId("12");
        user.setEmail("asd@asd.com");
        user.setUserName("joao");
        bike.setParkLat(-1);
        assertEquals(null, example.lockBicycle(bike, park, user));
        bike.setParkLat(0);
        bike.setParkLong(-1);
        assertEquals(null, example.lockBicycle(bike, park, user));
        bike.setParkLong(0);
        
        when(mockedTripDB.getOnGoingTrips("joao")).thenReturn(null);
        assertEquals(null, example.lockBicycle(bike, park, user));
        
        when(mockedTripDB.getOnGoingTrips("joao")).thenReturn(trip);
        doNothing().when(mockedTripDB).updateTrip(any(Trip.class));
        when(mockedBicycleDB.updateBicycle(any(Bicycle.class))).thenReturn(true);
        Park startPark = new Park();
        startPark.setElevation(50);
        when(mockedParkDB.getPark("12")).thenReturn(startPark);
        doNothing().when(mockedUserDB).updateUser(any(User.class));
        assertEquals(trip, example.lockBicycle(bike, park, user));
        
        when(mockedTripDB.getOnGoingTrips("joao")).thenThrow(new IllegalArgumentException());
        assertEquals(null, example.lockBicycle(bike, park, user));
  
    }
    @Test
    public void testLockEscooter() {
        System.out.println("LockEscooter");
        
        Escooter escooter = new Escooter();
        Trip trip = new Trip();
        User user = new User();
        Park park = new Park();
        park.setElevation(0);
        trip.setStartTime("2019-12-22 12:30:00");
        trip.setEndTime("2019-12-22 14:30:00");
        trip.setStartParkId("12");
        user.setEmail("asd@asd.com");
        user.setUserName("joao");
        escooter.setParkLat(-1);
        assertEquals(null, example.lockEscooter(escooter, park, user));
        escooter.setParkLat(0);
        escooter.setParkLong(-1);
        assertEquals(null, example.lockEscooter(escooter, park, user));
        escooter.setParkLong(0);
        
        when(mockedTripDB.getOnGoingTrips("joao")).thenReturn(null);
        assertEquals(null, example.lockEscooter(escooter, park, user));
        
        when(mockedTripDB.getOnGoingTrips("joao")).thenReturn(trip);
        doNothing().when(mockedTripDB).updateTrip(any(Trip.class));
        when(mockedEscooterDB.updateEscooter(any(Escooter.class))).thenReturn(true);
        Park startPark = new Park();
        startPark.setElevation(50);
        when(mockedParkDB.getPark("12")).thenReturn(startPark);
        doNothing().when(mockedUserDB).updateUser(any(User.class));
        assertEquals(trip, example.lockEscooter(escooter, park, user));
        
        when(mockedTripDB.getOnGoingTrips("joao")).thenThrow(new IllegalArgumentException());
        assertEquals(null, example.lockEscooter(escooter, park, user));
  
    }
    
    /**
     * Test getOnGoingTrips of class TripController.
     */
    @Test
    public void testGetOngoingTrips() {
        System.out.println("testGetOngoingTrips");
        List<Trip> trips = new LinkedList<>();
        trips.add(new Trip());
        when(mockedTripDB.getOnGoingTrips()).thenReturn(trips);
        assertEquals(trips, example.getOngoingTrips());
    }
    
    /**
     * Test GetTripWithoutInvoice of class TripController.
     */
    @Test
    public void testGetTripWithoutInvoice() {
        System.out.println("GetTripWithoutInvoice");
        
        when(mockedTripDB.getTrips("machado",0)).thenReturn(null);
        assertEquals(null, example.getTripsWithoutInvoice("machado"));
    }
}
