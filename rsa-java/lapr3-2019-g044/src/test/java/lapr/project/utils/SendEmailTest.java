/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 *
 */
public class SendEmailTest {
    public SendEmailTest() {
    }

    /**
     * Test of ValidatesendEmailTrue method, of class SendEmail.
     */
    @Test
    public void testValidatesendEmailTrue() {
        System.out.println("ValidatesendEmailTrue");
        String email = "joaocarlos845@hotmail.com";
        String subject  = "Test subject";
        String body = "Test body";
        boolean result = SendEmail.validateSendEmail(email,subject,body);

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
          String subject  = "Test subject";
        String body = "Test body";
        boolean result = SendEmail.validateSendEmail(email,subject,body);
        boolean expectedResult = false;
        assertEquals(expectedResult, result);
    }
}
