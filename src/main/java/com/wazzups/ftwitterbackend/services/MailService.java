package com.wazzups.ftwitterbackend.services;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Properties;

@Service
public class MailService {

    private final Gmail gmail;

    public MailService(Gmail gmail) {
        this.gmail = gmail;
    }

    public void sendMail(String to, String subject, String text) throws Exception{
        Properties props = new Properties();

        Session session = Session.getInstance(props);
        MimeMessage message = new MimeMessage(session);

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
        gmail.users().messages().send("me", msg).execute();
    }
}
