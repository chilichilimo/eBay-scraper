package comp3013.group3.ebayscraper.httpclient;

import java.util.Base64;

class Util {
	private Util() {

	}

	static String base64Encode(String clientId, String clientSecret) {
		String unencoded = clientId + ":" + clientSecret;
		return Base64.getEncoder().encodeToString(unencoded.getBytes());
	}

	static String formatItemId(String itemId) {
		return itemId.replaceAll("\\|", "%7c");
	}
}
