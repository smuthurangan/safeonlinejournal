package com.safe.online.journal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    static Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    public static Connection getRemoteConnection() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String dbName = "journal";
                String userName = "admin";
                String password = "password12345";
                String hostname = "journal.cxaaymm4qwrc.us-east-2.rds.amazonaws.com";
                String port = "3306";
                String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
                logger.debug("Getting remote connection with connection string from environment variables.");
                Connection con = DriverManager.getConnection(jdbcUrl);
                logger.debug("Remote connection successful.");
                return con;
            }
            catch (ClassNotFoundException e) { logger.warn(e.toString());}
            catch (SQLException e) { logger.warn(e.toString());}
        return null;
    }
}
