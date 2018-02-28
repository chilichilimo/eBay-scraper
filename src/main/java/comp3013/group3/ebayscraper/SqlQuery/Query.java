package comp3013.group3.ebayscraper.SqlQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Contains static functions for SQL queries.
 */
class Query {

    Driver driver = new Driver();
    Connection connection;

    /**
     * Singleton Design Pattern
     */
    private Query(){
        connection = driver.makeConnection();
    }

    /**
     * Executes a SQL query to retrieve user's email address from their ID
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

    static void getProducts(){

    }

    static void getProductWatches(){

    }

    static void updateProducts(){

    }

    static void updateProductWatches(){

    }

    static void updatePriceHistory(){

    }
}
