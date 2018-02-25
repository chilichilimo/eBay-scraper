package comp3013.group3.ebayscraper.httpclient;

import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;


public class UtilTest {

	String itemId = "v1|401421457135|671053460928";
	String formattedItemId = "v1%7c401421457135%7c671053460928";

	String fakeClientID = "hello";
	String fakeClientSecret = "world";
	String encodedCred = "aGVsbG86d29ybGQ=";

	ImmutableMap<String, String> fakePayload = ImmutableMap.of("hello", "1", "world", "2");
	String formattedPayload = "hello=1&world=2";

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

	@Test
	public void testMakePostPayload() throws Exception {
		String formatted = Util.makePostPayload(fakePayload);

		Assert.assertEquals(formattedPayload, formatted);
	}
}