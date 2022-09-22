/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.utils;

import java.util.Date;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;

/**
 *
 *
 */
public class SendEmail {
    

    /**
     * Public method Send email notification to user email
     *
     * @param email : email to send
     * @param body : body text of email
     * @param subject : subject text of email
     * @return : false if failed or true otherwise.
     */
    public static boolean validateSendEmail(String email, String subject, String body) {
        return sendEmail(email, subject, body);
    }

    /**
     * Private method Send email notification to user email
     *
     * @param email : email to send
     * @param body : body text of email
     * @param subject : subject text of email
     * @return : false if failed or true otherwise.
     */
    private static boolean sendEmail(String email, String title, String body) {
        try {
            String host = "smtp.gmail.com";
            String user = "lapr3.g044.2019@gmail.com";
            String pass = "QWERTY123@";
            String to = email;
            String from = "llapr3.g044.2019@gmail.com";
            String subject = title;
            String messageText = body;
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            javax.mail.Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(messageText);

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, user, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            return true;
        } catch (MessagingException ex) {
            return false;
        }
    }

}
