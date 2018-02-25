package comp3013.group3.ebayscraper.httpclient;

import org.json.JSONObject;

import java.util.Properties;

public interface Client {

	/**
	 * Wrapper around the getItem eBay API
	 * @param itemId eBay item ID to be queried
	 * @return JSONObject
	 */
	JSONObject getItem(String itemId);

	/**
	 * TODO
	 * Will currently return null
	 * @param searchTerm Search term to be queried
	 * @return JSONObject
	 */
	JSONObject search(String searchTerm);

	/**
	 * Builder for eBay Client interface. See ClientTest for example instantiation
	 * @param properties Should contain config information. E.g. urls, clientID, etc.
	 * @return Client
	 */
	static Client builder(Properties properties) {
		return new EbayClient(properties);
	}
}
