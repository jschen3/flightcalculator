package com.jimmy.flightcalculator.email;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailClient {
	private final String SMTP_HOST = "smtp.gmail.com";
	private final String SMTP_PORT = "465";
	
	public EmailClient() {
	}
	
	public void sendEmail(String to, final String from, final String password, String subject, String bodyText) throws AddressException, MessagingException, UnsupportedEncodingException{
		Properties props = new Properties();
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.smtp.port",  SMTP_PORT);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		transportEmail(session,to, from, password, subject, bodyText);
	}

	private void transportEmail(Session session,String to, String from, String password,  String subject, String bodyText) throws MessagingException, UnsupportedEncodingException {
		MimeMessage msg = new MimeMessage(session);
	    msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	    msg.addHeader("format", "flowed");
	    msg.addHeader("Content-Transfer-Encoding", "8bit");
	    msg.setFrom(new InternetAddress(from, "NoReply-JD"));
	    msg.setReplyTo(InternetAddress.parse(to, false));
	    msg.setSubject(subject, "UTF-8");
	    msg.setText(bodyText, "UTF-8");

	    msg.setSentDate(new Date());
	    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
	    Transport transport = session.getTransport("smtps");
	    transport.connect(SMTP_HOST, Integer.parseInt(SMTP_PORT), to, password);
	    transport.sendMessage(msg, msg.getAllRecipients());
	    transport.close();
	}	
	
}
