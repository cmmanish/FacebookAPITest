package Mar4API;

import Mar4Database.AccessDatabase;

import com.facebook.api.Ads_account;
import com.facebook.api.Ads_adgroup;
import com.facebook.api.Ads_campaign;
import com.facebook.api.Ads_campaign_group;
import com.facebook.api.Ads_connection;
import org.apache.log4j.Logger;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DownloadToDatabase {
    private final Logger log = Logger.getLogger(DownloadToDatabase.class);

    FacebookClientAccount fca = null;
    Long accountId = 53329000l;

    String accessToken = "CAAEz5omIYVQBAC1cGXJUy88bfp0a0pGykvSVoDVxjjWV4cDmAz9PlOCjF3AaJYo4oBNLS72dZBZCkzIb8BTpRYPZA216EZAo"
            + "TcryXQqU3X5zXK2f6ZCOZA3dN7KnHjz4GcyoA3pDasttSscfLoy66XwR6yflzhPZCK5AC55hD6RZA0sI0q3kyqtW2HcqWTocA2gZD";


    FacebookGraphService uri = new FacebookGraphService(fca, accountId, accessToken);

    long campaignId = 6016078198937l;
    long adsetId = 6017202488337l;
    long adgroupId = 6017202505337l;

    public DownloadToDatabase() throws Exception {


    }

    public void downloadGroupsForCampaign(long campaignId) throws Exception {

        List<Ads_campaign> adCampaignList = uri.getAllAdsetsInCampaign(campaignId);
        int size = adCampaignList.size();
        AccessDatabase accessDB = new AccessDatabase();

        for (int i = 0; i < size; i++) {
            log.info(adCampaignList.get(i).getId());
            log.info(adCampaignList.get(i).getName());
            log.info(adCampaignList.get(i).getCampaign_group_id());
            log.info(adCampaignList.get(i).getStart_time());
            log.info(adCampaignList.get(i).getDaily_budget());
            log.info(adCampaignList.get(i).getCampaign_status());

            long campaignGroupId = adCampaignList.get(i).getCampaign_group_id();
/*            int facebookCampaignId = accessDB.getFacebookCampaignId(campaignGroupId);
            accessDB.addFacebookGroup(facebookCampaignId, adCampaignList.get(i).getName(), adCampaignList.get(i).getId(),
                    adCampaignList.get(i).getCampaign_status(), adCampaignList.get(i).getDaily_budget());
                    */
        }
    }

    @Test
    public void testDownloadAllCampaigns() throws Exception {

        List<Ads_campaign_group> adCampaignGroupList = uri.getAllCampaigns();

        int size = adCampaignGroupList.size();
        log.info("Total Campaigns: " + size);

        AccessDatabase accessDB = new AccessDatabase();
        for (int i = 0; i < size; i++) {
            accessDB.addFacebookCampaign(adCampaignGroupList.get(i).getName(), adCampaignGroupList.get(i).getAccount_id(), adCampaignGroupList.get(i).getId(), adCampaignGroupList.get(i)
                    .getCampaign_group_status());
        }

    }

    @Test
    public void testDownloadGroupsForListOfCampaigns() throws Exception {

        List<Long> campaignList = new ArrayList<Long>();

        List<Ads_campaign_group> adCampaignGroupList = uri.getAllCampaigns();

        int size = adCampaignGroupList.size();
        //        log.info("Total Campaigns: " + size);

        adCampaignGroupList.get(0).getId();

        for (int i = 0; i < size; i++) {
            campaignList.add(adCampaignGroupList.get(i).getId());
        }
        log.info("campaignList size " + campaignList.size());

        for (long campaign : campaignList)
            downloadGroupsForCampaign(campaign);
    }

}
