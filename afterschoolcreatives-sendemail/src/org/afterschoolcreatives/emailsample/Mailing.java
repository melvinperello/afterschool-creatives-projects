/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afterschoolcreatives.emailsample;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Jhon Melvin
 */
public class Mailing {

    public static void main(String[] args) {

        // Create SMTP Configuration using TLS.
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
//------------------------------------------------------------------------------
// FOR SSL
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.socketFactory.class",
//                "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.port", "465");
//------------------------------------------------------------------------------

        // Create an authenticated session using your Credentials
        final String username = "sender@gmail.com";
        final String password = "senderPassword";
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        // declare transport object.
        Transport transport = null;
        try {
            // Get Transport object.
            transport = session.getTransport("smtp");
            // try to connect
            transport.connect();
            System.out.println("Service Connected ...");
            // Create Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sender@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("reciever@gmail.com"));
            message.setSubject("Daily Operation Report");

            //------------------------------------------------------------------
            // Create Message
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText("Hello here is your report for today", "utf-8");
            // Create Attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();
            String file = "C:\\send.txt";
            String fileName = "attachment.txt";
            DataSource source = new FileDataSource(file);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(fileName);
            // Create multipart message object
            Multipart multipart = new MimeMultipart();
            // add parts
            multipart.addBodyPart(messagePart);
            multipart.addBodyPart(attachmentPart);

            //------------------------------------------------------------------
            // set message contents.
            message.setContent(multipart);
            // send the message
            System.out.println("Sending Message . . .");
            transport.sendMessage(message, message.getAllRecipients());

        } catch (MessagingException e) {
            System.err.println("There was an error sending your e-mail");
            e.printStackTrace();
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                    System.out.println("Service Closed . . .");
                } catch (MessagingException ex) {
                    System.err.println("There was an error while disconnecting the service.");
                    ex.printStackTrace();
                }
            }
        }
    }
}
