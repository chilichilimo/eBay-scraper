package comp3013.group3.ebayscraper.SqlQuery;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDCB Driver Manager wrapper
 */
class Driver {

    /**
     * Loads JDCB driver
     */
    private void loadDriver(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println("Error in loading driver: " + ex.getMessage() + "\n" + ex.getStackTrace());
        }
    }

    /**
     * Makes connection with a given database
     * @param url database url
     * @param username database username
     * @param password database password
     * @return a connection instance to a database
     */
    Connection makeConnection(String url, String username, String password){

        loadDriver();

        // Make connection
        Connection connection = null;
        try {
            connection =  DriverManager.getConnection(url, username, password);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return connection;
    }

    /**
     * Makes connection with a local configuration file.
     * @return a connection instance to a database
     */
    Connection makeConnection(){
        Properties properties = new Properties();
        try{
            properties.load(new FileInputStream(System.getProperty("user.dir") + "/resources/config.properties"));
        }
        catch (Exception ex){
            System.out.println("Error in loading config file: " + ex.getMessage() + "\n" + ex.getStackTrace());
        }
        String db_url = properties.getProperty("db_url");
        String username = properties.getProperty("sql_username");
        String password = properties.getProperty("sql_password");

        return makeConnection(db_url, username, password);
    }
}
