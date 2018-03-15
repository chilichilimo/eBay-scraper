package comp3013.group3.ebayscraper.mailer;

import com.google.common.collect.ImmutableMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.Properties;

class MailerClient implements Mailer {
	private static Logger LOG = LogManager.getLogger(MailerClient.class.getName());

	private String mailUN;
	private String mailPW;

	private static Properties emailProps = new Properties();
	private static VelocityEngine engine = new VelocityEngine();
	static {
		engine.init();
	}

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
		
		String bodyText = makeTemplate(email, items);
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailUN));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
			message.setSubject("New update!");
			message.setContent(bodyText, "text/html");
			Transport.send(message);

			LOG.info("Successfully able to send email to: " + email);
		} catch (MessagingException e) {
			LOG.info("Unable to send email to: " + email);
			LOG.debug(e.getMessage());
			throw new MailerException(email, e);
		}
	}

	String makeTemplate(String email, Iterable<ImmutableItemInfo> items) {
		Template template = engine.getTemplate("resources/email-template.vm");
		VelocityContext context = new VelocityContext();
		context.put("email", email);
		context.put("objs", items);

		StringWriter out = new StringWriter();
		template.merge(context, out);
		return out.toString();
	}
}
