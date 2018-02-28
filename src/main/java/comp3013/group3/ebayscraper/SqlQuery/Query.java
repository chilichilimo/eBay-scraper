package comp3013.group3.ebayscraper.SqlQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Contains static functions for SQL queries.
 */
class Query {

    /**
     * 1. Retrieve all products, update their last_known_price in products table, update the
     * history table with new prices and timestamps.
     * 2. Iterate over watches. If there is a watch that has a lower last known price, update
     * the last notified price, add the price_watch id to a hashset of watchlist ids
     * 3. For every element in the hashset, find all items a user_id is watching, add a ticket
     * to the queue and eliminate all the id's with that user_id from hashset.
     */

    Driver driver = new Driver();
    Connection connection;

    /**
     * Singleton Design Pattern
     */
    private Query(){
        connection = driver.makeConnection();
    }

    /**
     * TODO: Implement.
     * Retrieves all the eBay IDs of the products to be retrieve new prices of products.
     * @return list of eBay IDs to be used to retrieve new prices.
     */
    static ArrayList<String> getProductsEbayId(){
        ArrayList<String> results = new ArrayList<String>();
        return results;
    }

    /**
     * TODO: Implement.
     * updates the price of all products with the data received form eBay.
     * @param eBayId product's eBay ID
     * @param newPrice product's new price
     * @return true if all product price update is successful.
     */
    static boolean updateProductsPrices(String eBayId, double newPrice){
        boolean result = false;
        return result;
    }

    /**
     * TODO: Implement.
     * Adds the latest price of an item to the price history
     * @param productId product ID
     * @return true if the price update was successful, false otherwise.
     */
    static boolean updatePriceHistory(int productId){
        boolean result = false;
        return result;
    }

    /**
     * TODO: Implement.
     * Iterates over all price watch IDs and if there is a new price for each item
     * (lower than before),it adds the price watch ID to the output. Also updates
     * the last_notified_price.
     * @return a set of price watch IDs.
     */
    static HashSet<Integer> checkPriceWatchNotifications(){
        HashSet<Integer> priceWatchIds = new HashSet<Integer>();
        return priceWatchIds;
    }


    /**
     * TODO: Implement a POJO for return element (user email and the list of their desired products with their prices.)
     * TODO: Implement
     * Creates email notification objects to be added to the queue of mailer.
     * @param priceWatchId the list of priceWatches recently updated.
     */
    static void createEmailNotificationItems(HashSet<Integer> priceWatchId){

    }

    /**
     * Executes a SQL query to retrieve user's email address from their ID.
     * @param id user ID
     * @return user email
     */
    static String getUserEmail(int id){
        Query query = new Query();
        String result = null;

        String sqlQuery = "SELECT email FROM users WHERE id = " + id;
        try{
            Statement statement = query.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            resultSet.next();
            result = resultSet.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
