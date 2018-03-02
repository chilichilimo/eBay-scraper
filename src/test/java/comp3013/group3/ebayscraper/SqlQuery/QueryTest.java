package comp3013.group3.ebayscraper.SqlQuery;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
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
        assert (Query.getUserEmail(1) != null);
    }

    //TODO: Implement tests below
    @Test
    public void testGetProducts() throws Exception {
        List<String> results = query.getProductsEbayId();
        Assert.assertTrue(results.size() > 0);
        for (String s : results) {
            Assert.assertTrue(StringUtils.countMatches(s, '|') == 2);
        }
    }

    @Test
    public void testGetProductWatches() throws Exception {

    }

    @Test
    public void testUpdateProducts() throws Exception {

    }

    @Test
    public void testUpdateProductWatches() throws Exception {

    }

    @Test
    public void testUpdatePriceHistory() throws Exception {

    }


}