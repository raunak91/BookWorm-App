package bmb.rns.com.bookworm_app.handlers.click;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.View;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import bmb.rns.com.bookworm_app.AnalyticsApplication;
import bmb.rns.com.bookworm_app.MainActivity;
import bmb.rns.com.bookworm_app.asynctasks.GetGoogleUserProfileTask;
import bmb.rns.com.bookworm_app.settings.Settings;

/**
 * Created by touchy on 17/1/16.
 */
public class GoogleSignIn extends ContextWrapper implements View.OnClickListener {

    public GoogleSignIn(Context base) {
        super(base);
    }

    public static void onActivityResult(Intent data, MainActivity mainActivity) {
        String mEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        new GetGoogleUserProfileTask(mainActivity, mEmail).execute();
    }

    @Override
    public void onClick(View v) {
        Tracker tracker = ((AnalyticsApplication) getApplicationContext()).getDefaultTracker();
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("google")
                .setAction("login")
                .build());
        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)) {
            case ConnectionResult.SUCCESS:
                Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"}, false, null, null, null, null);
                ((Activity) getBaseContext()).startActivityForResult(intent, Settings.GOOGLE_LOGIN_REQUEST_CODE);
                break;
        }
    }
}
