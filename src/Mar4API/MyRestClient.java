package Mar4API;

/**
 * Created by mmadhusoodan on 3/11/14.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.facebook.api.Ads_campaign_group;

public class MyRestClient {
    private final static Logger log = Logger.getLogger(TestBuildingURI.class);
    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper jsonMapper = new ObjectMapper();

    public void abc() {

        // // //////////////////
        //
        // Map<String, ArrayList<HashMap<String, String>>> targetCategories = new HashMap<String, ArrayList<HashMap<String, String>>>();
        //
        // String URI = "https://graph.facebook.com/act_53329000/adcampaign_groups?fields=account_id,id,name,campaign_group_status,objective&access_token="
        // +
        // "CAATqGuBpUXABAIMsM5y8T9iOS3EpKZA79bxZA0LNCRrHnAOKyM9tZAej48wHc244zC5871Nl62U5UlfZADVmkh6H5t4V16IWZALODeXgvMsvxyiqGSOHUhdZCjNqk21wHwNEDWhJgkuR9LtZAhzA9ZB0agP4W89tEZBRbcVUGTJSPPSUwLUYjmEh6XQ7X1rL850EZD";
        //
        // String response = restTemplate.getForObject(URI, String.class);
        //
        // targetCategories = jsonMapper.readValue(response, HashMap.class);
        //
        // log.info("targetCategories = " + targetCategories.get("data"));
        //
        // // for (int i =0;i < targetCategories.size();i++){
        // //
        // // log.info(targetCategories.get(i).size());
        // //
        // // }
    }

    private Map<String, Object> jsonObjectToMap(Object jsonObj) throws Exception {
        String jsonString = this.jsonMapper.writeValueAsString(jsonObj);
        return jsonMapper.readValue(jsonString, HashMap.class);
    }

    public void post() throws Exception {

        Long accountId = 53329000l;
        String ACCESS_TOKEN = "CAATqGuBpUXABAIMsM5y8T9iOS3EpKZA79bxZA0LNCRrHnAOKyM9tZAej48wHc244zC5871Nl62U5UlfZADVmkh6H5t4V16IWZALODeXgvMsvxyiqGSOHUhdZCjNqk21wHwNEDWhJgkuR9LtZAhzA9ZB0agP4W89tEZBRbcVUGTJSPPSUwLUYjmEh6XQ7X1rL850EZD";
        String url = "https://graph.facebook.com/act_53329000/adcampaign_groups&access_token=" + ACCESS_TOKEN;

        Ads_campaign_group campaign = new Ads_campaign_group();

        campaign.setAccount_id(accountId);
        campaign.setName("abc");
        campaign.setCampaign_group_status("Active");
        
        Map<String, Object> queryParameters = jsonObjectToMap(campaign);

        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        HttpStatus status = response.getStatusCode();
        String restCall = response.getBody();
        log.info(status);
        log.info(restCall);
    }

    public static void main(String args[]) throws Exception {

        MyRestClient myClient = new MyRestClient();
        myClient.post();
    }
}