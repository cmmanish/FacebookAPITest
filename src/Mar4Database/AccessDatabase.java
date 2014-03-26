package Mar4Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mysql.jdbc.PreparedStatement;

public class AccessDatabase {

    protected final Logger log = Logger.getLogger(AccessDatabase.class);

    String ACCESS_TOKEN = "access_token";
    static int rowUpdated = 0;

    Connection dbConnection = null;
    PreparedStatement preparedStatement = null;

    public Connection getDBConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

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

        String insertTableSQL = " insert into facebook_campaigns" + "(campaign_name, account_id, ext_id, campaign_status,creation_date) " + "values " + "(?,?,?,?,?)";

        try {
            dbConnection = getDBConnection();
            preparedStatement = (PreparedStatement) dbConnection.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, campaignName);
            preparedStatement.setLong(2, accountId);
            preparedStatement.setLong(3, extId);
            preparedStatement.setString(4, status);
            preparedStatement.setTimestamp(5, getCurrentTimeStamp());
            rowUpdated = preparedStatement.executeUpdate();
            log.info("Record is inserted into facebook_campaigns table! " + rowUpdated);

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

}
