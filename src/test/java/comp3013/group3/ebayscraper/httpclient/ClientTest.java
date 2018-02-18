package comp3013.group3.ebayscraper.httpclient;

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

	String itemId = "v1%7c401421457135%7c671053460928";
	String token = "";

	Client client;

	@Before
	public void setUp() throws Exception {
		Mockito.when(properties.getProperty("token")).thenReturn(token);
		client = Client.builder(properties);
	}

	@Test
	public void testGetItem() throws Exception {
		String result = client.getItem(itemId);

		System.out.println(result);
	}

	@Test
	public void testSearch() throws Exception {

	}
}