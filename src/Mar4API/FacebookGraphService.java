package Mar4API;

import com.facebook.api.Ads_account;
import com.facebook.api.Ads_adgroup;
import com.facebook.api.Ads_campaign;
import com.facebook.api.Ads_campaign_group;
import com.facebook.api.Ads_connection;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import com.facebook.api.Ads_connections_response;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mmadhusoodan on 3/7/14.
 */
public class FacebookGraphService extends AbstractJsonRestService {

    private final Logger log = Logger.getLogger(FacebookGraphService.class);

    FacebookURIBuilder uriBuilder;
    protected FacebookURIBuilderFactory fbURIBuilderFactory;
    protected FacebookClientAccount fca;
    protected Long accountId = 53329000l;

    private static String

    DATE_FORMAT_PARAM_KEY = "date_format", DEFAULT_DATE_FORMAT = "U", // returns datetimes in Unix time, because fb defaults to different time conventions all over the place
            // for paging use
            OFFSET_KEY = "offset", LIMIT_KEY = "limit", LIMIT = "limit", LIMIT_VALUE = "250", DATA = "data", COUNT = "count", ACCOUNT_PREFIX = "act_", FIELDS_PARAM_NAME = "fields",
            // campaign group level
            CAMPAIGN_GROUP_COMPONENT = "adcampaign_groups", CONNECTION_COMPONENT = "connectionobjects", OFFSITE_PIXELS_COMPONENT = "offsitepixels",
            // group level
            CAMPAIGN_COMPONENT = "adcampaigns";

    public FacebookGraphService() {

    }

    public FacebookGraphService(FacebookClientAccount fca, Long accountId, String accessToken) {
        setFacebookClientAccount(fca, accountId, accessToken);
    }

    public void setFacebookClientAccount(FacebookClientAccount facebookClientAccount, Long accountId, String accessToken) {
        this.fca = new FacebookClientAccount(accountId, accessToken);
        fbURIBuilderFactory = new FacebookURIBuilderFactory(fca.getAccessToken());

    }

    public FacebookClientAccount getFacebookClientAccount() {
        return this.fca;
    }

    protected String getAccountIdComponent() throws NullPointerException {
        log.info(ACCOUNT_PREFIX + String.valueOf(fca.getAccountId()));
        return ACCOUNT_PREFIX + String.valueOf(fca.getAccountId());
    }

    /**
     * given an object return a map based on the json representation.
     * converts object to json string to Map
     * 
     * @param jsonObj
     *        the object to get a map of
     * @return map representation of the object
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> jsonObjectToMap(Object jsonObj) throws Exception {
        String jsonString = this.jsonMapper.writeValueAsString(jsonObj);
        return this.jsonMapper.readValue(jsonString, HashMap.class);
    }

    public <T> T callGETMethod(FacebookURIBuilder facebookUriBuilder, Class<T> returnType) throws NullPointerException, URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

        URI uri = facebookUriBuilder.build();
        String result = GETCall(uri, String.class);
        log.info("result: " + result);

        return this.jsonMapper.readValue(result, returnType);
    }

    public <T> T callPOSTMethod(FacebookURIBuilder facebookUriBuilder, Class<T> returnType) throws NullPointerException, URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

        URI uri = facebookUriBuilder.build();
        String result = POSTCall(uri, null, String.class);
        log.info("result: " + result);

        return this.jsonMapper.readValue(result, returnType);
    }

    public <T> List<T> paginateCallGETMethod(FacebookURIBuilder facebookUriBuilder, TypeReference<List<T>> typeReference) throws NullPointerException, URISyntaxException, JsonGenerationException,
            JsonMappingException, IOException {

        URI uri = facebookUriBuilder.build();
        String result = GETCall(uri, String.class);

        log.info("Result : " + result);
        @SuppressWarnings("unchecked")
        Map<String, Object> response = this.jsonMapper.readValue(result, HashMap.class);

        List<T> returnList = new ArrayList<T>();
        // get the data by type use json mapping
        String dataString = this.jsonMapper.writeValueAsString(response.get(DATA));
        log.info("dataString: " + dataString);
        List<T> moreData = this.jsonMapper.readValue(dataString, typeReference);

        if (!moreData.isEmpty()) {
            returnList.addAll(moreData);
        }
        else {
        }
        return returnList;

    }

    /**
     * Get all of the connections.
     *
     * @return List<Ads_connection>
     */
    public List<Ads_connection> getConnections() throws NullPointerException, URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

        List<Ads_connection> connections = new ArrayList<Ads_connection>();
        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(getAccountIdComponent(), CONNECTION_COMPONENT);

        log.info(FacebookUtils.decode(uriBuilder.build().toString()));

        Ads_connections_response response = callGETMethod(uriBuilder, Ads_connections_response.class);

        if (ArrayUtils.isNotEmpty(response.getData())) {
            connections.addAll(Arrays.asList(response.getData()));
        }

