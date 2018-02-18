package comp3013.group3.ebayscraper.httpclient;

import java.util.Properties;

public interface Client {

	String getItem(String itemId);

	String search(String searchTerm);

	static Client builder(Properties properties) {
		return new EbayClient(properties);
	}
}
