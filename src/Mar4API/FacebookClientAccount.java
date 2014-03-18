package Mar4API;
/**
 * Facebook pca.
 */
public class FacebookClientAccount {

	private static final String UNDERSCORE = "_";

	private static final int HOUR_OF_DAILY_REPORT = 3;

	private Long accountId;
	private Long scheduledDailyReportId;
	private String sessionKey; // being phased out at the end of OAuth 2.0 upgrade
	private String accessToken; // for OAuth 2.0 compliance; serves the same purpose as old session_key

	public FacebookClientAccount(){
		
	}
	
	public FacebookClientAccount(Long accountId, String accessToken) {
		
		this.accountId = accountId;
		this.accessToken = accessToken;
	}

	public String getName() {
		if (getAccountId() == null) {
			return "";
		}
		else {
			return String.valueOf(getAccountId());
		}
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public Long getScheduledDailyReportId() {
		return this.scheduledDailyReportId;
	}

	public void setScheduledDailyReportId(Long scheduledDailyReportId) {
		this.scheduledDailyReportId = scheduledDailyReportId;
	}

	public int getHourOfDailyReport() {
		return HOUR_OF_DAILY_REPORT;
	}

}
