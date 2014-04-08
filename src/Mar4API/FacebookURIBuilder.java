package Mar4API;


import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FacebookURIBuilder {
    
    final static Logger log = Logger.getLogger(TestBuildingURI.class);
    private final org.apache.http.client.utils.URIBuilder uriBuilder;
    static final String DEFAULT_FACEBOOK_GRAPH_API_URL_BASE_PATH = "https://graph.facebook.com";
    private final String basePath;
    public static final String UTF_8 = "UTF-8" ;
    protected static final String 
            EQUAL_SIGN = "=", 
            AMPERSAND = "&", 
            QUESTION_MARK = "?", 
            FORWARD_SLASH = "/";
    
    //private static String ACCESS_TOKEN_KEY = "access_token";

    public FacebookURIBuilder() throws URISyntaxException {
        this(DEFAULT_FACEBOOK_GRAPH_API_URL_BASE_PATH);
    }

    public FacebookURIBuilder(String basePath) throws URISyntaxException {
        this.uriBuilder = new org.apache.http.client.utils.URIBuilder(basePath);
        this.basePath = basePath;
    }

    /**
     * Set the path of the uriBuilder
     */
    public void setPath(String path) {
        this.uriBuilder.setPath(path);
    }

    /**
     * Return the path of the uribuilder
     */
    public String getPath() {
        return this.uriBuilder.getPath();
    }

    /**
     * Return the host of the uri builder
     */
    public String getHost() {
        return this.uriBuilder.getHost();
    }
    
    /**
     * get url queryParams
     */
    public List<NameValuePair> getQueryParams() {
        return this.uriBuilder.getQueryParams();
    }

    
    /**
     * @return both url path and query part
     *         e.g. search?q=san+francisco
     */
    public String getPathParam() {
        return StringUtils.strip(getPath(), FORWARD_SLASH) + QUESTION_MARK + getEncodedParams();
    }
    
    
    
    /**
     * Encode parameter value, format the parameters string as k1=v1&k2=v2
     * @return parameter url string
     */
    public String getEncodedParams() {
        StringBuilder paramBuilder = new StringBuilder();

        for (NameValuePair param : this.uriBuilder.getQueryParams()) {
            String paramValue = String.valueOf(param.getValue());

            // Append ampersand to the parameter string if it's not empty
            if (paramBuilder.length() > 0) {
                paramBuilder.append(AMPERSAND);
            }
            paramBuilder.append(param.getName() + EQUAL_SIGN + FacebookUtils.encode(paramValue));
        }

        return paramBuilder.toString();
    }
    
    /**
     * Check if already contains the parameter
     * 
     * @param key
     *        paramter key need to be checked
     * @return true if contains the parameter
     */
    public boolean containsParameter(String key) {
        // Go throught current name value pair and check if it already contains the key
        for (NameValuePair entry : getQueryParams()) {
            if (entry.getName().equals(key)) {
                return true;
            }
        }
        return false;
    }
    
    

    /**
     * Add one url parameter
     * 
     * @param key
     *        parameter's key
     * @param value
     *        parameter's value
     */
    public void addParameter(String key, Object value) { //used for accessToken="CAATqGuBpUXAB.."  style 
        if (key != null) {
            this.uriBuilder.addParameter(key, String.valueOf(value));
        }
    }
    
    /**
     * Add a set of parameters
     * 
     * @param params
     *        parameter map
     */
    public void addParameters(Map<String, Object> params) { // for fields=name,id style
        if (params != null) {
            for (Entry<String, Object> param : params.entrySet()) {
                addParameter(param.getKey(), param.getValue());
            }
        }
    }
    
    /**
     * Append query component to the existing query
     * e.g. /insights/stories
     * 
     * @param queryComponent
     *        query component
     */
    public void appendPathComponent(String queryComponent) {

        // Trim the query component
        queryComponent = StringUtils.strip(queryComponent, FORWARD_SLASH);
        String path = StringUtils.strip(getPath(), FORWARD_SLASH);

        if (StringUtils.isNotBlank(queryComponent)) {
            if (StringUtils.isNotBlank(path)) {
                setPath(FORWARD_SLASH + path + FORWARD_SLASH + queryComponent);
            }
            else {
                setPath(FORWARD_SLASH + queryComponent);
            }
        }
    }
    
    /**
     * Append a list of query components to the existing query
     * Query components order matters
     * 
     * @param queryComponents
     *        a list of query components
     */
    public void appendPathComponents(String... queryComponents) {
        if (queryComponents != null) {
            // Join all the query components together and append it to the existing query
            appendPathComponent(StringUtils.join(queryComponents, FORWARD_SLASH));
        }
    }

    /**
     * Build uri based on uribuilder
     * 
     * @return uri
     */
    public URI build() throws URISyntaxException {
        return this.uriBuilder.build();
    }
    
 }
