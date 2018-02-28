package comp3013.group3.ebayscraper.SqlQuery;

import org.junit.Test;

public class DriverTest {

    @Test
    public void testMakeConnection() throws Exception {
        Driver driver  = new Driver();
        assert(driver.makeConnection() != null);
    }

}