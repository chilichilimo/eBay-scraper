package comp3013.group3.ebayscraper.SqlQuery;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

/**
 * Contains static functions for SQL queries.
 */
public class Query {

    /**
     * 1. Retrieve all products, update their last_known_price in products table, update the
     * history table with new prices and timestamps.
     * 2. Iterate over watches. If there is a watch that has a lower last known price, update
     * the last notified price, add the price_watch id to a hashset of watchlist ids
     * 3. For every element in the hashset, find all items a user_id is watching, add a ticket
     * to the queue and eliminate all the id's with that user_id from hashset.
     */

    private Driver driver = new Driver();
    private Connection connection;

    private static Query query;

    /**
     * Singleton Design Pattern
     */
    private Query(Properties properties){
        connection = driver.makeConnection(properties);
    }

    public static Query getInstance(Properties properties) {
        if (query == null) {
            query = new Query(properties);
        }

        return query;
    }

    /**
     * Gets all the eBay IDs of the products to be used to get new prices of products.
     * @return list of eBay IDs to be used to retrieve new prices.
     */
    public ArrayList<String> getProductsEbayId(){
        ArrayList<String> results = new ArrayList<String>();

        String sqlQuery = "SELECT ebay_id FROM products";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                results.add(resultSet.getString("ebay_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    /**
     * updates the price of all products in products table with the data received form eBay.
     * @param ebayId product's eBay ID
     * @param newPrice product's new price
     * @return true if all product price update is successful.
     */
    public boolean updateProductsPrices(String ebayId, double newPrice){
        boolean result = false;

        String sqlQuery = "UPDATE products SET last_known_price = " + newPrice + " WHERE ebay_id = \'" + ebayId + "\'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * returns the product's ID (foreign key of all tables except product) from eBay ID
     * @param ebayId item's eBay ID
     * @return item's product ID
     */
    private int getProductIdFromBayId(String ebayId){
        int result = -1;

        String sqlQuery = "SELECT id FROM products WHERE ebay_id =  \'" + ebayId + "\'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            resultSet.next();
            result = resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Adds the latest price of an item to the price history
     * @param ebayId  item's eBay ID
     * @return true if the price update was successful, false otherwise.
     */
    public boolean updatePriceHistory(String ebayId, double newPrice){
        boolean result = false;

        int productId = getProductIdFromBayId(ebayId);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String sqlQuery = "UPDATE price_history SET price = " + newPrice + ", timestamp = \'" + sdf.format(timestamp)
                + "\'" + " WHERE product_id = " + productId;

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * TODO: Implement.
     * Iterates over all price watch IDs and if there is a new price for each item
     * (lower than before),it adds the price watch ID to the output. Also updates
     * the last_notified_price.
     * @return a set of price watch IDs.
     */
    public HashSet<Integer> checkPriceWatchNotifications(){
        HashSet<Integer> priceWatchIds = new HashSet<Integer>();
        return priceWatchIds;
    }


    /**
     * TODO: Implement a POJO for return element (user email and the list of their desired products with their prices.)
     * TODO: Implement
     * Creates email notification objects to be added to the queue of mailer.
     * @param priceWatchId the list of priceWatches recently updated.
     */
    public void createEmailNotificationItems(HashSet<Integer> priceWatchId){

    }

    /**
     * Executes a SQL query to retrieve user's email address from their ID.
     * @param id user ID
     * @return user email
     */
    public String getUserEmail(int id){
        String result = null;

        String sqlQuery = "SELECT email FROM users WHERE id = " + id;
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            resultSet.next();
            result = resultSet.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
