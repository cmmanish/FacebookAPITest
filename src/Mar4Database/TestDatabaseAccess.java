package Mar4Database;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Map;

public class TestDatabaseAccess {
    private final Logger log = Logger.getLogger(TestDatabaseAccess.class);

    private AccessDatabase accessDb = new AccessDatabase();

    @Test
    public void testAccessToken() throws Exception {

        String accessToken = accessDb.getFacebookAccount("53329000").get("access_token");
        log.info(accessToken);

    }

    @Test
    public void testReadFacebookCampaign() throws Exception {

        Map<String, String> campaign = accessDb.readFacebookCampaign("Auto_PagePostBulkupload", 53329000);

        if (campaign.size() == 0) {
            log.info("Campaign Not found in Database ");    
        }
        
        else {
            log.info("campaignName: " + campaign.get("campaign_name"));
            log.info("extId: " + campaign.get("ext_id"));
            log.info("campaignStatus: " + campaign.get("campaign_status"));
        }
    }

    @Test
    public void testWriteFacebookCampaign() throws Exception {

        accessDb.addFacebookCampaign("Auto_PagePostBulkupload", 53329000, 6016078203737l, "ACTIVE");

    }
    
    @Test
    public void testUpdateFacebookCampaign() throws Exception {

        accessDb.updateFacebookCampaign("Auto_PagePostBulkupload", 53329000, "PAUSED");

    }
    

}
