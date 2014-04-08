package Mar4API;

import org.apache.log4j.Logger;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * A factory to build facebook uri by given params and path components
 * @author xwang
 */
public class FacebookURIBuilderFactory  {
    private final Logger log = Logger.getLogger(FacebookURIBuilderFactory.class);

    private static String ACCESS_TOKEN_KEY = "access_token";
    private final String accessToken;

    public FacebookURIBuilderFactory(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Base Facebook url builder constructor by setting only the base path
     */
    public FacebookURIBuilder createURIBuilder() throws URISyntaxException {
    	FacebookURIBuilder uriBuilder = new FacebookURIBuilder();
        return uriBuilder;
    }

    /**
     * Facebook Url builder creator by setting the base path and query components
     * @param pathComponents uri query components
     */
    public FacebookURIBuilder createURIBuilder(String... pathComponents) throws URISyntaxException {
        // Pass default access token, which is facebook client account access token
        return createURIBuilder(new HashMap<String, Object>(), pathComponents);
    }

    /**
     * Facebook Url builder creator by setting the base path, params and query components
     * @param params uri params
     * @param pathComponents uri query components
     */
    public FacebookURIBuilder createURIBuilder(Map<String, Object> params, String... pathComponents) throws URISyntaxException {
    	FacebookURIBuilder uriBuilder = new FacebookURIBuilder();
        uriBuilder.appendPathComponents(pathComponents);
        uriBuilder.addParameters(params);
        //log.info(uriBuilder.getEncodedParams());
        
        // Add access token as one of the params if not exist
        if (!uriBuilder.containsParameter(ACCESS_TOKEN_KEY)) {
            uriBuilder.addParameter(ACCESS_TOKEN_KEY, this.accessToken);
        }
        return uriBuilder;
    }
}
