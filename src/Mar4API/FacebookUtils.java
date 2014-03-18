package Mar4API;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

 /**
  * 
  * Most Facebook related Utility functions would be here in place holder 
  * 
  * @author mmadhusoodan
  *
  */
public final class FacebookUtils {

    private static final Logger log = Logger.getLogger(FacebookUtils.class);
    
    public static final String
            UTF_8 = "UTF-8",
            UTF_16 = "UTF-16LE",
            UTF_16_BE = "UTF-16BE";

    public static String encode(String url) {
        String ret = url;

        if (url != null) {
            try {
                ret = URLEncoder.encode(url, FacebookUtils.UTF_8);
            } catch (UnsupportedEncodingException e) {
                log.error("Unable to encode: " + url, e);
            } catch (IllegalArgumentException e) {
                log.error("Attempted encode argument failed: " + url, e);
            }
        }

        return ret;
    }
    
    public static String decode(String url) {
        String ret = url;

        if (url != null) {
            try {
                ret = URLDecoder.decode(url, FacebookUtils.UTF_8);
            } catch (UnsupportedEncodingException e) {
                log.error("Attempted decode failed: " + url + "\tException: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                log.error("Attempted decode argument failed: " + url + "\tException: " + e.getMessage());
            }
        }

        return ret;
    }
    }
