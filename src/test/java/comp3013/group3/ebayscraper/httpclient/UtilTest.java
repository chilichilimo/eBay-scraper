package comp3013.group3.ebayscraper.httpclient;

import org.junit.Assert;
import org.junit.Test;


public class UtilTest {

	String itemId = "v1|401421457135|671053460928";
	String formattedItemId = "v1%7c401421457135%7c671053460928";

	String fakeClientID = "hello";
	String fakeClientSecret = "world";
	String encodedCred = "aGVsbG86d29ybGQ=";


	@Test
	public void testBase64Encode() throws Exception {
		String encoded = Util.base64Encode(fakeClientID, fakeClientSecret);

		Assert.assertEquals(encodedCred, encoded);
	}

	@Test
	public void testFormatItemId() throws Exception {
		String formatted = Util.formatItemId(itemId);

		Assert.assertEquals(formattedItemId, formatted);
	}
}