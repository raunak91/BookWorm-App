package bmb.rns.com.bookworm_app.settings;

import java.util.Arrays;
import java.util.List;

/**
 * Created by touchy on 17/1/16.
 */
public class Settings {

    public final static int GOOGLE_LOGIN_REQUEST_CODE = 1001;
    public final static int RECOVER_FROM_PLAY_SERVICES_ERROR_REQUEST_CODE = 1002;
    public final static int FACEBOOK_LOGIN_REQUEST_CODE = 1101;
    public final static List<String> FACEBOOK_PERMISSIONS = Arrays.asList("email");
    public final static String APP_LINK = "Download Venturesity App : https://play.google.com/apps/testing/venturesity.user";
    private static final String HOST = "api.linkedin.com";
    public static final String LINKEDIN_MY_PROFILE_URL = "https://" + HOST + "/v1/people/~:(id,first-name,last-name,email-address,picture-url,Positions)";
    private final static String LOGIN_SCOPE = "https://www.googleapis.com/auth/plus.login";
    private final static String ME_SCOPE = "https://www.googleapis.com/auth/plus.me";
    private final static String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";
    private final static String PROFILE_SCOPE = "https://www.googleapis.com/auth/userinfo.profile";
    public final static String GOOGLE_OAUTH2_SCOPES = "oauth2:" + LOGIN_SCOPE + " " + ME_SCOPE + " " + EMAIL_SCOPE + " " + PROFILE_SCOPE;
    public static String LINKEDIN_ACCESS_TOKEN = "";
}
