package comp3013.group3.ebayscraper.mailer;

import com.google.common.collect.ImmutableMap;

import java.util.Properties;

public interface Mailer {

	/**
	 * Method call to send email
 	 * @param toEmail Map of emails to send.
	 *                Key consists of email addresses to send to.
	 *                Values are an iterable list of ItemInfo objects
	 * @throws MailerException Wrapper around MessagingException from javax.mail extending RuntimeExceptiom
	 */
	void sendMail(ImmutableMap<String, Iterable<ImmutableItemInfo>> toEmail) throws MailerException;

	/**
	 * Builder for instance of Mailer. See MailerTest for example instantiation
	 * @param properties Should contain config info. E.g. username and password for mailer account
	 * @return Mailer
	 */
	static Mailer builder(Properties properties) {
		return new MailerClient(properties);
	}
}
