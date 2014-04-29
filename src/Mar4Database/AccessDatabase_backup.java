package Mar4Database;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AccessDatabase_backup {
    
    protected final Logger log = Logger.getLogger(AccessDatabase_backup.class);
    
    public void testMyLocalSQL() throws Exception {

        String database = "127.0.0.1";
        String port = "3306";
        String login = "root";
        String password = "root";

        String publisherCampaignId = "";
        String campaignName = "";
        String clientAccountId = "";
        String extId = "";
        String campaignStatus = "";
        
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver).newInstance();

        Connection conn = DriverManager.getConnection("jdbc:mysql://" + database + ":" + port + "/", login, password);

        try {
            log.info("Database connection established");
            Statement statment = conn.createStatement();
            statment.executeQuery("use Mar4Database");
            String query = "select  * from facebook_campaigns;";//
            statment.executeQuery(query);
            ResultSet rs = statment.getResultSet();
            while (rs.next()) {
                publisherCampaignId = rs.getString("publisher_campaign_id");
                campaignName = rs.getString("campaign_name");
                clientAccountId = rs.getString("client_account_id");
                extId = rs.getString("ext_id");
                campaignStatus = rs.getString("publisher_campaign_status");
            }
            rs.close();
            statment.close();
        }
        catch (Exception e) {
            System.err.println();
            log.info(e);
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                    log.info("Database connection terminated");
                }
                catch (Exception e) { /* ignore close errors */
                }

            }
        }
    }
    
    public void testMySQL() throws Exception {

        String database = "golf.marinsoftware.com";
        String port = "3306";
        String login = "marin";
        String password = "wawptw";

        String accountId = "";
        String accessToken = "";
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver).newInstance();

        Connection conn = DriverManager.getConnection("jdbc:mysql://" + database + ":" + port + "/", login, password);

        try {
            log.info("Database connection established");
            Statement statment = conn.createStatement();
            statment.executeQuery("use marin");
            String query = "select account_id, access_token from facebook_client_accounts where  facebook_client_accounts.`facebook_client_account_id` in (select client_account_id from  "+
            "publisher_client_accounts where publisher_account_id in (select publisher_account_id from publisher_accounts where client_id = 101));";//
            statment.executeQuery(query);
            ResultSet rs = statment.getResultSet();
            while (rs.next()) {
                accountId = rs.getString("account_id");
                if (accountId.equalsIgnoreCase("")) {
                    ;
                }
                else {
                    accountId = rs.getString("account_id");
                    accessToken = rs.getString("access_token");
                    log.info(accessToken);
                }
            }
            rs.close();
            statment.close();
        }
        catch (Exception e) {
            System.err.println();
            log.info(e);
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                    log.info("Database connection terminated");
                }
                catch (Exception e) { /* ignore close errors */
                }

            }
        }
    }
   
}
