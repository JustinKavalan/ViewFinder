package com.nosleep.viewfinder.viewfinder;

import android.app.Activity;
import android.content.Intent;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.nosleep.viewfinder.dbobject.DBImage;
import com.nosleep.viewfinder.dbobject.ImageData;
import com.nosleep.viewfinder.util.FirebaseManager;

import java.io.ByteArrayOutputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        Bitmap test = BitmapFactory.decodeResource(getResources(), R.drawable.night_sky_1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        test.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        String result =  Base64.encodeToString(baos.toByteArray(), Base64.URL_SAFE);
        DBImage test1 = new DBImage(new ImageData(), result);
        FirebaseManager.pushImage(test1);

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_location_images);
        viewPager.setAdapter(new CustomPageAdapter(this));

    }

    /** Called when user selects location from feed */
    public void getLocationDetails(View view) {
        Intent intent = new Intent(this, LocationDetails.class);
        startActivity(intent);
    }
}
