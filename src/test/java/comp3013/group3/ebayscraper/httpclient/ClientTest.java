package comp3013.group3.ebayscraper.httpclient;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.Properties;

public class ClientTest {

	Properties properties = new Properties();

	String itemId = "v1|401421457135|671053460928";

	Client client;

	@Before
	public void setUp() throws Exception {
		properties.load(new FileInputStream(System.getProperty("user.dir") + "/resources/config.properties"));
		client = Client.builder(properties);
	}

	@Test
	public void testGetItem() throws Exception {
		JSONObject result = client.getItem(itemId);

		Assert.assertEquals(result.get("itemId"), itemId);
	}

	@Test
	public void testSearch() throws Exception {

	}
}