package com.safe.online.journal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
@Component
public class LoginHelper {
    static Logger logger = LoggerFactory.getLogger(ConnectionPool.class);

    public long getUserID(String user) {
        long userId = -1;
        Connection pool = null;
        Statement stat = null;
        String SQL = null;
        ResultSet result = null;
        try {
            pool = ConnectionPool.getRemoteConnection();
            stat = pool.createStatement();
            SQL = "SELECT User_ID FROM journal.Users WHERE Email_ID='"+user+"'";
            result = stat.executeQuery(SQL);
            while(result.next()) {
                userId = result.getLong(1);
            }
        }catch(Exception e) {
            logger.warn("Exception while validating users : " + e);
        } finally {
            try {
                if(result != null) result.close();
            } catch (SQLException e) {
                logger.warn("Exception while closing resultset : " + e);
            }
            try {
                if(stat != null) stat.close();
            } catch (SQLException e) {
                logger.warn("Exception while closing statement : " + e);
            }
//            try {
//                if(pool != null) pool.close();
//            } catch (SQLException e) {
//                logger.warn("Exception while closing connection : " + e);
//            }
        }
        return userId;
    }

    public boolean isValidUser(String user, String password) {
        boolean passwordMatch = false;
        Connection pool = null;
        Statement stat = null;
        String SQL = null;
        ResultSet result = null;
        try {
            pool = ConnectionPool.getRemoteConnection();
            stat = pool.createStatement();
            SQL = "SELECT Password,Salt FROM journal.Users WHERE Email_ID='"+user+"'";
            result = stat.executeQuery(SQL);
            while(result.next()) {
                String passwordFromDB = result.getString(1);
                String salt = result.getString(2);
                passwordMatch = PasswordUtils.verifyUserPassword(password, passwordFromDB, salt);
            }
        }catch(Exception e) {
            logger.warn("Exception while validating users : " + e);
        } finally {
            try {
                if(result != null) result.close();
            } catch (SQLException e) {
                logger.warn("Exception while closing resultset : " + e);
            }
            try {
                if(stat != null) stat.close();
            } catch (SQLException e) {
                logger.warn("Exception while closing statement : " + e);
            }
//            try {
//                if(pool != null) pool.close();
//            } catch (SQLException e) {
//                logger.warn("Exception while closing connection : " + e);
//            }
        }
        return passwordMatch;
    }

    public boolean checkAlreadyExist(String user) {
        boolean userMatch = false;
        Connection pool = null;
        PreparedStatement stat = null;
        String SQL = null;
        ResultSet result = null;
        try {
            pool = ConnectionPool.getRemoteConnection();
            SQL = "SELECT Email_ID FROM journal.Users WHERE Email_ID=?";
            stat = pool.prepareStatement(SQL);
            stat.setString(1,user);
            result = stat.executeQuery();
            while(result.next()) {
                userMatch = result.getString(1) == null ? false : true;
            }
        }catch(Exception e) {
            logger.warn("Exception while validating users : " + e);
        } finally {
            try {
                if(result != null) result.close();
            } catch (SQLException e) {
                logger.warn("Exception while closing resultset : " + e);
            }
            try {
                if(stat != null) stat.close();
            } catch (SQLException e) {
                logger.warn("Exception while closing statement : " + e);
            }
//            try {
//                if(pool != null) pool.close();
//            } catch (SQLException e) {
//                logger.warn("Exception while closing connection : " + e);
//            }
        }
        return userMatch;
    }

    public void createUser(String user, String password) {
        Connection pool = null;
        PreparedStatement stat = null;
        String SQL = null;
        ResultSet result = null;
        try {
            pool = ConnectionPool.getRemoteConnection();
            SQL = "INSERT INTO journal.Users (Email_ID,Password,Salt) VALUES (?,?,?)";
            stat = pool.prepareStatement(SQL);
            stat.setString(1, user);
            // Generate Salt. The generated value can be stored in DB.
            String salt = PasswordUtils.getSalt(30);
            // Protect user's password. The generated value can be stored in DB.
            String mySecurePassword = PasswordUtils.generateSecurePassword(password, salt);
            stat.setString(2, mySecurePassword);
            stat.setString(3, salt);
            stat.execute();
        }catch(Exception e) {
            logger.warn("Exception while validating users : " + e);
        } finally {
            try {
                if(result != null) result.close();
            } catch (SQLException e) {
                logger.warn("Exception while closing resultset : " + e);
            }
            try {
                if(stat != null) stat.close();
            } catch (SQLException e) {
                logger.warn("Exception while closing statement : " + e);
            }
//            try {
//                if(pool != null) pool.close();
//            } catch (SQLException e) {
//                logger.warn("Exception while closing connection : " + e);
//            }
        }
    }
}
