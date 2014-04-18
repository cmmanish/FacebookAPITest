package Mar4Database;

import com.mysql.jdbc.PreparedStatement;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AccessDatabase {

    protected final Logger log = Logger.getLogger(AccessDatabase.class);

    String ACCESS_TOKEN = "access_token";
    static int rowUpdated = 0;

    Connection dbConnection = null;
    PreparedStatement preparedStatement = null;
    ObjectMapper jsonMapper = new ObjectMapper();

    public Connection getMyDBConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

        String host = "localhost";
        String database = "/FacebookDatabase";
        String port = "3306";
        String login = "root";
        String password = "root";
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver).newInstance();

        Connection conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + database, login, password);
        return conn;

    }
    
    public Connection getDBConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

        String host = "tesla";
        String database = "/marin";
        String port = "3306";
        String login = "marin";
        String password = "wawptw";
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver).newInstance();

        Connection conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + database, login, password);
        return conn;

    }
    
 // returns ArrayList of Behaviours <id , name > format
    public ArrayList<HashMap<String, String>> getPartnerCategoriesFromDatabase(String audienceTemplate) throws Exception {

        ArrayList<HashMap<String, String>> behaviorList =  new ArrayList<HashMap<String, String>>();
        String partnerCategories = null;
         try {
            log.info("Database connection established");
            Statement statment = getDBConnection().createStatement();
            statment.executeQuery("use marin"); 

            String queryStr = "select partner_categories from facebook_targets_v2, templates  where templates.`publisher_object_id` = facebook_targets_v2.facebook_target_id "
                    + "and templates.template_name = '" + audienceTemplate + "' ;";
            statment.executeQuery(queryStr);
            ResultSet rs = statment.getResultSet();
            while (rs.next()) {
                partnerCategories = rs.getString("partner_categories");
            }
            rs.close();
            statment.close();
            behaviorList =jsonMapper.readValue(partnerCategories, ArrayList.class);
    
        }
        catch (Exception e) {
            System.err.println();
            log.info(e);
        }
        finally {
            

            }
       

        return behaviorList;
    }

    public ArrayList < HashMap<String, String >> getFacebookCategoriesFromDatabase(String audienceTemplate) throws Exception {
    
        Map<String, ArrayList < HashMap<String, String >>> response = new HashMap<String, ArrayList < HashMap<String, String >>>();
        
        ArrayList < HashMap<String, String >> behaviorList = new ArrayList < HashMap<String, String >>();
        String facebookCategories = "";
        try {
            log.info("Database connection established");
            Statement statment = getDBConnection().createStatement();
            statment.executeQuery("use marin");// client_id=" + clientId);

            String queryStr = "select  facebook_categories from facebook_targets_v2, templates  where templates.`publisher_object_id` = facebook_targets_v2.facebook_target_id "
                    + "and templates.template_name = '" + audienceTemplate + "' ;";
            statment.executeQuery(queryStr);
            ResultSet rs = statment.getResultSet();
            while (rs.next()) {
            facebookCategories = rs.getString("facebook_categories");
            }
            rs.close();
            statment.close();
            response = jsonMapper.readValue(facebookCategories, Map.class);
            behaviorList = response.get("behaviors") ;
            
        }
        catch (Exception e) {
            System.err.println();
            log.info(e);
        }
        finally {
            if (getDBConnection() != null) {
                try {
                    getDBConnection().close();
                    log.info("Database connection terminated");
                }
                catch (Exception e) { /* ignore close errors */
                }

            }
        }
        return behaviorList;
    }

    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }

    public String getAccessTokenFromDatabase(String accountId) throws Exception {

        String accessToken = getFacebookAccount(accountId).get(ACCESS_TOKEN);
        log.info(accessToken);
        return accessToken;
    }

    public Map<String, String> getFacebookAccount(String account_id) throws Exception {

        Map<String, String> campaign = new HashMap<String, String>();

        try {
            log.info("Database connection established");
            Statement statment = getDBConnection().createStatement();
            String query = "select  * from facebook_accounts where account_id = '" + account_id + "';";//
            log.info("Querry:  " + query);
            statment.executeQuery(query);
            ResultSet rs = statment.getResultSet();
            while (rs.next()) {
                campaign.put("facebook_account_id", rs.getString("facebook_account_id"));
                campaign.put("account_id", rs.getString("account_id"));
                campaign.put("username", rs.getString("username"));
                campaign.put("password", rs.getString("password"));
                campaign.put("access_token", rs.getString("access_token"));
            }
            rs.close();
            statment.close();
        }
        catch (Exception e) {
            System.err.println();
            log.info(e);
        }
        finally {
            if (getDBConnection() != null) {
                try {
                    getDBConnection().close();
                    log.info("Database connection terminated");
                }
                catch (Exception e) { /* ignore close errors */
                }

            }
        }
        return campaign;
    }
    
    public int  getFacebookCampaignId(long campaignGroupId) throws NullPointerException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        
       int facebookCampaignId = 0;
        try {
            log.info("Database connection established");
            dbConnection = getDBConnection();
            Statement statment = getDBConnection().createStatement();
            String query = "select facebook_campaign_id from facebook_campaigns where ext_id = '" + campaignGroupId + "';";//
            log.info("Query:  " + query);
            statment.executeQuery(query);
            ResultSet rs = statment.getResultSet();

            while (rs.next()) {
                facebookCampaignId = rs.getInt("facebook_campaign_id");
            }
            rs.close();
            preparedStatement.close();
        }
        catch (Exception e) {
            System.err.println();
            log.info(e);
        }
        finally {
            if (getDBConnection() != null) {
                try {
                    getDBConnection().close();
                    log.info("Database connection terminated");
                }
                catch (Exception e) { /* ignore close errors */
                }

            }
        }
        return facebookCampaignId ;// Integer.parseInt(facebookCampaignId);
    }

    public Map<String, String> readFacebookCampaign(String campaignName, long accountId) throws Exception {

        Map<String, String> campaign = new HashMap<String, String>();

        try {
            log.info("Database connection established");
            dbConnection = getDBConnection();
            String selectTableSQL = "select * from facebook_campaigns where account_id = ? and campaign_name = ?";
            preparedStatement = (PreparedStatement) dbConnection.prepareStatement(selectTableSQL);
            preparedStatement.setLong(1, accountId);
            preparedStatement.setString(2, campaignName);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                campaign.put("facebook_campaign_id", rs.getString("facebook_campaign_id"));
                campaign.put("campaign_name", rs.getString("campaign_name"));
                campaign.put("account_id", rs.getString("account_id"));
                campaign.put("ext_id", rs.getString("ext_id"));
                campaign.put("campaign_status", rs.getString("campaign_status"));
            }
            rs.close();
            preparedStatement.close();
        }
        catch (Exception e) {
            System.err.println();
            log.info(e);
        }
        finally {
            if (getDBConnection() != null) {
                try {
                    getDBConnection().close();
                    log.info("Database connection terminated");
                }
                catch (Exception e) { /* ignore close errors */
                }

            }
        }
        return campaign;
    }

    public int updateFacebookCampaign(String campaignName, long accountId, String status) throws Exception {

        Map<String, String> campaign = readFacebookCampaign(campaignName, accountId);
        String facebookCampaignId = campaign.get("facebook_campaign_id");

        if (facebookCampaignId != null) {
            String updateTableSQL = "update facebook_campaigns set campaign_status = ? where facebook_campaign_id = '" + facebookCampaignId + "' ;";
            dbConnection = getDBConnection();
            preparedStatement = (PreparedStatement) dbConnection.prepareStatement(updateTableSQL);
            preparedStatement.setString(1, status);
            rowUpdated = preparedStatement.executeUpdate();
        }
        return rowUpdated;
    }

    public int addFacebookCampaign(String campaignName, long accountId, long extId, String status) throws Exception {

        String insertTableSQL = " insert into facebook_campaigns" + 
        "(campaign_name, account_id, ext_id, campaign_status,creation_date) " + "values " + "(?,?,?,?,?)";

        try {
            dbConnection = getDBConnection();
            preparedStatement = (PreparedStatement) dbConnection.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, campaignName);
            preparedStatement.setLong(2, accountId);
            preparedStatement.setLong(3, extId);
            preparedStatement.setString(4, status);
            preparedStatement.setTimestamp(5, getCurrentTimeStamp());
            rowUpdated = preparedStatement.executeUpdate();

        }
        catch (SQLException e) {

            System.out.println(e.getMessage());

        }
        finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }
        return rowUpdated++;
    }

    public int addFacebookGroup(int facebook_campaign_id, String groupName, long extId, String status,long dailyBudget) throws Exception {

        String insertTableSQL ="insert into facebook_groups (facebook_campaign_id, group_name, ext_id, group_status, daily_budget, creation_date)  "
                + "values"+ "(?, ?, ?, ?, ?, ?) ; ";
               
        log.info(insertTableSQL);
        try {
            dbConnection = getDBConnection();
            preparedStatement = (PreparedStatement) dbConnection.prepareStatement(insertTableSQL);
            preparedStatement.setInt(1, facebook_campaign_id);
            preparedStatement.setString(2,  groupName);
            preparedStatement.setLong(3, extId);
            preparedStatement.setString(4, status);
            preparedStatement.setLong(5, dailyBudget);
            preparedStatement.setTimestamp(6, getCurrentTimeStamp());
            rowUpdated = preparedStatement.executeUpdate();
            
        }
        catch (SQLException e) {

            System.out.println(e.getMessage());

        }
        finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }
        return rowUpdated;
    }
}
