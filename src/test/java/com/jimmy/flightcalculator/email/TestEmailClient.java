package com.jimmy.flightcalculator.email;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.Test;

public class TestEmailClient {
	public String RESOURCES = "src/test/java/resources/folder";
	@Test
	public void sendEmail() throws GeneralSecurityException, IOException, AddressException, MessagingException{
		File emailFolder = new File(RESOURCES);
		if (!emailFolder.exists()){
			emailFolder.mkdirs();
		}
		EmailClient emailClient  = new EmailClient(RESOURCES);
		emailClient.sendEmail("chen.s.jimmy@gmail.com", "chen.s.jimmy@gmail.com", "testEmail", "TestBody Hello");
	}
}
