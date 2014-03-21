package Mar4API;

import com.facebook.api.Ads_account;
import com.facebook.api.Ads_adgroup;
import com.facebook.api.Ads_campaign;
import com.facebook.api.Ads_campaign_group;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;
import org.jsoup.helper.StringUtil;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mmadhusoodan on 3/7/14.
 */
public class FacebookGraphService extends AbstractJsonRestService {

    private final Logger log = Logger.getLogger(TestBuildingURI.class);

    FacebookURIBuilder uriBuilder;
    protected FacebookURIBuilderFactory fbURIBuilderFactory;
    protected FacebookClientAccount fca;
    protected Long accountId = 53329000l;

    private static String

    DATE_FORMAT_PARAM_KEY = "date_format", DEFAULT_DATE_FORMAT = "U", // returns datetimes in Unix time, because fb defaults to different time conventions all over the place

            DATA = "data", COUNT = "count", ACCOUNT_PREFIX = "act_", FIELDS_PARAM_NAME = "fields",
            // campaign group level
            CAMPAIGN_GROUP_COMPONENT = "adcampaign_groups",
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

    public <T> T callGETMethod(FacebookURIBuilder facebookUriBuilder, Class<T> returnType) throws NullPointerException, URISyntaxException, JsonGenerationException, JsonMappingException, IOException {

        URI uri = facebookUriBuilder.build();
        String result = (String) restCall(uri, String.class);
        log.info("result: " + result);

        // Map<String, Object> response = this.jsonMapper.readValue(result, HashMap.class);
        // log.info(response);
        // List<T> returnList = new ArrayList<T>();
        // String dataString = this.jsonMapper.writeValueAsString(response.get(DATA));
        // return returnList;

        return this.jsonMapper.readValue(result, returnType);
    }

    public <T> List<T> paginateCallGETMethod(FacebookURIBuilder facebookUriBuilder, TypeReference<List<T>> typeReference) throws NullPointerException, URISyntaxException, JsonGenerationException,
            JsonMappingException, IOException {

        URI uri = facebookUriBuilder.build();
        String result = (String) restCall(uri, String.class);

        log.info("Result : " + result);
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

    public void getAccount() throws NullPointerException, URISyntaxException, JsonGenerationException, JsonMappingException, IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        
        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_account.class));
        log.info("Params are: " + params);
        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, getAccountIdComponent());

        uriBuilder.getEncodedParams();

        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
        Ads_account ads_account = callGETMethod(uriBuilder, Ads_account.class);

        log.info(ads_account.getAccount_id());
        log.info(ads_account.getName());
        log.info(ads_account.getCurrency());

    }

    /**
     * Get all of the ad campaigns under this account.
     * 
     * @throws IOException
     * @throws NullPointerException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */

    public void getAllCampaigns() throws URISyntaxException, JsonGenerationException, JsonMappingException, NullPointerException, IOException { // Marin Campaign

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DATE_FORMAT_PARAM_KEY, DEFAULT_DATE_FORMAT);
        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_campaign_group.class));

        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, getAccountIdComponent(), CAMPAIGN_GROUP_COMPONENT);
        uriBuilder.getEncodedParams();
        // log.info("Host - " + uriBuilder.getHost());
        // log.info("Path - " + uriBuilder.getPath());
        // log.info("URI "+uriBuilder.build().toString());
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
        List<Ads_campaign_group> adCampaignGroupList = paginateCallGETMethod(uriBuilder, new TypeReference<List<Ads_campaign_group>>() {
        });

        log.info(adCampaignGroupList.get(1).getId());
        log.info(adCampaignGroupList.get(1).getName());
        log.info(adCampaignGroupList.get(1).getCampaign_group_status());

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
        log.info(adCampaignGroup.getAccount_id());
        log.info(adCampaignGroup.getCampaign_group_status());
        log.info(adCampaignGroup.getName());
    
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
    public void getAllAdsets() throws URISyntaxException, JsonGenerationException, JsonMappingException, NullPointerException, IOException {
        // Marin Group

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DATE_FORMAT_PARAM_KEY, DEFAULT_DATE_FORMAT);
        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_campaign.class));

        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, getAccountIdComponent(), CAMPAIGN_COMPONENT);
        uriBuilder.getEncodedParams();
        // log.info("Host - " + uriBuilder.getHost());
        // log.info("Path - " + uriBuilder.getPath());
        // log.info("URI "+uriBuilder.build().toString());
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
        List<Ads_campaign> adSetsList = paginateCallGETMethod(uriBuilder, new TypeReference<List<Ads_campaign>>() {
        });

        int i = 0;
        log.info(adSetsList.size());
        log.info(adSetsList.get(i).getId());
        log.info(adSetsList.get(i).getName());
        log.info(adSetsList.get(i).getDaily_budget());
        log.info(adSetsList.get(i).getLifetime_budget());
        log.info(adSetsList.get(i).getStart_time());
        log.info(adSetsList.get(i).getEnd_time());

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
        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_campaign.class));
        // Need to retrieve all fields for an Ads_campaign, otherwise the latest data will not be retrieved from Facebook

        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, String.valueOf(campaignId), CAMPAIGN_COMPONENT);
        uriBuilder.getEncodedParams();
        // log.info("Host - " + uriBuilder.getHost());
        // log.info("Path - " + uriBuilder.getPath());
        // log.info("URI "+uriBuilder.build().toString());
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
        
        List<Ads_campaign> adSetsList = paginateCallGETMethod(uriBuilder, new TypeReference<List<Ads_campaign>>() {});
        log.info(adSetsList.size());
        log.info(adSetsList.get(0).getAccount_id());
        log.info(adSetsList.get(0).getName());
        log.info(adSetsList.get(0).getStart_time());
        
        return adSetsList;
    }

    /**
     * Retrieves a single adset by external ID
     * 
     * @param adsetId
     *        The external ID of the adset to retrieve
     * @return The retrieved adset
     */
    public void getAdset(long adsetId) throws Exception { // Ads_campaign
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DATE_FORMAT_PARAM_KEY, DEFAULT_DATE_FORMAT);
        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_campaign.class));

        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, String.valueOf(adsetId));
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
        Ads_campaign adCampaign = callGETMethod(uriBuilder, Ads_campaign.class);
        log.info(adCampaign.getId());
        log.info(adCampaign.getCampaign_group_id());
        log.info(adCampaign.getName());
        log.info(adCampaign.getCampaign_status());
        log.info(adCampaign.getDaily_budget());
        log.info(adCampaign.getLifetime_budget());

    }

    public void getAdsGroup(long groupId) throws Exception { // Ads_adgroup
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DATE_FORMAT_PARAM_KEY, DEFAULT_DATE_FORMAT);
        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_adgroup.class));

        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, String.valueOf(groupId));

        // log.info(uriBuilder.build().toString());
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
        Ads_adgroup adgroup = callGETMethod(uriBuilder, Ads_adgroup.class);
        
        log.info(adgroup.getId());
        log.info(adgroup.getName());
        log.info(adgroup.getAdgroup_status());
        log.info(adgroup.getBid_info());
        
        
        
    }
}
