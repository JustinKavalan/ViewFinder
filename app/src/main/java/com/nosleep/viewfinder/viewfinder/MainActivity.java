package com.nosleep.viewfinder.viewfinder;

import android.app.Activity;
import android.content.Intent;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nosleep.viewfinder.util.FirebaseManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_location_images);
        viewPager.setAdapter(new CustomPageAdapter(this));

//        FirebaseManager.pushImage(null);
    }

    /** Called when user selects location from feed */
    public void getLocationDetails(View view) {
        Intent intent = new Intent(this, LocationDetails.class);
        startActivity(intent);
    }
}
