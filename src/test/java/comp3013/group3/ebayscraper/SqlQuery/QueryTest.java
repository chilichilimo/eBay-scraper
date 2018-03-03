package comp3013.group3.ebayscraper.SqlQuery;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

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
}