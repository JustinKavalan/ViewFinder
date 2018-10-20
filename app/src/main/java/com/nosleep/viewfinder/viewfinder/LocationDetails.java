package com.nosleep.viewfinder.viewfinder;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class LocationDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        ViewPager viewPager = findViewById(R.id.vp_location_images);
        viewPager.setAdapter(new CustomPageAdapter(this));

        // Get the intent that started this activity
        Intent intent = getIntent();
    }
}
