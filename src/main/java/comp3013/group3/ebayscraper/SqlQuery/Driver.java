package comp3013.group3.ebayscraper.SqlQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDCB Driver Manager wrapper
 */
class Driver {

    Logger LOG = LogManager.getLogger(Driver.class.getName());

    Driver() {
        loadDriver();
    }

    /**
     * Loads JDCB driver
     */
    private void loadDriver(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
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

        String passing_url = url + ";databaseName=ebaySqlServerDB;user="+username+";password="+password+";";

        // Make connection
        Connection connection = null;
        try {
//            connection = DriverManager.getConnection(url, username, password);
            connection = DriverManager.getConnection(passing_url);

        } catch (SQLException ex) {
            LOG.debug("SQLException: " + ex.getMessage());
            LOG.debug("SQLState: " + ex.getSQLState());
            LOG.debug("VendorError: " + ex.getErrorCode());
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

    Connection makeConnection(Properties properties) {
        String db_url = properties.getProperty("db_url");
        String username = properties.getProperty("sql_username");
        String password = properties.getProperty("sql_password");

        return makeConnection(db_url, username, password);
    }
}
