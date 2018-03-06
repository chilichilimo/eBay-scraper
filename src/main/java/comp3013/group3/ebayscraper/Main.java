package comp3013.group3.ebayscraper;

import comp3013.group3.ebayscraper.SqlQuery.Query;
import comp3013.group3.ebayscraper.httpclient.Client;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Main {
    public static void main(String args[]){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(System.getProperty("user.dir") + "/resources/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Query query = Query.getInstance(properties);
        Client client = Client.builder(properties);

        ArrayList<String> ebayIds = query.getProductsEbayId();
        for (String id : ebayIds) {
            JSONObject payload = client.getItem(id);
            System.out.println(payload.getJSONObject("price").toString());
            double newPrice = payload.getJSONObject("price").getDouble("value");
            query.updateProductsPrices(id, newPrice);
            query.updatePriceHistory(id, newPrice);
        }

        ArrayList<Integer> watchIds = new ArrayList<Integer>();
        watchIds = query.checkPriceWatchNotifications();


    }
}
