package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class AccessDatabase {
    
    protected final Logger log = Logger.getLogger(AccessDatabase.class);
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

    public static void main(String[] args) throws Exception {
        
        AccessDatabase helloDb = new AccessDatabase();
        helloDb.testMySQL();
        // TODO Auto-generated method stub

    }

}
