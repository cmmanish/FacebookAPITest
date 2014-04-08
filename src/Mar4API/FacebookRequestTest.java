package Mar4API;


/**
 * Direct String in / String out tests.
 *
 * @author grimaldi
 */
public class FacebookRequestTest {

    private static final String
        API_KEY = "9b55ede3e18b3d653835b6f4aaf0fdf6",
        SECRET_KEY = "3744523fc97c7245e2b925b6236acc09",
        SESSION_KEY = "6131ad92d0a9e8be30b94e5d-100000344076246"; // Marin session


    private static final long ACCT_ID = 53329000l; // Marin ad account id



//    @Test
//    public void testGetAccounts() throws Exception {
//        Map<String, Object> params = getBaseParams();
//        params.put("method", FacebookMethod.ADS_GET_ACCOUNTS.methodName());
//        params.put("account_id", ACCT_ID);
//
//        String response = getResponse(params);
//
//        Assert.assertTrue(response.length() > 0);
//    }
//
//
//    @Test
//    public void testGetCampaigns() throws Exception {
//
//        Map<String, Object> params = getBaseParams();
//        params.put("method", FacebookMethod.ADS_GET_CAMPAIGNS.methodName());
//        params.put("account_id", ACCT_ID);
//
//        // get just 1 campaign:
//        params.put("campaign_ids", new Long[] {6002378747137l});
//
//        String response = getResponse(params);
//
//        Assert.assertTrue(response.length() > 0);
//    }
//
//
//    @Test
//    public void testGetGroups() throws Exception {
//
//        Map<String, Object> params = getBaseParams();
//        params.put("method", FacebookMethod.ADS_GET_GROUPS.methodName());
//        params.put("account_id", ACCT_ID);
//
//        // get just 1 group:
//        params.put("campaign_ids", new Long[] {6002378747137l});
//        params.put("adgroup_ids", new Long[] {6002378763537l});
//
//        String response = getResponse(params);
//
//        Assert.assertTrue(response.length() > 0);
//    }
//
//
//    @Test
//    public void testGetAds() throws Exception {
//
//        Map<String, Object> params = getBaseParams();
//        params.put("method", FacebookMethod.ADS_GET_ADS.methodName());
//        params.put("account_id", ACCT_ID);
//
//        // narrow by campaign and adgroup ids:
//        params.put("campaign_ids", new Long[] {6002378747137l});
//        // params.put("adgroup_ids", new Long[] {6002378763537l});
//
//        String response = getResponse(params);
//
//        Assert.assertTrue(response.length() > 0);
//    }
//
//
//    @Test
//    public void testGetAdObjects() throws Exception {
//
//        Map<String, Object> params = getBaseParams();
//        params.put("method", FacebookMethod.ADS_GET_ADS.methodName());
//        params.put("account_id", ACCT_ID);
//
//        // get just 1 campaign:
//        params.put("campaign_ids", new Long[] {6002378747137l});
//        params.put("adgroup_ids", new Long[] {6002378763537l});
//
//        // should be length 1 array:
//        String response = getResponse(params);
//
//        Assert.assertTrue(response.length() > 0);
//    }
//
//
//    @Test
//    public void testGetCampaignStats() throws Exception {
//
//        Map<String, Object> params = getBaseParams();
//        params.put("method", "facebook.ads.getCampaignStats");
//        params.put("account_id", ACCT_ID);
//
//        params.put("campaign_ids", new Long[] {6002378747137l});
//
//        // time-range Map thing; 0 to 0 means get all stats;
//        List<Map<String, Long>> timeRanges = new ArrayList<Map<String,Long>>();
//
//        Map<String, Long> timeRange = new HashMap<String, Long>(1);
//        timeRange.put("time_start", System.currentTimeMillis() - (1000 * 60 * 60 * 72));
//        timeRange.put("time_stop", System.currentTimeMillis());
//        timeRanges.add(timeRange);
//
//        params.put("time_ranges", timeRanges);
//
//        String response = getResponse(params);
//
//        Assert.assertTrue(response.length() > 0);
//    }
//
//
//    @Test
//    public void testGetAdGroupStats() throws Exception {
//
//        Map<String, Object> params = getBaseParams();
//        params.put("method", "facebook.ads.getAdGroupStats");
//        params.put("account_id", ACCT_ID);
//
//        params.put("campaign_ids", new Long[] {6002378747137l});
//
//        // time-range Map thing; 0 to 0 means get all stats;
//        List<Map<String, Long>> timeRanges = new ArrayList<Map<String,Long>>();
//
//        Map<String, Long> timeRange = new HashMap<String, Long>(1);
//        timeRange.put("time_start", System.currentTimeMillis() - (1000 * 60 * 60 * 72));
//        timeRange.put("time_stop", System.currentTimeMillis());
//        timeRanges.add(timeRange);
//
//        params.put("time_ranges", timeRanges);
//
//        String response = getResponse(params);
//
//        Assert.assertTrue(response.length() > 0);
//    }
//
//    /**
//     * TODO: fix this test
//     * @throws Exception
//     */
//    //@Test
//    public void testUpdateCampaign() throws Exception {
//
//        Map<String, Object> params = getBaseParams();
//        params.put("method", FacebookMethod.ADS_GET_CAMPAIGNS.methodName());
//        params.put("account_id", ACCT_ID);
//
//        // get just 1 campaign:
//        params.put("campaign_ids", new Long[] {6002378747137l});
//
//        // should be length 1 array:
//        Ads_campaign[] campaigns = getResponse(params, Ads_campaign[].class);
//
//        Ads_campaign campaign = campaigns[0];
//        campaign.setDaily_budget(campaign.getDaily_budget() + 3);
//
//        //System.out.println(prettyPrintJson(new ObjectMapper().writeValueAsString(campaign)));
//
//        params.put("method", FacebookMethod.ADS_UPDATE_CAMPAIGNS.methodName());
//        params.put("campaign_specs", new Ads_campaign[] {campaign});
//
//        // TODO: ugh, again, wsdl is wrong; just print response for now:
//        String response = getResponse(params);
//
//        Assert.assertTrue(response.length() > 0);
//    }
//
//    /**
//     * Tests Facebook's suggested likes functionality
//     * TODO: Fix this text
//     * @throws Exception
//     */
//    //@Test
//    public void testGetSuggestedLikes() throws Exception {
//        String response;
//        Map<String, Object> params;
//
//        // keyword apple has numerous suggestions
//        for (int i = 1; i <= 10; i++) {
//            params = getBaseParams();
//            params.put("method", FacebookMethod.ADS_GET_KEYWORD_SUGGESTIONS.methodName());
//            params.put("input_keywords", "apple, golf");
//            params.put("max_results", i);
//            response = getResponse(params);
//            Assert.assertTrue(response.split(",").length == i);
//        }
//
//        // keyword "oiauegriovuahef" has no results and should return empty json string ("[]")
//        params = getBaseParams();
//        params.put("method", FacebookMethod.ADS_GET_KEYWORD_SUGGESTIONS.methodName());
//        params.put("input_keywords", "oiauegriovuahef");
//        params.put("max_results", 1);
//        response = getResponse(params);
//        Assert.assertEquals(2, response.length());
//    }
//
//    @Test
//    public void testGetAutoCompleteKeywords() throws Exception {
//
//        Map<String, Object> params = getBaseParams();
//        params.put("method", FacebookMethod.ADS_GET_AUTO_COMPLETE_KEYWORDS.methodName());
//        params.put("account_id", ACCT_ID);
//
//        params.put("query_string", "dog");
//        params.put("type", "1");
//        params.put("max_results", "20");
//
//        String response = getResponse(params);
//
//        Assert.assertTrue(response.length() > 0);
//    }
//
//    /**
//     * TODO: Fix this text
//     */
//    //@Test
//    public void testGetBroadCategories() throws Exception {
//        Map<String, Object> params = getBaseParams();
//        params.put("method", FacebookMethod.ADS_GET_BROAD_CATEGORIES.methodName());
//        params.put("account_id", ACCT_ID);
//
//        String response = getResponse(params);
//        Assert.assertFalse(response.contains("error"));
//        Assert.assertTrue(response.contains("{\"id\":6002714885172,\"name\":\"Cooking\",\"parent_category\":\"Activities\"}"));
//    }
//
//    /** */
//    private String getResponse(Map<String, Object> params) throws Exception {
//        return getResponse(params, String.class);
//    }
//
//    private <T> T getResponse(Map<String, Object> params, Class<T> type) throws Exception {
//
//        // translate incoming params set to String, String json for request:
//        Map<String, String> jsonParams = getJsonParams(params);
//
//        // sign the call
//        String signature = FacebookSignatureUtil.generateSignature(jsonParams, SECRET_KEY);
//        jsonParams.put("sig", signature);
//
//        // convert the map into a POST arg for FB (? weird):
//        String requestArgs = BasicClientHelper.delimit(jsonParams);
//
//        RestTemplate template = new RestTemplate();
//        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
//        converters.add(new StringHttpMessageConverter());
//        converters.add(new MappingJacksonHttpMessageConverter());
//        template.setMessageConverters(converters);
//
//        //System.out.println(requestArgs);
//
//        return template.postForObject(FacebookApiUrls.getDefaultServerUrl().toString(), requestArgs, type);
//    }
//
//
//    /** */
//    private Map<String, String> getJsonParams(Map<String, Object> params) throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, String> jsonParams = new HashMap<String, String>(params.size());
//
//        for (Entry<String, Object> entry : params.entrySet()) {
//            Object o = entry.getValue();
//            String value;
//
//            if (o instanceof String) {
//                value = (String) o;
//            } else {
//                value = mapper.writeValueAsString(o);
//            }
//            jsonParams.put(entry.getKey(), value);
//        }
//        return jsonParams;
//    }
//
//
//    /** */
//    private Map<String, Object> getBaseParams() {
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("api_key", API_KEY);
//        params.put("call_id", System.currentTimeMillis());
//        params.put("format", "json");
//        params.put("session_key", SESSION_KEY);
//        params.put("v", "1.0");
//        return params;
//    }
//
//
//    /**
//     * holy cow !? is this the easiest way to do this ?
//     * <p>
//     * TODO: Util this.
//     */
//    @SuppressWarnings("unused")
//    private String prettyPrintJson(String json) throws Exception {
//        StringWriter out = new StringWriter();
//        new MappingJsonFactory(new ObjectMapper()).createJsonGenerator(out).useDefaultPrettyPrinter().writeTree(
//            new ObjectMapper().readTree(json));
//        return out.toString();
//    }
}
