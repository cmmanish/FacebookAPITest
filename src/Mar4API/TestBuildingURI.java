package Mar4API;


import org.junit.Test;

public class TestBuildingURI {

    FacebookClientAccount fca = null;
    Long accountId = 53329000l;
    String accessToken = "CAATqGuBpUXABAIMsM5y8T9iOS3EpKZA79bxZA0LNCRrHnAOKyM9tZAej48wHc244zC5871Nl62U5UlfZADVmkh6H5t4V16IWZALOD"
            + "eXgvMsvxyiqGSOHUhdZCjNqk21wHwNEDWhJgkuR9LtZAhzA9ZB0agP4W89tEZBRbcVUGTJSPPSUwLUYjmEh6XQ7X1rL850EZD";

    FacebookGraphService uri = new FacebookGraphService(fca, accountId, accessToken);

    long campaignId = 6016853428337l;
    long adsetId = 6016853502737l;



    @Test
    public void testBuildAccountURI() throws Exception {

       //uri.getAccountURI();
    }

    @Test
    public void testBuildAllCampaignsURI() throws Exception {

       // uri.getAllCampaignsURI();


    }
    
    @Test
    public void testBuildAllGroupsURI() throws Exception {

       uri.getAllAdsetsURI();
    }



//
//        uri.getAllAdsetsURI();
//        uri.getCampaign(campaignId);
//        uri.getAdset(adsetId);
    }


