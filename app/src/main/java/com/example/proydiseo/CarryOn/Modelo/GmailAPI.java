package com.example.proydiseo.CarryOn.Modelo;

import com.sun.mail.smtp.SMTPTransport;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.*;

public class GmailAPI {
    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String USERNAME = "ratlinkgames@gmail.com";
    private static final String PASSWORD = "kevinaxel";

    private static final String EMAIL_FROM = "ratlinkgames@gmail.com";

    private static final String EMAIL_SUBJECT = "Modificación de contraseña";
    private static final String EMAIL_TEXT = "El codigo para modificar su contraseña es: \n";

    public static int sendPassMail(String userMail, String code){
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.port", "587"); // default port 25

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {

            // from
            msg.setFrom(new InternetAddress(EMAIL_FROM));

            // to
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(userMail, false));

            // subject
            msg.setSubject(EMAIL_SUBJECT);

            // content
            msg.setText(EMAIL_TEXT+code);

            msg.setSentDate(new Date());

            // Get SMTPTransport
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();

            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


}
