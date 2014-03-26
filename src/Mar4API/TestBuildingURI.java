package Mar4API;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import Mar4Database.AccessDatabase;

import com.facebook.api.Ads_account;
import com.facebook.api.Ads_adgroup;
import com.facebook.api.Ads_campaign;
import com.facebook.api.Ads_campaign_group;

public class TestBuildingURI {
    private final Logger log = Logger.getLogger(TestBuildingURI.class);

    FacebookClientAccount fca = null;
    Long accountId = 53329000l;

    String accessToken = "CAATqGuBpUXABAIMsM5y8T9iOS3EpKZA79bxZA0LNCRrHnAOKyM9tZAej48wHc244zC5871Nl62U5UlfZADVmkh6H5t4V16IWZALOD"
            + "eXgvMsvxyiqGSOHUhdZCjNqk21wHwNEDWhJgkuR9LtZAhzA9ZB0agP4W89tEZBRbcVUGTJSPPSUwLUYjmEh6XQ7X1rL850EZD";

    AccessDatabase helloDb = new AccessDatabase();
    // String accessToken = "";

    FacebookGraphService uri = new FacebookGraphService(fca, accountId, accessToken);

    public TestBuildingURI() throws Exception {
        // accessToken = helloDb.getFacebookAccounts("53329000").get("access_token");

    }

    long campaignId = 6017202486337l;
    long adsetId = 6017202488337l;
    long adgroupId = 6017202505337l;

    @Test
    public void testGetAdsGroup() throws Exception {

        Ads_adgroup adgroup = uri.getAdsGroup(adgroupId);

        log.info(adgroup.getName());
        log.info(adgroup.getAccount_id());
        log.info(adgroup.getId());
        log.info(adgroup.getCampaign_id());
        log.info(adgroup.getAdgroup_status());

        log.info("Targeting: ========================== ");
        log.info(adgroup.getTargeting().getAge_max());
        log.info(adgroup.getTargeting().getAge_min());
        for (String country : adgroup.getTargeting().getCountries())
            log.info(country);

        log.info(adgroup.getBid_type());
        log.info(adgroup.getBid_info());
    }

    @Test
    public void testGetAllAdSetsInCampaign() throws Exception {

        List<Ads_campaign> adSetsList = uri.getAllAdsetsInCampaign(campaignId);
        int i = 0;

        log.info(adSetsList.size());
        log.info(adSetsList.get(i).getAccount_id());
        log.info(adSetsList.get(i).getName());
        log.info(adSetsList.get(i).getCampaign_group_id());
        log.info(adSetsList.get(i).getStart_time());
    }

    @Test
    public void testGetAdSet() throws Exception { // getGroup()

        Ads_campaign adCampaign = uri.getAdset(adsetId);

        log.info(adCampaign.getId());
        log.info(adCampaign.getCampaign_group_id());
        log.info(adCampaign.getName());
        log.info(adCampaign.getCampaign_status());
        log.info(adCampaign.getDaily_budget());
        log.info(adCampaign.getStart_time());
        log.info(adCampaign.getEnd_time());
    }

    @Test
    public void testGetCampaign() throws Exception {

        Ads_campaign_group adCampaignGroup = uri.getCampaign(campaignId);

        log.info("CAMPAIGN LEVEL: ");
        log.info("Account: " + adCampaignGroup.getAccount_id());
        log.info("ID: " + adCampaignGroup.getId());
        log.info("Name: " + adCampaignGroup.getName());
        log.info("Status: " + adCampaignGroup.getCampaign_group_status());
    }

    @Test
    public void testGetAllCampaigns() throws Exception {

        List<Ads_campaign_group> adCampaignGroupList = uri.getAllCampaigns();

        int size = adCampaignGroupList.size();
        log.info("Total Campaigns: " + size);

    }

    @Test
    public void testWriteCampaignsToDatabase() throws Exception {

        List<Ads_campaign_group> adCampaignGroupList = uri.getAllCampaigns();

        int size = adCampaignGroupList.size();
        log.info("Total Campaigns: " + size);

        AccessDatabase accessDB = new AccessDatabase();
        for (int i = 0; i < size; i++) {
            accessDB.addFacebookCampaign(
                    adCampaignGroupList.get(i).getName(), 
                    adCampaignGroupList.get(i).getAccount_id(), 
                    adCampaignGroupList.get(i).getId(), 
                    adCampaignGroupList.get(i).getCampaign_group_status());
        }

    }

    @Test
    public void testGetAllGroups() throws Exception {

        List<Ads_campaign> adSetsList = uri.getAllAdsets();
        log.info(adSetsList.size());
        int i = 0;

        log.info(adSetsList.get(i).getId());
        log.info(adSetsList.get(i).getName());
        log.info(adSetsList.get(i).getDaily_budget());
        log.info(adSetsList.get(i).getLifetime_budget());
        log.info(adSetsList.get(i).getStart_time());
        log.info(adSetsList.get(i).getEnd_time());
    }

    @Test
    public void testGetAccount() throws Exception {

        // Ads_account ads_account = uri.getAccount();
        // log.info(ads_account.getAccount_id());
        // log.info(ads_account.getName());
        // log.info(ads_account.getCurrency());
    }

}
