package comp3013.group3.ebayscraper.httpclient;

import java.util.Base64;
import java.util.Map;

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

	static String makePostPayload(Map<String, String> payload) {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (Map.Entry<String, String> entry : payload.entrySet()) {
			if (first) {
				first = false;
			} else {
				result.append("&");
			}

			result.append(entry.getKey());
			result.append("=");
			result.append(entry.getValue());
		}

		return result.toString();
	}
}
