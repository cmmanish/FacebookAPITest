package Mar4API;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class TestRESTFulServices {
    
    FacebookClientAccount fca = null;
    Long accountId = 53329000l;
    String accessToken = "CAATqGuBpUXABAIMsM5y8T9iOS3EpKZA79bxZA0LNCRrHnAOKyM9tZAej48wHc244zC5871Nl62U5UlfZADVmkh6H5t4V16IWZALOD"
            + "eXgvMsvxyiqGSOHUhdZCjNqk21wHwNEDWhJgkuR9LtZAhzA9ZB0agP4W89tEZBRbcVUGTJSPPSUwLUYjmEh6XQ7X1rL850EZD";

    FacebookGraphService uri = new FacebookGraphService(fca, accountId, accessToken);
    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testGet() {

        

    }

}
