package comp3013.group3.ebayscraper.SqlQuery;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class QueryTest {
    Query query;

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream(System.getProperty("user.dir") + "/resources/config.properties"));
        query = Query.getInstance(properties);
    }

    @Test
    public void testGetUserID() throws Exception {
        assert (query.getUserEmail(1) != null);
    }

    @Test
    public void testGetProducts() throws Exception {
        List<String> results = query.getProductsEbayId();
        Assert.assertTrue(results.size() > 0);
        for (String s : results) {
            Assert.assertTrue(StringUtils.countMatches(s, '|') == 2);
        }
    }

    @Test
    public void testUpdateProductsPrices() throws Exception {
        assert (query.updateProductsPrices("v1|273061822198|572310666234", 300));
    }

    @Test
    public void testUpdatePriceHistory() throws Exception{
        assert (query.updatePriceHistory("v1|273061822198|572310666234", 300));
    }

    @Test
    public void testCheckPriceWatchNotifications() throws Exception{
        assert (query.checkPriceWatchNotifications() != null);
    }

    @Test
    public void testUpdateLastNotifiedPrice(){
        assert (query.updateLastNotifiedPrice(4,300));
        assert (query.updateLastNotifiedPrice(9,300));
    }

    @Test
    public void testCreateEmailNotificationItems(){
        ArrayList<Integer> test = new ArrayList<>();
        test.add(4);
        test.add(5);
        test.add(9);
        assert (query.createEmailNotificationItems(test) != null);
    }
}