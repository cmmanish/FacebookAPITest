package Mar4API;

/**
 * Created by mmadhusoodan on 3/11/14.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.client.RestTemplate;

public class MyRestClient {
    private final static Logger log = Logger.getLogger(TestBuildingURI.class);
    
    public static void main(String args[]) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper jsonMapper = new ObjectMapper();

        Map<String, ArrayList<HashMap<String, String>>> targetCategories = new HashMap<String, ArrayList<HashMap<String, String>>>();

        String URI = "https://graph.facebook.com/act_53329000/adcampaign_groups?fields=account_id,id,name,campaign_group_status,objective&access_token="
                + "CAATqGuBpUXABAIMsM5y8T9iOS3EpKZA79bxZA0LNCRrHnAOKyM9tZAej48wHc244zC5871Nl62U5UlfZADVmkh6H5t4V16IWZALODeXgvMsvxyiqGSOHUhdZCjNqk21wHwNEDWhJgkuR9LtZAhzA9ZB0agP4W89tEZBRbcVUGTJSPPSUwLUYjmEh6XQ7X1rL850EZD";

        String response = restTemplate.getForObject(URI, String.class);

        targetCategories = jsonMapper.readValue(response, HashMap.class);

        log.info("targetCategories = " + targetCategories.get("data"));
        
//        for (int i =0;i < targetCategories.size();i++){
//            
//            log.info(targetCategories.get(i).size());
//            
//        }

    }
}