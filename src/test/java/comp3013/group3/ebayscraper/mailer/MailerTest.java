package comp3013.group3.ebayscraper.mailer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.Properties;

public class MailerTest {

	Properties properties = new Properties();

	String testDest1;
	String testDest2;

	ImmutableMap<String, Iterable<ImmutableItemInfo>> testItems;

	Mailer mailer;

	@Before
	public void setUp() throws Exception {
		properties.load(new FileInputStream(System.getProperty("user.dir") + "/resources/config.properties"));
		mailer = Mailer.builder(properties);
		testDest1 = properties.getProperty("test_dest1");
		testDest2 = properties.getProperty("test_dest2");

		ImmutableItemInfo item1 = ImmutableItemInfo.builder()
				.name("Hello")
				.url("https://ebayapp.azurewebsites.net/")
				.price(12.34)
				.imageUrl("https://images.pexels.com/photos/39317/chihuahua-dog-puppy-cute-39317.jpeg?w=1260&h=750&auto=compress&cs=tinysrgb")
				.build();

		ImmutableItemInfo item2 = ImmutableItemInfo.builder()
				.name("world")
				.url("https://ebayapp.azurewebsites.net/")
				.price(34.56)
				.imageUrl("https://images.pexels.com/photos/39317/chihuahua-dog-puppy-cute-39317.jpeg?w=1260&h=750&auto=compress&cs=tinysrgb")
				.build();

		ImmutableItemInfo item3 = ImmutableItemInfo.builder()
				.name("Hello 2")
				.url("https://ebayapp.azurewebsites.net/")
				.price(56.78)
				.imageUrl("https://images.pexels.com/photos/39317/chihuahua-dog-puppy-cute-39317.jpeg?w=1260&h=750&auto=compress&cs=tinysrgb")
				.build();

		ImmutableList<ImmutableItemInfo> itemList1 = ImmutableList.of(item1, item2);
		ImmutableList<ImmutableItemInfo> itemList2 = ImmutableList.of(item3);

		testItems = ImmutableMap.of(testDest1, itemList1, testDest2, itemList2);
	}

	@Test
	public void testSendMail() throws Exception {
		mailer.sendMail(testItems);
	}
}