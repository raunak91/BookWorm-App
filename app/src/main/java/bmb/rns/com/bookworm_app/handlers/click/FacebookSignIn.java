package bmb.rns.com.bookworm_app.handlers.click;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONException;
import org.json.JSONObject;

import bmb.rns.com.bookworm_app.AnalyticsApplication;
import bmb.rns.com.bookworm_app.settings.Initialize;
import bmb.rns.com.bookworm_app.settings.Settings;

/**
 * Created by touchy on 17/1/16.
 */
public class FacebookSignIn extends ContextWrapper implements View.OnClickListener {
    private static CallbackManager callbackManager;

    public FacebookSignIn(Context base) {
        super(base);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        callbackManager = CallbackManager.Factory.create();
        Tracker tracker = ((AnalyticsApplication) getApplicationContext()).getDefaultTracker();
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("facebook")
                .setAction("login")
                .build());
        LoginManager.getInstance().logInWithReadPermissions(((Activity) getBaseContext()), Settings.FACEBOOK_PERMISSIONS);
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResults) {

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResults.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        JSONObject json = response.getJSONObject();
                                        try {
                                            String name = json.get("name").toString();
                                            String email = json.get("email").toString();
                                            String picture = "http://graph.facebook.com/" + json.get("id").toString() + "/picture?type=large";
                                            Initialize.setProfile("facebook", (Activity) getBaseContext(), email, name, picture);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {

                        Log.e("dd", "facebook login canceled");

                    }


                    @Override
                    public void onError(FacebookException e) {


                        Log.e("dd", "facebook login failed error");

                    }
                });
    }
}
