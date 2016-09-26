package ua.lviv.lgs.service.implementation;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import ua.lviv.lgs.service.MailSenderService;

@Service
public class MailSenderServiceImpl implements MailSenderService {

	final static String NAME = "logos.sender228@gmail.com";
	final static String EMAIL = "logos.sender228@gmail.com";
	final static String PASS = "12345678qaz";
		
	public synchronized void sendMail(String messageHeader, String email, String message){
		
		Properties prop = System.getProperties();
		prop.setProperty("mail.smtp.starttls.enable", "true");
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.port", "465");
        prop.setProperty("mail.smtp.host", "smtp.gmail.com");
        prop.setProperty("mail.smtp.socketFactory.port", "465");
        prop.setProperty("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        
        Authenticator aut = new Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication(){
        		return new PasswordAuthentication(NAME, PASS);
        	}
		};
        
        Session session = Session.getDefaultInstance(prop, aut);
        
        try {
			MimeMessage mess = new MimeMessage(session);
			mess.setFrom(new InternetAddress(EMAIL));
			mess.addRecipient(Message.RecipientType.TO, new InternetAddress(EMAIL));
			mess.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			mess.setSubject(messageHeader);
			mess.setText(message);
			Transport.send(mess);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong");
		}
	}
}