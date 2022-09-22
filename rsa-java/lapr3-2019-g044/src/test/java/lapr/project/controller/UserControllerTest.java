/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lapr.project.data.UserDB;
import lapr.project.model.Park;
import lapr.project.model.User;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 *
 */
public class UserControllerTest {

    @InjectMocks
    UserController uController;
    @Mock
    private UserDB userDb;

    private List<String> usernames;
    private List<String> emails;
    private List<Integer> heights;
    private List<Integer> weights;
    private List<Double> avgSpeeds;
    private List<String> cardNumbers;
    private List<String> genders;
    private List<String> passwords;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        uController = new UserController();
        uController.setUserDataBase(userDb);

        usernames = new LinkedList<>();
        emails = new LinkedList<>();
        heights = new LinkedList<>();
        weights = new LinkedList<>();
        avgSpeeds = new LinkedList<>();
        cardNumbers = new LinkedList<>();
        genders = new LinkedList<>();
        passwords = new LinkedList<>();

        usernames.add("joaomachado");
        emails.add("joao@gmail.com");
        heights.add(190);
        weights.add(60);
        avgSpeeds.add(Double.valueOf(10));
        cardNumbers.add("4544000019821545");
        genders.add("Male");
        passwords.add("12343255");

    }

    /**
     * Test of constructor, of class User.
     */
    @Test
    public void testUser() {
        System.out.println("Test User");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94, 5.5, "Masculino");
        User user = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94, 5.5, "Masculino");
        assertTrue(user.equals(instance));
    }

    /**
     * Test of setUserDataBase method, of class UserController.
     */
    @Test
    public void testSetUserDataBase() {
        System.out.println("setUserDataBase");
        UserDB userDataBase = new UserDB();
        UserController instance = new UserController();
        instance.setUserDataBase(userDataBase);

    }

    /**
     * Test of getUserByEmail method, of class UserController.
     */
    @Test
    public void testGetUserByEmail() throws SQLException {
        System.out.println("getUserByEmail");
        String email = "123456@isep.ipp.pt";

        User user = new User();
        user.setUserName("Machado");
        user.setEmail(email);
        user.setPassword("12345678");
        user.setCardNumber("4544000019821545");
        user.setHeight(186);
        user.setWeight(94);
        user.setPoints(20);
        user.setInitialFee(5);
        user.setAvgSpeed(5.5);

        when(userDb.getUserByEmail(email)).thenReturn(user);
        User expResult = user;
        User result = uController.getUserByEmail(email);
        assertEquals(expResult, result);
        verify(userDb).getUserByEmail(email);

        when(userDb.getUserByEmail(email)).thenThrow(IllegalArgumentException.class);
        expResult = null;
        result = uController.getUserByEmail(email);
        assertEquals(expResult, result);
    }

    @Test
    public void testNewUserShouldBeFalse() throws SQLException {

        UserDB udb = mock(UserDB.class);
        String email = "123456@isep.ipp.pt";
        User user = new User();
        uController = new UserController();
        uController.setUserDataBase(udb);

        when(udb.getUserByEmail(email)).thenReturn(null);

        boolean expResult = false;
        boolean result = uController.newUser("", "", "", "", 0, 0, 5.5, "");
        assertEquals(result, expResult);
    }

    /**
     * Test of newUser method, of class UserController.
     */
    @Test
    public void testNewUser() {
        System.out.println("newUser");

        UserDB udb = mock(UserDB.class);
        String email_1 = "123456@isep.ipp.pt";
        User user = new User();
        user.setEmail(email_1);
        user.setPassword("qwerty");
        uController = new UserController();
        uController.setUserDataBase(udb);

        when(udb.getUserByEmail(email_1)).thenReturn(null);

        boolean expResult = false;
        boolean result = uController.newUser("", "", "", "", 0, 0, 5.5, "");
        assertEquals(result, expResult);

        //Invalid email
        when(udb.getUserByEmail(email_1)).thenReturn(user);

        expResult = false;
        result = uController.newUser("", email_1, "", "", 0, 0, 5.5, "");
        assertEquals(result, expResult);
    }

    /**
     * Test of newUser method, of class UserController.
     */
    @Test
    public void testNewUser2() {
        System.out.println("newUser");

        String username = "Machado";
        String email = "123456@isep.ipp.pt";
        String cardNumber = "4544000019821545";
        int height = 186;
        int weight = 94;
        double avgSpeed = 5.5;
        String gender = "Masculino";

        UserDB udb = mock(UserDB.class);
        uController = new UserController();
        uController.setUserDataBase(udb);

        doThrow(new IllegalArgumentException("Illegal Test 1")).when(udb).getUserByEmail(any(String.class));
        doThrow(new IllegalArgumentException("Illegal Test 2")).when(udb).addUser(any(User.class));
        boolean result = uController.newUser(username, email, email, cardNumber, height, weight, avgSpeed, gender);
        assertEquals(result, false);
    }

    /**
     * Test of newUser method, of class UserController.
     */
    @Test
    public void testNewUser3() {
        System.out.println("newUser");

        String username = "Machado";
        String email = "123456@isep.ipp.pt";
        String cardNumber = "4544000019821545";
        int height = 186;
        int weight = 94;
        double avgSpeed = 5.5;
        String gender = "Masculino";

        UserDB udb = mock(UserDB.class);
        uController = new UserController();
        uController.setUserDataBase(udb);

        doThrow(new IllegalArgumentException("Illegal Test 1")).when(udb).getUserByEmail(any(String.class));

        boolean result = uController.newUser(username, email, email, cardNumber, height, weight, avgSpeed, gender);

        assertEquals(result, true);
    }

    /**
     * Test of newUser method, of class UserController.
     */
    @Test
    public void testNewUser4() {
        System.out.println("newUser");

        String username = "Machado";
        String email = "123456@isep.ipp.pt";
        String cardNumber = "4544000019821545";
        int height = 186;
        int weight = 94;
        double avgSpeed = 5.5;
        String gender = "Masculino";

        UserDB udb = mock(UserDB.class);
        uController = new UserController();
        uController.setUserDataBase(udb);

        boolean result = uController.newUser(username, email, email, cardNumber, height, weight, avgSpeed, gender);

        assertEquals(result, false);
    }

    /**
     * Test of validateUser method, of class UserController.
     */
    @Test
    public void testValidateUserTrue() throws SQLException {
        System.out.println("validateUserTrue");
        String email = "123456@isep.ipp.pt";

        UserDB udb = mock(UserDB.class);
        uController = new UserController();
        uController.setUserDataBase(udb);

        User user = new User();
        user.setUserName("Machado");
        user.setEmail(email);
        user.setPassword("12345678");
        user.setCardNumber("4544000019821545");
        user.setHeight(186);
        user.setWeight(94);
        user.setPoints(20);
        user.setInitialFee(1);
        user.setAvgSpeed(5.5);

        when(udb.getUserByEmail(email)).thenReturn(user);

        boolean expResult = true;
        boolean result = uController.validateUser(email);
        assertEquals(expResult, result);
        verify(udb).getUserByEmail(email);//verifica se a DB mock foi usada
    }

    /**
     * Test of validateUser method, of class UserController.
     */
    @Test
    public void testValidateUserFalse() throws SQLException {
        System.out.println("validateUserFalse");
        String email = "1111111@isep.ipp.pt";

        UserDB udb = mock(UserDB.class);
        uController = new UserController();
        uController.setUserDataBase(udb);

        when(udb.getUserByEmail(email)).thenReturn(null);

        boolean expResult = false;
        boolean result = uController.validateUser(email);
        assertEquals(expResult, result);
        verify(udb).getUserByEmail(email);//verifica se a DB mock foi usada
    }

    /**
     * Test of validateUser method, of class UserController.
     */
    @Test
    public void testRemoveUser() throws SQLException {
        System.out.println("removeteUserTrue");
        String email = "joasdasf@gmail.com";

        UserDB udb = mock(UserDB.class);
        uController = new UserController();
        uController.setUserDataBase(udb);

        User user = new User();
        user.setUserName("Machado");
        user.setEmail(email);
        user.setPassword("12345678");
        user.setCardNumber("4544000019821545");
        user.setHeight(186);
        user.setWeight(94);
        user.setPoints(20);
        user.setInitialFee(1);
        user.setAvgSpeed(5.5);

        when(udb.removeUser(email)).thenReturn(true);

        boolean expResult = false;
        boolean result = uController.removeUser(email);
        assertEquals(expResult, result);
        verify(udb).removeUser(email);//verifica se a DB mock foi usada

        when(udb.removeUser("asdasd@gmail.com")).thenReturn(false);

        expResult = true;
        result = uController.removeUser("asdasd@gmail.com");
        assertEquals(expResult, result);
        verify(udb).removeUser(email);//verifica se a DB mock foi usada

    }

    /**
     * Test of ValidatesendEmailTrue method, of class SendEmail.
     */
    @Test
    public void testValidatesendEmailTrue() {
        System.out.println("ValidatesendEmailTrue");
        String email = "joaocarlos845@hotmail.com";
        String subject = "Test subject";
        String body = "Test body";

        uController = new UserController();

        boolean result = uController.sendEmail(email, subject, body);

        boolean expectedResult = true;
        assertEquals(expectedResult, result);
    }

    /**
     * Test of ValidatesendEmailFalse method, of class SendEmail.
     */
    @Test
    public void testValidatesendEmailFalse() {
        System.out.println("ValidatesendEmailFalse");
        String email = "";
        String subject = "Test subject";
        String body = "Test body";

        uController = new UserController();

        boolean result = uController.sendEmail(email, subject, body);
        boolean expectedResult = false;
        assertEquals(expectedResult, result);
    }

    /**
     * Test of getNearestParks method, of class User.
     */
    @Test
    public void testGetNearestParks() {
        System.out.println("Nearest Parks by Location");
        List<Park> empty_list = new ArrayList<>();
        List<Park> lst_parks = new ArrayList<>();
        double lat = 40.77, lon = -73.988;
        int radius = 1000;

        lst_parks.add(new Park("1", 40.760, -73.984, 0, "Test1", 10, 10, 100, 4));
        lst_parks.add(new Park("1", 40.761, -73.985, 0, "Test2", 10, 10, 100, 4));
        lst_parks.add(new Park("1", 40.762, -73.986, 0, "Test3", 10, 10, 100, 4));
        lst_parks.add(new Park("1", 40.763, -73.987, 0, "Test4", 10, 10, 100, 4));

        UserDB udb = mock(UserDB.class);
        uController = new UserController();
        uController.setUserDataBase(udb);

        List<Integer> expResult = new ArrayList<>();
        expResult.add(782);
        expResult.add(905);

        when(udb.getAllParks()).thenReturn(lst_parks);

        List<Integer> result = uController.getNearestParks(empty_list, lat, lon, radius);

        assertArrayEquals(result.toArray(), expResult.toArray());

    }

    /**
     * Test of testGetNearestParksNull method, of class User.
     */
    @Test
    public void testGetNearestParksNull() {
        System.out.println("Nearest Parks by Location Null List");
        List<Park> null_list = null;
        List<Park> lst_parks = null;
        List<Park> expResult = null;
        double lat = 40.77, lon = -73.988;
        int radius = 1000;

        UserDB udb = mock(UserDB.class);
        uController = new UserController();
        uController.setUserDataBase(udb);

        when(udb.getAllParks()).thenReturn(lst_parks);

        List<Integer> result = uController.getNearestParks(null_list, lat, lon, radius);

        assertEquals(result, expResult);

    }

    /**
     * Test of validateDistance method, of class User.
     */
    @Test
    public void testValidateDistanceNegativeRadius() {
        System.out.println("Valid Distance Negative Radius");
        int distance = 2000;
        uController = new UserController();
        boolean expResult = false;
        boolean result = uController.validateDistance(distance, -1234);
        assertEquals(expResult, result);
    }

    /**
     * Test of testValidateDistanceWithValidRadius method, of class User.
     */
    @Test
    public void testValidateDistanceWithValidRadius() {
        System.out.println("Valid Distance Valid Radius");
        int distance = 2000;
        uController = new UserController();
        boolean expResult = true;
        boolean result = uController.validateDistance(distance, 3000);
        assertEquals(expResult, result);
    }

    /**
     * Test of testValidateDistanceWithValidRadius method, of class User.
     */
    @Test
    public void testValidateDistanceWithInvalidRadius() {
        System.out.println("Valid Distance Invalid Radius");
        int distance = 1000;
        uController = new UserController();
        boolean expResult = true;
        boolean result = uController.validateDistance(distance, -1000);
        assertEquals(expResult, result);
    }

    /**
     * Test of orderByDistance method, of class User.
     */
    @Test
    public void testOrderByDistance() {
        System.out.println("Order by Distance");

        List<Park> result = new ArrayList<>();
        List<Park> expResult = new ArrayList<>();

        Park test1 = new Park("1", 40.760, -73.984, 0, "Test1", 10, 10, 100, 4);
        Park test2 = new Park("1", 40.761, -73.985, 0, "Test2", 10, 10, 100, 4);
        Park test3 = new Park("1", 40.762, -73.986, 0, "Test3", 10, 10, 100, 4);
        Park test4 = new Park("1", 40.763, -73.987, 0, "Test4", 10, 10, 100, 4);

        expResult.add(test4);
        expResult.add(test3);
        expResult.add(test2);
        expResult.add(test1);

        result.add(test3);
        result.add(test2);
        result.add(test1);
        result.add(test4);

        UserController instance = new UserController();
        double lat = 40.77, lon = -73.988;
        assertTrue(instance.orderByDistance(result, lat, lon));

        assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of testOrderByDistanceNull method with null list, of class User.
     */
    @Test
    public void testOrderByDistanceNull() {
        System.out.println("Order by Distance Null List");

        boolean result;
        boolean expResult = false;

        List<Park> lst_park = null;

        uController = new UserController();
        double lat = 40.77, lon = -73.988;
        result = uController.orderByDistance(lst_park, lat, lon);

        assertEquals(result, expResult);
    }

    /**
     * Test of testGetUserByUserName method .
     */
    @Test
    public void testGetUserByUserName() {
        System.out.println("GetUserByUserName");
        User user = new User();

        when(userDb.getUserByUserName("joao9")).thenThrow(IllegalArgumentException.class);
        assertEquals(null, uController.getUserByUserName("joao9"));

        when(userDb.getUserByUserName("joao")).thenReturn(user);
        assertEquals(user, uController.getUserByUserName("joao"));
    }

    /**
     * Test of addUsers method, of class UserController. Ensures users are not
     * saved, since at least one already exists.
     */
    @Test
    public void testAddUsersUnsuccessfulAlreadyExists() {
        System.out.println("testAddUsersUnsuccessfulAlreadyExists");
        boolean expResult = false;
        when(userDb.getUserByEmail("joao@gmail.com")).thenReturn(new User());
        boolean result = uController.addUsers(usernames, emails, heights, weights, avgSpeeds, cardNumbers,genders,passwords);
        assertEquals(expResult, result);
        verify(userDb, times(1)).getUserByEmail(any(String.class));
        verify(userDb, never()).addUsers(anyList());
    }

    /**
     * Test of addusers method, of class UserCOntroller. Ensures users are not
     * saved, since data of at least one user is invalid.
     */
    @Test
    public void testAddUsersUnsuccessfulInvalidData() {
        System.out.println("testAddUsersUnsuccessfulInvalidData");
        boolean expResult = false;
        List<String> invalidEmails = new LinkedList<>();
        invalidEmails.add("joao");
        boolean result = uController.addUsers(usernames, invalidEmails, heights, weights, avgSpeeds, cardNumbers,genders,passwords);
        assertEquals(expResult, result);
    }

    @Test
    public void testSaveUsers() {
        System.out.println("SaveUsers");

        User user = new User();

        user.setUserName("joaomachado");
        user.setEmail("asd@asd.com");
        user.setHeight(190);
        user.setWeight(60);
        user.setAvgSpeed(10);
        user.setCardNumber("4544000019821545");
     user.setGender("Male");
          user.setPassword("1234567");
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userDb.getUserByEmail("joao@gmail.com")).thenReturn(null);
        doThrow(new IllegalArgumentException()).when(userDb).addUsers(users);

        assertTrue(uController.addUsers(usernames, emails, heights, weights, avgSpeeds, cardNumbers,genders,passwords));

        doThrow(new IllegalArgumentException()).when(userDb).addUsers(anyList());

        assertFalse(uController.addUsers(usernames, emails, heights, weights, avgSpeeds, cardNumbers,genders,passwords));

    }

    /**
     * Test of payAmount method, of class UserController.
     */
    @Test
    public void testPayAmount() {
        System.out.println("testPayAmount");
        String username = "a";

        boolean expResult = true;
        when(uController.getUserByUserName(username)).thenReturn(new User());
        boolean result = uController.payAmount(username, 1);
        assertEquals(expResult, result);

        expResult = false;
        result = uController.payAmount(username, 0);
        assertEquals(expResult, result);

        expResult = false;
        result = uController.payAmount(username, -1);
        assertEquals(expResult, result);

        expResult = false;
        when(uController.getUserByUserName(username)).thenReturn(null);
        result = uController.payAmount(username, 1);
        assertEquals(expResult, result);
    }
}
