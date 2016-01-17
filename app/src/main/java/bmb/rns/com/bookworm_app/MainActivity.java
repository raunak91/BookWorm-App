package bmb.rns.com.bookworm_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import bmb.rns.com.bookworm_app.handlers.click.FacebookSignIn;
import bmb.rns.com.bookworm_app.handlers.click.GoogleSignIn;
import bmb.rns.com.bookworm_app.settings.Initialize;
import bmb.rns.com.bookworm_app.settings.Settings;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //    Use when using vibrator - Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        //call this function to vibrate vibrator.vibrate(1000);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        sendTestMessageGA();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        new Initialize(this);
    }

    private void sendTestMessageGA() {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Activity")
                .setAction("App opened")
                .build());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Initialize.animator_app.setInAnimation(Initialize.inAnim_back);
            Initialize.animator_app.setOutAnimation(Initialize.outAnim_back);
            if (Initialize.animator_app.getDisplayedChild() != 0) {
                Initialize.animator_app.showPrevious();
            } else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {

            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("User Action")
                    .setAction("Share Button click")
                    .build());
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = Settings.APP_LINK;
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));


        } else if (id == R.id.nav_profile) {
// add profile view
//            setContentView(R.layout.profile_page);
            Initialize.home_animator.showNext();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Settings.GOOGLE_LOGIN_REQUEST_CODE:
            case Settings.RECOVER_FROM_PLAY_SERVICES_ERROR_REQUEST_CODE:
                GoogleSignIn.onActivityResult(data, MainActivity.this);
                break;
            case Settings.FACEBOOK_LOGIN_REQUEST_CODE:
                FacebookSignIn.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
