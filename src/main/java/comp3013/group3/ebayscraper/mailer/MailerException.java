package comp3013.group3.ebayscraper.mailer;

import javax.mail.MessagingException;

public class MailerException extends RuntimeException {
	private MessagingException callerException;
	private String email;

	MailerException(String email, MessagingException e) {
		super("Unable to send email to: " + email + "\n" + e.getMessage());
		this.email = email;
		callerException = e;
	}
}
