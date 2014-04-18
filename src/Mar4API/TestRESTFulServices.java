package Mar4API;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class TestRESTFulServices {
    private final static Logger log = Logger.getLogger(TestBuildingURI.class);
    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper jsonMapper = new ObjectMapper();

    @Test
    public void testGetFacebookCategories() throws JsonParseException, JsonMappingException, IOException {

        Map<String, ArrayList<HashMap<String, String>>> adTargetingCategory = new HashMap<String, ArrayList<HashMap<String, String>>>();
        String URI = "https://graph.facebook.com/search?remove_demo_categories=1&type=adTargetingCategory&access_token="
                + "CAACzCX4Hg5sBAHYex54uZC83neUfeJeBiXCb6sp75jrkDFRsAtmvshJYydzLYyXIUdJ2J7Mba2YQtjPOzrZCEjPFNXu7xPtUNJV1hDTkhrD6nXWfM7kqNl4snyWU2ZAIDf5ssfxZCEDZC8p4TZB7Hca9Q40fndtxmWJz7WruZC21ADjJTPyoGSE5EI6gVxQUO0ZD";
        String response = restTemplate.getForObject(URI, String.class);
        adTargetingCategory = jsonMapper.readValue(response, HashMap.class);

        //log.info("targetCategories = " + adTargetingCategory.get("data"));

        ArrayList<HashMap<String, String>> facebookCategories = adTargetingCategory.get("data");
        int size = facebookCategories.size();
        log.info(facebookCategories.size());

        Map<String, ArrayList<String>> typeNamePair = new HashMap<String, ArrayList<String>>();
        ArrayList<String> child = new ArrayList<String>();
        
        File file = new File("/Users/mmadhusoodan/Desktop/facebookCategory.txt");

        for (int i = 0; i < size - 1; i++) {

            String key = facebookCategories.get(i).get("type");
            String value = facebookCategories.get(i).get("name");

            child.add(value);
            typeNamePair.put(key, child);

        }
        log.info(typeNamePair.keySet().size());
        
     // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
 //       bw.write(content);
        
        for (String key : typeNamePair.keySet()) {
            //log.info("Key: " + key + ", Value: " + typeNamePair.get(key));
            //bw.write(key + ":" + typeNamePair.get(key)+ "\\n");
            bw.write(key);bw.write("\n"); 
            
            bw.write(typeNamePair.get(key)+ "");
            bw.write("--------------");
            bw.write("\n"); 
        }
        bw.close();
        
    }

    @Test
    public void testGetadCampaignGroups() throws JsonParseException, JsonMappingException, IOException {

        Map<String, ArrayList<HashMap<String, String>>> adcampaign_groups = new HashMap<String, ArrayList<HashMap<String, String>>>();

        String URI = "https://graph.facebook.com/act_53329000/adcampaign_groups?fields=account_id,id,name,campaign_group_status,objective&access_token="
                + "CAATqGuBpUXABAIMsM5y8T9iOS3EpKZA79bxZA0LNCRrHnAOKyM9tZAej48wHc244zC5871Nl62U5UlfZADVmkh6H5t4V16IWZALODeXgvMsvxyiqGSOHUhdZCjNqk21wHwNEDWhJgkuR9LtZAhzA9ZB0agP4W89tEZBRbcVUGTJSPPSUwLUYjmEh6XQ7X1rL850EZD";
        String response = restTemplate.getForObject(URI, String.class);

        adcampaign_groups = jsonMapper.readValue(response, HashMap.class);

        log.info("targetCategories = " + adcampaign_groups.get("data"));

    }

}
