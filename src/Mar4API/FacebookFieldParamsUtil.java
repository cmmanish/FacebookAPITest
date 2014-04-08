package Mar4API;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * A Facebook fields param utility class
 *
 * In order to read details about an object, Facebook requires a fields parameter which
 * specifies all of the fields that needs to be read pertaining to the object.
 *
 * For example:
 * To read details about an adgroup object, make an HTTP GET to
 * https://graph.facebook.com/{adgroup_id}?fields=<comma separated list of fields>
 *
 * @author dson
 */
public class FacebookFieldParamsUtil {
    public static final String
                COMMA_SEPERATOR = ",";

    private static final Set<String> IGNORE_FIELDS = new HashSet<String>();

    /** Cache to hold a comma separated string of field names for a given class **/
    private static Map<Class, String> CACHED_CLASS_FIELD_NAMES = new HashMap<Class, String>();

    static {
        /**
         * WSDL creates objects which implement java.io.Serializable,
         * in the implementation of these objects WSDL creates internal
         * helper fields (__equalsCalc, __hashCodeCalc, typeDesc)
         *
         * These fields should be ignored since they are not user
         * declared fields
         */
        IGNORE_FIELDS.add("__equalsCalc");
        IGNORE_FIELDS.add("__hashCodeCalc");
        IGNORE_FIELDS.add("typeDesc");

        // Error is not one of the params used to read a Facebook object
        // but only used when Facebook responds with an error
        IGNORE_FIELDS.add("error");

        IGNORE_FIELDS.add("objective");


        /**
         * The following fields that are associated with Adgroups should be ignored.
         * since Facebook has deprecated them. Because there is adapter layer code regarding
         * the creative and tracking_pixel_ids field, we will need to clean up the code base
         * after the iteration-0043 release to minimize risk.
         *
         * TODO: Remove creative and tracking_pixel_ids from here and clean up backend code
         */
        IGNORE_FIELDS.add("creative");
        IGNORE_FIELDS.add("tracking_pixel_ids");
    }

    /**
     * Get a comma separated String of class field names mapped to the
     * "fields" parameter name to be used to generate a Facebook graph service request.
     *
     * Cache the result of the comma separated class field names since this utility function
     * will be called multiple times for a given class.
     *
     * @param clazz Class of the WSDL compiled Java object
     * @return String of the comma separated name of fields
     */
    public static String getCommaSeparatedClassFieldNames(Class clazz) {
        String commaSeparatedClassFieldNames = StringUtils.EMPTY;

        // Check whether we have already cached the comma separated field
        // names for this class, and if so, return the result
        if (CACHED_CLASS_FIELD_NAMES.containsKey(clazz)) {
            commaSeparatedClassFieldNames = CACHED_CLASS_FIELD_NAMES.get(clazz);
        } else {
            // Get all declared fields of the given class
            Field[] fields = clazz.getDeclaredFields();
            List<String> fieldNames = new ArrayList<String>();

            for (Field field : fields) {
                String name = field.getName();

                if (!IGNORE_FIELDS.contains(name)) {
                    fieldNames.add(name);
                }
            }
            // Generate a comma separated string of field names
            commaSeparatedClassFieldNames = StringUtils.join(fieldNames.toArray(), COMMA_SEPERATOR);
            CACHED_CLASS_FIELD_NAMES.put(clazz, commaSeparatedClassFieldNames);
        }
        
        return commaSeparatedClassFieldNames;
    }
}
