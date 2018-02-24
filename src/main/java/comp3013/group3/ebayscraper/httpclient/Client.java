package comp3013.group3.ebayscraper.httpclient;

import org.json.JSONObject;

import java.util.Properties;

public interface Client {

	JSONObject getItem(String itemId);

	JSONObject search(String searchTerm);

	static Client builder(Properties properties) {
		return new EbayClient(properties);
	}
}
