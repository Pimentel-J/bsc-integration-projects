/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 *
 */
public class UserTest {

    public UserTest() {
    }

    /**
     * Test of getUsername method, of class User.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        String expResult = "Machado";
        String result = instance.getUserName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEmail method, of class User.
     */
    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        String expResult = "machado@gmail.com";
        String result = instance.getEmail();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPassword method, of class User.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        String expResult = "123456";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCardNumber method, of class User.
     */
    @Test
    public void testGetCardNumber() {
        System.out.println("getCardNumber");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        String expResult = "4544000019821545";
        String result = instance.getCardNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHeight method, of class User.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        double expResult = 186;
        double result = instance.getHeight();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHeight method, of class User.
     */
    @Test
    public void testeGetGender() {
        System.out.println("getGender");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        String expResult = "Masculino";
        String result = instance.getGender();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWeight method, of class User.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        double expResult = 94;
        double result = instance.getWeight();
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class User.
     */
    @Test
    public void testhashCode() {
        System.out.println("hashCode");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        int expResult = -67709116;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPoints method, of class User.
     */
    @Test
    public void testGetPoints() {
        System.out.println("getPoints");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        long expResult = 0;
        long result = instance.getPoints();
        assertEquals(expResult, result);
    }

    /**
     * Test of getInitialCost method, of class User.
     */
    @Test
    public void testGetInitialCost() {
        System.out.println("getInitialCost");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        double expResult = 0.5;
        double result = instance.getInitialFee();
        assertEquals(expResult, result, 0.5);
    }

    /**
     * Test of getInitialCost method, of class User.
     */
    @Test
    public void testGetAvgSpeed() {
        System.out.println("getAvgSpeed");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        double expResult = 5.5;
        double result = instance.getAvgSpeed();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsDifferentWeight() {
        System.out.println("Test Different Weight");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94, 5.5,"Masculino");

        User user = new User();
        user.setUserName("Machado");
        user.setEmail("machado@gmail.co");
        user.setPassword("123456");
        user.setCardNumber("4544000019821545");
        user.setHeight(186);
        user.setWeight(90);
        user.setPoints(20);
        user.setInitialFee(5);
        user.setAvgSpeed(5.5);
        assertTrue(!user.equals(instance));
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsDifferentHeight() {
        System.out.println("Test Different Height");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");

        User user = new User();
        user.setUserName("Machado");
        user.setEmail("machado@gmail.co");
        user.setPassword("123456");
        user.setCardNumber("4544000019821545");
        user.setHeight(190);
        user.setWeight(90);
        user.setPoints(20);
        user.setInitialFee(5);
        user.setAvgSpeed(5.5);
        assertTrue(!user.equals(instance));
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsDifferentAvgSpeed() {
        System.out.println("Test Different Avg Speed");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");

        User user = new User();
        user.setUserName("Machado");
        user.setEmail("machado@gmail.co");
        user.setPassword("123456");
        user.setCardNumber("4544000019821545");
        user.setHeight(186);
        user.setWeight(94);
        user.setPoints(20);
        user.setInitialFee(5);
        user.setAvgSpeed(6.5);
        assertTrue(!user.equals(instance));
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsDifferentIdUser() {
        System.out.println("Test Different idUser");
        User instance = new User();
        instance.setUserId(0);
        long expResult = 1;
        long result = instance.getUserId();
        assertNotEquals(expResult, result);
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsDifferentUsername() {
        System.out.println("Test Different Username");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        User user = new User("Marta", "matilde@hotmail.com", "qwerty", "4544000019821545", 165, 55,5.5, "Feminino");
        assertTrue(!user.equals(instance));
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testSameUser() {
        System.out.println("Test Same Username");
        User instance = new User("machado@gmail.com", "123456");
        User user = new User("machado@gmail.com", "123456");
        assertTrue(user.equals(instance));
    }

    /**
     * Test of hashCode method, of class User.
     */
    @Test
    public void testAddPoints() {
        System.out.println("addPoitns");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        int expResult = 10;
        instance.addPoints(expResult);
        assertEquals(expResult, instance.getPoints());
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsDifferentName() {
        System.out.println("Test Different Name");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");

        User user = new User();
        user.setUserName("joao");
        user.setEmail("machado@gmail.co");
        user.setPassword("123456");
        user.setCardNumber("4544000019821545");
        user.setHeight(186);
        user.setWeight(94);
        user.setPoints(1);
        user.setInitialFee(5);
        user.setAvgSpeed(5.5);
        assertTrue(!user.equals(instance));
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsDifferentGender2() {
        System.out.println("Test Different Gender");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");

        User user = new User();
        user.setUserName("joao");
        user.setEmail("machado@gmail.co");
        user.setPassword("123456");
        user.setCardNumber("4544000019821545");
        user.setHeight(186);
        user.setWeight(93);
        user.setPoints(1);
        user.setInitialFee(5);
        user.setAvgSpeed(5.5);
        user.setGender("fem");
        assertTrue(!user.equals(instance));
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsDifferentEmail() {
        System.out.println("Test Different Email");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        User user = new User("Machado", "aaaa@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        assertTrue(!user.equals(instance));
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsDifferentPoints() {
        System.out.println("Test Different Points");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        User user = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        user.setPoints(10);
        assertTrue(!user.equals(instance));
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsDifferentInitialCost() {
        System.out.println("Test Different InitialCost");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        User user = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5,"Masculino");
        user.setInitialFee(1);
        assertTrue(!user.equals(instance));
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsDifferentCardNumber() {
        System.out.println("Test Different Card Number");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94, 5.5,"Masculino");
        User user = new User("Machado", "machado@gmail.com", "123456", "346346453234", 186, 94, 5.5,"Masculino");
        assertTrue(!user.equals(instance));
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsDifferentPassword() {
        System.out.println("Test Different Password");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        User user = new User("Machado", "machado@gmail.com", "qwerty", "4544000019821545", 186, 94, 5.5,"Masculino");
        assertTrue(!user.equals(instance));
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsDifferentGender() {
        System.out.println("Test Different Gender");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94, 5.5,"Masculino");
        User user = new User("Machado", "machado@gmail.com", "qwerty", "4544000019821545", 186, 94,5.5, "Feminino");
        assertTrue(!user.equals(instance));
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEquals() {
        System.out.println("Test Equals");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        User user = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94, 5.5,"Masculino");
        assertTrue(user.equals(instance));
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsDifferenteIdUser() {
        System.out.println("Test equals - false diferente idUser ");
        User user = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        user.setUserId(0);
        User user1 = new User("username1", "anotheremail@mail.com", "qwerty2", "4704000022219545", 174, 75,5.5, "Masculino");
        user.setUserId(1);
        boolean expResult = false;
        boolean result = user.equals(user1);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsTrue() {
        System.out.println("Test equals - true ");
        User user = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        User instance = user;
        boolean expResult = true;
        boolean result = instance.equals(user);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualDifferentObj() {
        System.out.println("Test equals - false ");
        User user = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        User instance = new User("username1", "anotheremail@mail.com", "qwerty2", "4704000022219545", 174, 75,5.5, "Masculino");
        boolean expResult = false;
        boolean result = instance.equals(user);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsNull() {
        System.out.println("Test equals - null ");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        User user = null;
        boolean expResult = false;
        boolean result = instance.equals(user);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEqualsDifferentClass() {
        System.out.println("Test equals - null ");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        String password = "123456";
        boolean expResult = false;
        boolean result = instance.equals(password);
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class User.
     */
    @Test
    public void testGetId() {
        System.out.println("Test Get Id");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        long result = instance.getUserId();
        int expResult = 0;

        assertEquals(expResult, result);
    }

    /**
     * Test of equal cost method, of class User.
     */
    @Test
    public void testCostEquals() {
        System.out.println("Test Cost not equals");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        double result = instance.getInitialFee();
        double expResult = 0.5;

        assertEquals(expResult, result);
    }

    /**
     * Test of diferent cost method, of class User.
     */
    @Test
    public void testCostNotEquals() {
        System.out.println("Test Cost not equals");
        User instance = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        double result = instance.getInitialFee();
        double expResult = 50.0000;
        assertNotEquals(expResult, result);
    }

    /**
     * Test of validateEmail method, of class User.
     */
    @Test
    public void testValidateEmail() {
        System.out.println("validateEmail");
        User user = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94, 5.5,"Masculino");
        String email = "machado@gmail.com";
        boolean expResult = true;
        boolean result = user.validateEmail(email);
        assertEquals(expResult, result);

    }

    /**
     * Test of validateEmail method, of class User.
     */
    @Test
    public void testValidateEmailInvalid() {
        System.out.println("validateEmail");
        User user = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5,"Masculino");
        String email = "machadogmail.com";
        boolean expResult = false;
        boolean result = user.validateEmail(email);
        assertEquals(expResult, result);

        expResult = false;
        result = user.validateEmail("machadogmail@com");
        assertEquals(expResult, result);
    }

    /**
     * Test of validatePassword method, of class User.
     */
    @Test
    public void testValidatePassword() {
        System.out.println("Valid password");
        User user = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        String validPassword = "123456";
        boolean expResult = true;
        boolean result = user.validatePassword(validPassword);
        assertEquals(expResult, result);
    }

    /**
     * Test of validatePassword method, of class User.
     */
    @Test
    public void testrValidatePasswordInvalid() {
        System.out.println("Invalid password");
        User user = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94,5.5, "Masculino");
        String invalidPassword = "123";
        boolean expResult = false;
        boolean result = user.validatePassword(invalidPassword);
        assertEquals(expResult, result);
    }

    /**
     * Test of setPassword method, of class Park.
     */
    @Test
    public void testSetPassword() {
        System.out.println("testSetPassword");
        String expResult = "1234567";
        User user = new User("Machado", "machado@gmail.com", "123456", "4544000019821545", 186, 94, 5.5,"Masculino");
        assertNotEquals(expResult, user.getPassword());
        user.setPassword(expResult);
        assertEquals(expResult, user.getPassword());
        assertThrows(IllegalArgumentException.class, () -> user.setPassword("false"));
        assertThrows(IllegalArgumentException.class, () -> user.setPassword(""));
    }

    /**
     * Test of toString method, of class User.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int userId = 0;
        String userName = "Machado";
        String email = "machado@gmail.com";
        String password = "123456";
        String cardNumber = "4544000019821545";
        int height = 186;
        int weight = 95;
        int points = 20;
        double initialFee = 5;
        double avgSpeed = 5.5;
        String gender = "Masculino";
        User instance = new User(userName, email, password, cardNumber, height, weight,avgSpeed, gender);
        instance.setInitialFee(initialFee);
        instance.setPoints(points);
        instance.setAvgSpeed(avgSpeed);
        String expResult = "User: \n" + "userId=" + userId + "\n username=" + userName + "\n email=" + email + "\n password=" + password + "\n cardNumber=" + cardNumber + "\n height=" + height + "\n weight=" + weight + "\n points=" + points + "\n initialFee=" + initialFee + "\n averege speed= " + avgSpeed + "\n gender= " + gender + '.';

        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
