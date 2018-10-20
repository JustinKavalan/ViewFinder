package com.nosleep.viewfinder.viewfinder;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.nosleep.viewfinder.dbobject.DBImage;
import com.nosleep.viewfinder.util.FirebaseManager;

import java.util.List;

public class LocationDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        List<DBImage> temp = FirebaseManager.getClosestImages(0.0,0.0,3);
        Bitmap[] images = new Bitmap[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            images[i] = temp.get(i).getImage();
        }

        Log.d("Backend Connection", "" + temp.size());

        ViewPager viewPager = findViewById(R.id.vp_location_images);
        viewPager.setAdapter(new CustomPageAdapter(this, images));

        // Get the intent that started this activity
        Intent intent = getIntent();
    }
}
