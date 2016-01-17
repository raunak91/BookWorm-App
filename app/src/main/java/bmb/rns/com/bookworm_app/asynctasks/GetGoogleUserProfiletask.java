package bmb.rns.com.bookworm_app.asynctasks;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Network;
import android.os.AsyncTask;

import com.google.android.gms.appdatasearch.GetRecentContextCall;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import bmb.rns.com.bookworm_app.settings.Initialize;
import bmb.rns.com.bookworm_app.settings.Settings;

import com.android.volley.Request;

/**
 * Created by touchy on 17/1/16.
 */
public class GetGoogleUserProfileTask extends AsyncTask<Void, Void, Void> {
    Activity mActivity;
    String mEmail;

    public GetGoogleUserProfileTask(Activity activity, String email) {
        this.mActivity = activity;
        this.mEmail = email;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            String token = fetchToken();
            if (token != null) {
                getUserInfoFromAccessToken(token);
                // **Insert the good stuff here.**
                // Use the token to access the user's Google data.
            }
        } catch (IOException e) {
            // The fetchToken() method handles Google-specific exceptions,
            // so this indicates something went wrong at a higher level.
            // TIP: Check for network connectivity before starting the AsyncTask.
        }
        return null;
    }

    protected String fetchToken() throws IOException {
        try {
            return GoogleAuthUtil.getToken(mActivity, mEmail, Settings.GOOGLE_OAUTH2_SCOPES);
        } catch (UserRecoverableAuthException userRecoverableException) {
            // GooglePlayServices.apk is either old, disabled, or not present
            // so we need to show the user some UI in the activity to recover.
            handleException(userRecoverableException);
        } catch (GoogleAuthException fatalException) {
            // Some other type of unrecoverable exception has occurred.
            // Report and log the error as appropriate for your app.
        }
        return null;
    }

    public void handleException(final Exception e) {
        // Because this call comes from the AsyncTask, we must ensure that the following
        // code instead executes on the UI thread.
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (e instanceof GooglePlayServicesAvailabilityException) {
                    // The Google Play services APK is old, disabled, or not present.
                    // Show a dialog created by Google Play services that allows
                    // the user to update the APK
                    int statusCode = ((GooglePlayServicesAvailabilityException) e)
                            .getConnectionStatusCode();
                    Dialog dialog = GooglePlayServicesUtil.getErrorDialog(statusCode,
                            mActivity,
                            Settings.RECOVER_FROM_PLAY_SERVICES_ERROR_REQUEST_CODE);
                    dialog.show();
                } else if (e instanceof UserRecoverableAuthException) {
                    // Unable to authenticate, such as when the user has not yet granted
                    // the app access to the account, but the user can fix this.
                    // Forward the user to an activity in Google Play services.
                    Intent intent = ((UserRecoverableAuthException) e).getIntent();
                    mActivity.startActivityForResult(intent,
                            Settings.RECOVER_FROM_PLAY_SERVICES_ERROR_REQUEST_CODE);
                }
            }
        });
    }

    private void getUserInfoFromAccessToken(String access_token) {
        String url = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=" + access_token;
        // Formulate the request and handle the response.
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new GetRecentContextCall.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String name = response.getString("name");
                    String email = response.getString("email");
                    String picture = response.getString("picture");
                    Initialize.setProfile("google", mActivity, email, name, picture);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new GetRecentContextCall.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Network.getInstance(mActivity).addToRequestQueue(jsonObjReq);
    }
}
