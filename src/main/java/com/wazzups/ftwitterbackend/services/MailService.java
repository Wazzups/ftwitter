package com.wazzups.ftwitterbackend.services;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.wazzups.ftwitterbackend.exceptions.EmailFailedToSendException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

@Service
public class MailService {

    private final Gmail gmail;

    public MailService(Gmail gmail) {
        this.gmail = gmail;
    }

    public void sendMail(String to, String subject, String text) {
        Properties props = new Properties();

        Session session = Session.getInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress("ftwitter@gmail.com"));
            message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(text);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            message.writeTo(os);

            byte[] data = os.toByteArray();
            String encodeMail = Base64.getUrlEncoder().encodeToString(data);
            Message msg = new Message();
            msg.setRaw(encodeMail);
            msg = gmail.users().messages().send("me", msg).execute();
        } catch (Exception e) {
            throw  new EmailFailedToSendException();
        }
    }
}
