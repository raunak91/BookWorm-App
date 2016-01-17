package bmb.rns.com.bookworm_app.asynctasks;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import bmb.rns.com.bookworm_app.R;

/**
 * Created by touchy on 17/1/16.
 */
public class UpdateProfilePhoto extends AsyncTask<String, Void, Bitmap> {

    private final View header;
    private final Activity home;

    public UpdateProfilePhoto(View header, Activity home) {
        this.header = header;
        this.home = home;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        String domain = params[1];
        if (domain == "facebook") {
            try {
                HttpURLConnection con = (HttpURLConnection) (new URL(url).openConnection());
                con.setInstanceFollowRedirects(false);
                con.connect();
                int responseCode = con.getResponseCode();
                if (responseCode == 301 || responseCode == 302) {
                    url = con.getHeaderField("Location");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        ImageView profilePhoto = (ImageView) header.findViewById(R.id.profile_photo);
        ImageView profilePagePhoto = (ImageView) home.findViewById(R.id.profile_page_photo);
        profilePagePhoto.setImageBitmap(bitmap);
        profilePhoto.setImageBitmap(bitmap);
    }
}
