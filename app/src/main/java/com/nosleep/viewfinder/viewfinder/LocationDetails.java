package com.nosleep.viewfinder.viewfinder;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.nosleep.viewfinder.dbobject.DBImage;
import com.nosleep.viewfinder.util.FirebaseCallback;
import com.nosleep.viewfinder.util.FirebaseManager;

import java.util.List;

public class LocationDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        final ViewPager mViewPager = findViewById(R.id.vp_location_images);

        FirebaseCallback callback = new FirebaseCallback() {
            ViewPager viewPager = mViewPager;

            @Override
            public void callback(List<DBImage> result) {
                Bitmap[] images = new Bitmap[result.size()];
                for (int i = 0; i < result.size(); i++) {
                    images[i] = result.get(i).getImage();
                }

                Log.d("Backend Connection", "" + result.size());

                viewPager.setAdapter(new CustomPageAdapter(LocationDetails.this, images));

            }
        };
        FirebaseManager.getClosestImages(callback,0.0,0.0,3);

        // Get the intent that started this activity
        Intent intent = getIntent();
    }
}
