package Mar4API;

/**
 * Created by mmadhusoodan on 3/11/14.
 */


import com.facebook.api.Ads_campaign_group;
import org.springframework.web.client.RestTemplate;


public class RestClient
{
    public static void main(String args[])
            throws Exception
    {
        RestTemplate restTemplate = new RestTemplate();
        
        String URI = "https://graph.facebook.com/act_53329000/adcampaign_groups?fields=account_id,id,name,campaign_group_status,objective&access_token="
                + "CAATqGuBpUXABAIMsM5y8T9iOS3EpKZA79bxZA0LNCRrHnAOKyM9tZAej48wHc244zC5871Nl62U5UlfZADVmkh6H5t4V16IWZALODeXgvMsvxyiqGSOHUhdZCjNqk21wHwNEDWhJgkuR9LtZAhzA9ZB0agP4W89tEZBRbcVUGTJSPPSUwLUYjmEh6XQ7X1rL850EZD";
        
        Ads_campaign_group result = restTemplate.getForObject(URI, Ads_campaign_group.class);

        System.out.println("result = " + result);
    }
}