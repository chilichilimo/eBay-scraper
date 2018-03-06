package comp3013.group3.ebayscraper.mailer;

import com.google.common.collect.ImmutableMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

class MailerClient implements Mailer {
	private static Logger LOG = LogManager.getLogger(MailerClient.class.getName());

	private String mailUN;
	private String mailPW;

	private static Properties emailProps = new Properties();

	static {
		emailProps.put("mail.smtp.auth", "true");
		emailProps.put("mail.smtp.starttls.enable", "true");
		emailProps.put("mail.smtp.host", "smtp.gmail.com");
		emailProps.put("mail.smtp.port", "587");
	}

	private Session session;

	MailerClient(Properties properties) {
		mailUN = properties.getProperty("mail_un");
		mailPW = properties.getProperty("mail_pw");

		session = Session.getInstance(emailProps, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailUN, mailPW);
			}
		});
	}

	@Override
	public void sendMail(ImmutableMap<String, Iterable<ImmutableItemInfo>> toEmail) throws MailerException {
		toEmail.entrySet().forEach(entry -> sendMail(entry.getKey(), entry.getValue()));
	}

	void sendMail(String email, Iterable<ImmutableItemInfo> items) throws MailerException {
		StringBuilder builder = new StringBuilder();
		builder.append("New lowest prices for the following items: \n");

		boolean no_items = true;
		for (ImmutableItemInfo item : items) {
			if (item == null) {
				System.out.println(email + " has null item");
			} else {
				no_items = false;
				builder.append("item: " + item.name() + " price: " + item.price() + " link " + item.url());
				builder.append("\n");
			}
		}

		if (no_items) {
			return;
		}

		builder.append("Footer stuff\n");
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailUN));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
			message.setSubject("New update!");
			message.setText(builder.toString());

			Transport.send(message);

			LOG.info("Successfully able to send email to: " + email);
		} catch (MessagingException e) {
			LOG.info("Unable to send email to: " + email);
			LOG.debug(e.getMessage());
			throw new MailerException(email, e);
		}
	}
}
