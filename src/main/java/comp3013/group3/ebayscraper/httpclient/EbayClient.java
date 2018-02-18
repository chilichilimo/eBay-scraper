package comp3013.group3.ebayscraper.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

class EbayClient implements Client {
	private static Logger LOG = LogManager.getLogger(EbayClient.class.getName());

	// TODO Move urls to properties
	private String browseUrl = "https://api.ebay.com/buy/browse/v1/";
	private String getItemUrl = "item/";
	private String token = "";


	EbayClient(Properties properties) {

	}

	@Override
	public String getItem(String itemId) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(browseUrl + getItemUrl + itemId);

		request.addHeader("Authorization", "Bearer" + token);
		request.addHeader("Content-Type", "application/json");

		LOG.info("Making getItem request for: " + itemId);

		String result;
		try {
			HttpResponse response = client.execute(request);

			LOG.info("Response code: " + response.getStatusLine().getStatusCode());

			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			result = buffer.toString();
		} catch (IOException e) {
			LOG.warn("IOException: " + e.getMessage());
			result = null;
		}


		return result;
	}

	@Override
	public String search(String searchTerm) {
		return null;
	}
}
