package comp3013.group3.ebayscraper.httpclient;

import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

class EbayClient implements Client {
	private static Logger LOG = LogManager.getLogger(EbayClient.class.getName());

	private HttpClient client = new DefaultHttpClient();

	private String browseUrl;
	private String getItemUrl;
	private String authUrl;
	private String apiScope;

	private String clientId;
	private String clientSecret;

	private String token;
	private String tokenRequestPayload;


	EbayClient(Properties properties) {
		browseUrl = properties.getProperty("browse_url");
		getItemUrl = properties.getProperty("get_item_url");
		authUrl = properties.getProperty("auth_url");
		apiScope = properties.getProperty("base_api_scope");

		clientId = properties.getProperty("client_id");
		clientSecret = properties.getProperty("client_secret");

		tokenRequestPayload = Util.makePostPayload(
				ImmutableMap.of("grant_type", "client_credentials", "scope", apiScope));
		token = renewToken();
	}

	@Override
	public JSONObject getItem(String itemId) {
		HttpGet request = new HttpGet(browseUrl + getItemUrl + Util.formatItemId(itemId));

		request.addHeader("Authorization", "Bearer " + token);
		request.addHeader("Content-Type", "application/json");

		LOG.info("Making getItem request for: " + itemId);

		JSONObject result;
		try {
			result = makeRequest(request);
		} catch (IOException e) {
			LOG.debug("IOException: " + e.getMessage());
			result = null;
		}

		return result;
	}

	@Override
	public JSONObject search(String searchTerm) {
		return null;
	}


	private String renewToken() {
		HttpPost req = new HttpPost(authUrl);
		req.addHeader("Content-Type", "application/x-www-form-urlencoded");
		req.addHeader("Authorization", "Basic " + Util.base64Encode(clientId, clientSecret));

		HttpEntity entity;
		try {
			entity = new StringEntity(tokenRequestPayload);
		} catch (UnsupportedEncodingException e) {
			LOG.error("Unable to encode payload into entity");
			System.exit(1);
			return null;
		}

		req.setEntity(entity);
		String result;
		try {
			JSONObject response = makeRequest(req);
			LOG.info("Successfully obtained access code");
			result = response.getString("access_token");
		} catch (IOException e) {
			LOG.debug("IOException: " + e.getMessage());
			result = null;
		}

		return result;
	}

	private JSONObject makeRequest(HttpUriRequest request) throws IOException {
		HttpResponse response = client.execute(request);

		LOG.info("Response code: " + response.getStatusLine().getStatusCode());

		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer buff = new StringBuffer();
		String line;
		while ((line = reader.readLine()) != null) {
			buff.append(line);
		}

		return new JSONObject(buff.toString());
	}
}
