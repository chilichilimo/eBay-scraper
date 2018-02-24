package comp3013.group3.ebayscraper.httpclient;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Properties;

public class ClientTest {

	@Mock
	Properties properties;

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	String itemId = "v1|401421457135|671053460928";
	String token = "";

	Client client;

	@Before
	public void setUp() throws Exception {
		Mockito.when(properties.getProperty("token")).thenReturn(token);
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