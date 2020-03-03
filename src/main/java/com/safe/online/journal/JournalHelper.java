package com.safe.online.journal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JournalHelper {
    static Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    @Autowired
    LoginHelper helper;
    public void createJournal(String journal, String user) {
        Connection pool = null;
        PreparedStatement stat = null;
        String SQL = null;
        ResultSet result = null;
        try {
            String pattern = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat(pattern);
            pool = ConnectionPool.getRemoteConnection();
            long userid = helper.getUserID(user);
            SQL = "INSERT INTO journal.Journal (JournalObject,User_ID,Create_Date) VALUES (?,?,?)";
            stat = pool.prepareStatement(SQL);
            stat.setString(1, journal);
            stat.setLong(2, userid);
            stat.setString(3,simpleDateFormat.format(new Date()));
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

    public List<Journal> findAll(String user) {
        Connection pool = null;
        PreparedStatement stat = null;
        String SQL = null;
        ResultSet result = null;
        List<Journal> returnList = new ArrayList<Journal>();
        try {
            pool = ConnectionPool.getRemoteConnection();
            long userid = helper.getUserID(user);
            SQL = "SELECT id,JournalObject,Create_Date FROM journal.Journal WHERE User_ID=? order by UNIX_TIMESTAMP(Create_Date) DESC ";
            stat = pool.prepareStatement(SQL);
            stat.setLong(1,userid);
            result = stat.executeQuery();
            while(result.next()) {
                Journal journal = new Journal();
                int id = result.getInt(1);
                journal.setId(id);
                String journalStr = result.getString(2);
                journalStr = journalStr != null && journalStr.length() > 100 ? journalStr.substring(0,100) : journalStr;
                journal.setComment(journalStr);
                journal.setCreateTime(result.getString(3));
                returnList.add(journal);
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
        return returnList;
    }

    public Journal find(String user, int idJournal) {
        Connection pool = null;
        PreparedStatement stat = null;
        String SQL = null;
        ResultSet result = null;
        Journal journal = new Journal();
        try {
            pool = ConnectionPool.getRemoteConnection();
            long userid = helper.getUserID(user);
            SQL = "SELECT id,JournalObject,Create_Date FROM journal.Journal WHERE User_ID=? AND id=? order by UNIX_TIMESTAMP(Create_Date) DESC ";
            stat = pool.prepareStatement(SQL);
            stat.setLong(1,userid);
            stat.setInt(2,idJournal);
            result = stat.executeQuery();
            while(result.next()) {
                int id = result.getInt(1);
                journal.setId(id);
                String journalStr = result.getString(2);
                journalStr = journalStr != null && journalStr.length() > 100 ? journalStr.substring(0,100) : journalStr;
                journal.setComment(journalStr);
                journal.setCreateTime(result.getString(3));
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
        return journal;
    }

    public void delete(String user, int idJournal) {
        Connection pool = null;
        PreparedStatement stat = null;
        String SQL = null;
        ResultSet result = null;
        Journal journal = new Journal();
        try {
            pool = ConnectionPool.getRemoteConnection();
            long userid = helper.getUserID(user);
            SQL = "Delete From journal.Journal WHERE User_ID=? AND id=? ";
            stat = pool.prepareStatement(SQL);
            stat.setLong(1,userid);
            stat.setInt(2,idJournal);
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
