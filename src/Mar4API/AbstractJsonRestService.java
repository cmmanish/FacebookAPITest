package Mar4API;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

/**
 * Created by mmadhusoodan on 3/7/14.
 * <p/>
 * Base class for service calls to json/rest APIs
 * It would have the GET POST DELETE
 */
public abstract class AbstractJsonRestService {

    private final Logger log = Logger.getLogger(AbstractJsonRestService.class);

    protected ObjectMapper jsonMapper;
    protected RestTemplate restTemplate;

    public AbstractJsonRestService() {

        this.jsonMapper = new ObjectMapper();
        restTemplate = new RestTemplate();

    }

    protected <T> T GETCall(URI uri, Class<T> returnType) throws JsonGenerationException, JsonMappingException, IOException {
        T response = this.restTemplate.getForObject(uri, returnType);

        return response;

    }

    protected <T> T POSTCall(URI uri, Class<T> returnClass, Object upload) throws JsonGenerationException, JsonMappingException, IOException {
        T response = this.restTemplate.postForObject(uri, upload, returnClass);
        
        return response;
    }
}