        return connections;

    }

    /**
     * Retrieve all of the offsite pixels
     * 
     * @param limit
     *        The max number of return objects
     * @return List of all instances of the offsite pixels
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getOffsitePixels(int limit) {
        List<Map<String, Object>> instanceData = new ArrayList<Map<String, Object>>();
        try {
            Map<String, Object> response = null;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(LIMIT_KEY, limit);

            FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, getAccountIdComponent(), OFFSITE_PIXELS_COMPONENT);

            log.info(FacebookUtils.decode(uriBuilder.build().toString()));
            response = callGETMethod(uriBuilder, HashMap.class);
            if (response != null) {
                instanceData = (List<Map<String, Object>>) response.get(DATA);
            }
        }
        catch (Exception e) {
            log.error("Error retrieving offsite pixels", e);
        }

        return instanceData;
    }

    // need to see what we are getting id in the response
    public Ads_account getAccount() throws NullPointerException, URISyntaxException, JsonGenerationException, JsonMappingException, IOException {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_account.class));
        log.info("Params are: " + params);
        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, getAccountIdComponent());

        uriBuilder.getEncodedParams();

        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
        Ads_account ads_account = callGETMethod(uriBuilder, Ads_account.class);

        return ads_account;

    }

    /**
     * Get all of the ad campaigns under this account.
     * 
     * @throws IOException
     * @throws NullPointerException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */

    public List<Ads_campaign_group> getAllCampaigns() throws URISyntaxException, JsonGenerationException, JsonMappingException, NullPointerException, IOException { // Marin Campaign

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DATE_FORMAT_PARAM_KEY, DEFAULT_DATE_FORMAT);
        params.put(LIMIT, LIMIT_VALUE);

        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_campaign_group.class));

        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, getAccountIdComponent(), CAMPAIGN_GROUP_COMPONENT);
        uriBuilder.getEncodedParams();
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
        List<Ads_campaign_group> adCampaignGroupList = paginateCallGETMethod(uriBuilder, new TypeReference<List<Ads_campaign_group>>() {
        });

        return adCampaignGroupList;

    }

    public void createCampaign(Ads_campaign_group campaign) throws Exception {

        Map<String, Object> params = jsonObjectToMap(campaign);
        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, getAccountIdComponent(), CAMPAIGN_GROUP_COMPONENT);
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));

        // List<Ads_campaign_group> createCampaignResultList = callPOSTMethod(createCampaignRelativeUrl, Ads_campaign_group.class,null);

    }

    public Ads_campaign_group getCampaign(long campaignId) throws Exception {

        // Ads_campaign_group
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DATE_FORMAT_PARAM_KEY, DEFAULT_DATE_FORMAT);
        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_campaign_group.class));

        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, new String[] { String.valueOf(campaignId) });
        // log.info(uriBuilder.build().toString());
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));

        Ads_campaign_group adCampaignGroup = callGETMethod(uriBuilder, Ads_campaign_group.class);

        return adCampaignGroup;

    }

    /**
     * Get all of the adset under the given campaign.
     * 
     * @return List<Ads_campaign>
     * @throws IOException
     * @throws NullPointerException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    public List<Ads_campaign> getAllAdsets() throws URISyntaxException, JsonGenerationException, JsonMappingException, NullPointerException, IOException {
        // Marin Group

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DATE_FORMAT_PARAM_KEY, DEFAULT_DATE_FORMAT);
        params.put(LIMIT, LIMIT_VALUE);
        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_campaign.class));

        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, getAccountIdComponent(), CAMPAIGN_COMPONENT);
        uriBuilder.getEncodedParams();
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
        List<Ads_campaign> adSetsList = paginateCallGETMethod(uriBuilder, new TypeReference<List<Ads_campaign>>() {
        });

        return adSetsList;
    }

    /**
     * Get all of the adset under the given campaign.
     * 
     * @param campaignId
     *        The external ID of the campaign
     * @return List<Ads_campaign>
     */
    public List<Ads_campaign> getAllAdsetsInCampaign(long campaignId) throws Exception {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DATE_FORMAT_PARAM_KEY, DEFAULT_DATE_FORMAT);
        params.put(LIMIT, LIMIT_VALUE);
        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_campaign.class));
        // Need to retrieve all fields for an Ads_campaign, otherwise the latest data will not be retrieved from Facebook

        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, String.valueOf(campaignId), CAMPAIGN_COMPONENT);
        uriBuilder.getEncodedParams();
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));

        List<Ads_campaign> adSetsList = paginateCallGETMethod(uriBuilder, new TypeReference<List<Ads_campaign>>() {
        });

        return adSetsList;
    }

    /**
     * Retrieves a single adset by external ID
     * 
     * @param adsetId
     *        The external ID of the adset to retrieve
     * @return The retrieved adset
     */
    public Ads_campaign getAdset(long adsetId) throws Exception { // Ads_campaign
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DATE_FORMAT_PARAM_KEY, DEFAULT_DATE_FORMAT);
        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_campaign.class));

        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, String.valueOf(adsetId));
        log.info(uriBuilder.getPath());
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
        Ads_campaign adCampaign = callGETMethod(uriBuilder, Ads_campaign.class);

        return adCampaign;

    }

    public Ads_adgroup getAdsGroup(long groupId) throws Exception { // Ads_adgroup
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DATE_FORMAT_PARAM_KEY, DEFAULT_DATE_FORMAT);
        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_adgroup.class));

        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, String.valueOf(groupId));
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
        Ads_adgroup adgroup = callGETMethod(uriBuilder, Ads_adgroup.class);

        return adgroup;

    }
}