package bmb.rns.com.bookworm_app.settings;

/**
 * Created by touchy on 17/1/16.
 */
import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.text.Layout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;


import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import bmb.rns.com.bookworm_app.BuildConfig;
import bmb.rns.com.bookworm_app.R;
import bmb.rns.com.bookworm_app.asynctasks.UpdateProfilePhoto;
import bmb.rns.com.bookworm_app.handlers.click.FacebookSignIn;
import bmb.rns.com.bookworm_app.handlers.click.GoogleSignIn;
import bmb.rns.com.bookworm_app.views.TypeFaceButton;

public class Initialize {
    public static TranslateAnimation outAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
    public static TranslateAnimation inAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
    public static TranslateAnimation outAnim_back = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
    public static TranslateAnimation inAnim_back = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
    public static TranslateAnimation upInAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
    public static TranslateAnimation upOutAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f);
    public static TranslateAnimation downInAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
    public static TranslateAnimation downOutAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, +1.0f);

    public static ViewAnimator animator_app, quizAnimator, home_animator;
    public static ImageButton button_linkedin_signin;
    public static TypeFaceButton button_google_signin, button_facebook_login;
    private final Activity home;

    public Initialize(Activity home) {
        this.home = home;
        home_animator = (ViewAnimator) home.findViewById(R.id.home_animator);
        animator_app = (ViewAnimator) home.findViewById(R.id.animator_app);
        quizAnimator = (ViewAnimator) home.findViewById(R.id.quiz_animator);
        button_linkedin_signin = (ImageButton) home.findViewById(R.id.button_linkedin_signin);
        button_google_signin = (TypeFaceButton) home.findViewById(R.id.google_plus_login);
        button_facebook_login = (TypeFaceButton) home.findViewById(R.id.facebook_login);

        inAnim.setDuration(500);
        outAnim.setDuration(500);

        inAnim_back.setDuration(500);
        outAnim_back.setDuration(500);

        upInAnim.setDuration(500);
        upOutAnim.setDuration(500);

        downInAnim.setDuration(500);
        downOutAnim.setDuration(500);

        FacebookSdk.sdkInitialize(home.getApplicationContext(), Settings.FACEBOOK_LOGIN_REQUEST_CODE);
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }

        button_facebook_login.setOnClickListener(new FacebookSignIn(home));
        button_google_signin.setOnClickListener(new GoogleSignIn(home));

        quizAnimator.setInAnimation(Initialize.inAnim);
        quizAnimator.setOutAnimation(Initialize.outAnim);
    }

    public static void setProfile(String domain, Activity mActivity, String email, String name, String profilePhoto) {
        NavigationView navigationView = (NavigationView) mActivity.findViewById(R.id.nav_view);
        View profileView = (View) mActivity.findViewById(R.id.profile_page_main);
        View header = navigationView.getHeaderView(0);
        TextView profilePageName = (TextView) mActivity.findViewById(R.id.profile_page_name);
        profilePageName.setText(name);
        TextView profilePageEmail = (TextView) mActivity.findViewById(R.id.profile_page_email);
        profilePageEmail.setText(email);
        TextView full_name = (TextView) header.findViewById(R.id.full_name);
        TextView emailTextView = (TextView) header.findViewById(R.id.email);
        full_name.setText(name);
        emailTextView.setText(email);
        new UpdateProfilePhoto(header, mActivity).execute(profilePhoto, domain);

        //navigate
        Initialize.animator_app.setInAnimation(Initialize.inAnim);
        Initialize.animator_app.setOutAnimation(Initialize.outAnim);
        Initialize.animator_app.setDisplayedChild(1);
    }

    public static void displayProfilePage() {
        home_animator.setInAnimation(Initialize.inAnim);
        home_animator.setOutAnimation(Initialize.outAnim);
    }
}
