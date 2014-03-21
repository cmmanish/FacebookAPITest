package Mar4API;

import org.junit.Test;

public class TestBuildingURI {

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

        uri.getAdsGroup(adgroupId);

    }

    
    @Test
    public void testGetAdSetsListInCampaign() throws Exception {

        uri.getAllAdsetsInCampaign(campaignId);

    }

    
    @Test
    public void testGetAdSet() throws Exception {

        uri.getAdset(adsetId);

    }

    @Test
    public void testGetCampaign() throws Exception {

        uri.getCampaign(campaignId);

    }

    @Test
    public void testGetAccount() throws Exception {

        //uri.getAccount();
    }

    @Test
    public void testGetAllCampaigns() throws Exception {

        uri.getAllCampaigns();

    }

    @Test
    public void testGetAllGroups() throws Exception {

        uri.getAllAdsets();
    }

    //
    // uri.getAllAdsetsURI();
    // uri.getCampaign(campaignId);
    // uri.getAdset(adsetId);
}
