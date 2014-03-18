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

    public <T> List<T> callGETMethod(FacebookURIBuilder facebookUriBuilder, TypeReference<List<T>> typeReference) throws NullPointerException, URISyntaxException, JsonGenerationException,
            JsonMappingException, IOException {

        int lastBatchSize;
        int totalSize = 0;
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
            // update the offset
            lastBatchSize = moreData.size();
            totalSize += lastBatchSize;
        }
        else {
            lastBatchSize = 0;
        }
        return returnList;

    }

    public void getAccountURI() throws NullPointerException, URISyntaxException, JsonGenerationException, JsonMappingException, IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_account.class));
        log.info("Params are: " + params);
        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, getAccountIdComponent());

        uriBuilder.getEncodedParams();
        
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
        List<Ads_account> ads_accountList = callGETMethod(uriBuilder, new TypeReference<List<Ads_account>>() {
        });
        log.info(ads_accountList.get(1).getAccount_id());

    }

    /**
     * Get all of the ad campaigns under this account.
     * 
     * @throws IOException
     * @throws NullPointerException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */

    public void getAllCampaignsURI() throws URISyntaxException, JsonGenerationException, JsonMappingException, NullPointerException, IOException { // Marin Campaign

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_campaign_group.class));

        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, getAccountIdComponent(), CAMPAIGN_GROUP_COMPONENT);
        uriBuilder.getEncodedParams();
        // log.info("Host - " + uriBuilder.getHost());
        // log.info("Path - " + uriBuilder.getPath());
        // log.info("URI "+uriBuilder.build().toString());
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
        List<Ads_campaign_group> adCampaignGroupList = callGETMethod(uriBuilder, new TypeReference<List<Ads_campaign_group>>() {
        });

        log.info(adCampaignGroupList.get(1).getId());
        log.info(adCampaignGroupList.get(1).getName());
        log.info(adCampaignGroupList.get(1).getCampaign_group_status());
        // callGETMethod(uriBuilder, String.class);
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
    public void getAllAdsetsURI() throws URISyntaxException, JsonGenerationException, JsonMappingException, NullPointerException, IOException { 
        // Marin Group

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_campaign.class));

        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, getAccountIdComponent(), CAMPAIGN_COMPONENT);
        uriBuilder.getEncodedParams();
        // log.info("Host - " + uriBuilder.getHost());
        // log.info("Path - " + uriBuilder.getPath());
        // log.info("URI "+uriBuilder.build().toString());
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
        List<Ads_campaign> adSetsList = callGETMethod(uriBuilder, new TypeReference<List<Ads_campaign>>() {});

        log.info(adSetsList.get(1).getId());
        log.info(adSetsList.get(1).getName());
        log.info(adSetsList.get(1).getDaily_budget());
        log.info(adSetsList.get(1).getLifetime_budget());
        log.info(adSetsList.get(1).getStart_time());
        log.info(adSetsList.get(1).getEnd_time());

    }

    public void getCampaign(long campaignId) throws Exception {

        // Ads_campaign_group
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DATE_FORMAT_PARAM_KEY, DEFAULT_DATE_FORMAT);
        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_campaign_group.class));

        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, new String[] { String.valueOf(campaignId) });
        // log.info(uriBuilder.build().toString());
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));

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

        // log.info(uriBuilder.build().toString());
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
    }

    public void getAdsGroup(long groupId) throws Exception { // Ads_adgroup
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DATE_FORMAT_PARAM_KEY, DEFAULT_DATE_FORMAT);
        params.put(FIELDS_PARAM_NAME, FacebookFieldParamsUtil.getCommaSeparatedClassFieldNames(Ads_adgroup.class));

        FacebookURIBuilder uriBuilder = fbURIBuilderFactory.createURIBuilder(params, String.valueOf(groupId));

        // log.info(uriBuilder.build().toString());
        log.info(FacebookUtils.decode(uriBuilder.build().toString()));
    }
}
