package com.edt.email;

import java.io.File;
import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Outlook365Email
{
    public Outlook365Email(String properties) 
            throws IOException
    {
        File propertiesFile = new File(properties);
        
        if (!propertiesFile.exists())
            throw new IOException(properties + " doesn't exsist");
        
        EmailProperties.setProperties(properties);
        EmailProperties.init();
    }
    
    public void send(String body)
    {
        try
        {
            String user = System.getProperty("email.smtp.user"),
                    pwd = System.getProperty("email.smtp.password"),
                    
                    to = System.getProperty("email.smtp.to"),
                    cc = System.getProperty("email.smtp.cc"),
                    bcc = System.getProperty("email.smtp.bcc"),
                    from = System.getProperty("email.smtp.from"),
                    subject = System.getProperty("email.smtp.subject");
    
            Session session = Session.getInstance(System.getProperties(), new javax.mail.Authenticator()
            {
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(user, pwd);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            
            if (null != to)
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            
            if (null != cc)
                message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
            
            if (null != bcc)
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
            
            message.setSubject(subject);
            
            if (null != body)
            {
                message.setContent(body, "text/html");
                
                System.out.println("Sending " + subject + 
                                    "\r\n- from " + from +
                                    "\r\n- to " + ((null != to) ? to : "N/A") +
                                    "\r\n- cc " + ((null != cc) ? cc : "N/A") +
                                    "\r\n- bcc " + ((null != bcc) ? bcc : "N/A") +
                                    "\r\n- on " + System.getProperty("mail.smtp.host") + "...");
                
                Transport.send(message);
                System.out.println("Successful!!");
            }
            else
                System.out.println("NO email to send.");
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }
}