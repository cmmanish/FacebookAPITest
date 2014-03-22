package Mar4API;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

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
    FacebookGraphService uri = new FacebookGraphService(fca, accountId, accessToken);
    long campaignId = 6016078198937l;
    long adsetId = 6016853502737l;
    long adgroupId = 6017258841537l;

    @Test
    public void testGetAdsGroup() throws Exception {

        Ads_adgroup adgroup = uri.getAdsGroup(adgroupId);
        
        log.info(adgroup.getName());
        log.info(adgroup.getAccount_id());
        log.info(adgroup.getId());
        log.info(adgroup.getCampaign_id());
        log.info(adgroup.getAdgroup_status());

        log.info("Targeting ");
        log.info(adgroup.getTargeting().getAge_max());
        log.info(adgroup.getTargeting().getAge_min());
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
        log.info(adSetsList.get(i).getStart_time());

    }

    @Test
    public void testGetAdSet() throws Exception {

        Ads_campaign adCampaign = uri.getAdset(adsetId);

        log.info(adCampaign.getId());
        log.info(adCampaign.getCampaign_group_id());
        log.info(adCampaign.getName());
        log.info(adCampaign.getCampaign_status());
        log.info(adCampaign.getDaily_budget());
        log.info(adCampaign.getLifetime_budget());

    }

    @Test
    public void testGetCampaign() throws Exception {

        Ads_campaign_group adCampaignGroup = uri.getCampaign(campaignId);

        log.info(adCampaignGroup.getAccount_id());
        log.info(adCampaignGroup.getCampaign_group_status());
        log.info(adCampaignGroup.getName());

    }

    @Test
    public void testGetAllCampaigns() throws Exception {

        List<Ads_campaign_group> adCampaignGroupList = uri.getAllCampaigns();
        int i = 0;
        log.info(adCampaignGroupList.get(i).getId());
        log.info(adCampaignGroupList.get(i).getName());
        log.info(adCampaignGroupList.get(i).getCampaign_group_status());

    }

    @Test
    public void testGetAllGroups() throws Exception {

        List<Ads_campaign> adSetsList = uri.getAllAdsets();
        int i = 0;

        log.info(adSetsList.size());
        log.info(adSetsList.get(i).getId());
        log.info(adSetsList.get(i).getName());
        log.info(adSetsList.get(i).getDaily_budget());
        log.info(adSetsList.get(i).getLifetime_budget());
        log.info(adSetsList.get(i).getStart_time());
        log.info(adSetsList.get(i).getEnd_time());
    }

    @Test
    public void testGetAccount() throws Exception {

        Ads_account ads_account = uri.getAccount();
        log.info(ads_account.getAccount_id());
        log.info(ads_account.getName());
        log.info(ads_account.getCurrency());
    }

}
