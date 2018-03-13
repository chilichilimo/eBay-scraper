package comp3013.group3.ebayscraper;

import com.google.common.collect.ImmutableMap;
import comp3013.group3.ebayscraper.SqlQuery.Query;
import comp3013.group3.ebayscraper.httpclient.Client;
import comp3013.group3.ebayscraper.mailer.ImmutableItemInfo;
import comp3013.group3.ebayscraper.mailer.Mailer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Main {
    public static void main(String args[]) {

        Logger LOG = LogManager.getLogger(Main.class.getName());

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(System.getProperty("user.dir") + "/resources/config.properties"));
        } catch (IOException e) {
            LOG.debug("IOException: " + e.getMessage());
        }

        Query query = Query.getInstance(properties);
        Client client = Client.builder(properties);
        Mailer mailer = Mailer.builder(properties);

        ArrayList<String> ebayIds = query.getProductsEbayId();
        for (String id : ebayIds) {
            JSONObject payload = client.getItem(id);
            if (!payload.has("price")) {
                LOG.error("eBay ID " + id + "is not valid.");
                //TODO: Cleanup expired row with eBayID.
                continue;
            }
            double newPrice = payload.getJSONObject("price").getDouble("value");
            query.updateProductsPrices(id, newPrice);
            query.updatePriceHistory(id, newPrice);
        }

        ArrayList<Integer> watchIds = query.checkPriceWatchNotifications();
        HashMap<Integer, ArrayList<Integer>> userWatches = query.createEmailNotificationItems(watchIds);

        if (!userWatches.isEmpty()) {
            HashMap<String, ArrayList<ImmutableItemInfo>> emailItemMap = new HashMap<String, ArrayList<ImmutableItemInfo>>();
            for (Map.Entry<Integer, ArrayList<Integer>> entry : userWatches.entrySet()) {
                String userEmail = query.getUserEmail(entry.getKey());
                ArrayList<ImmutableItemInfo> listOfProducts = new ArrayList<ImmutableItemInfo>();
                for (Integer product_id : entry.getValue()) {
                    ImmutableItemInfo item = query.getProductDetails(product_id);
                    listOfProducts.add(item);
                }
                emailItemMap.put(userEmail, listOfProducts);
            }
            mailer.sendMail(ImmutableMap.<String, Iterable<ImmutableItemInfo>>builder().putAll(emailItemMap).build());
        }

        for (int watchId: watchIds) {
            query.updateLastNotifiedPrice(watchId, query.getProductPrice(query.getProductIdWatchTable(watchId)));
        }
    }
}
