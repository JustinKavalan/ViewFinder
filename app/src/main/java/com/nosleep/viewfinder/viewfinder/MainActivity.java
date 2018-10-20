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

import com.google.firebase.database.FirebaseDatabase;
import com.nosleep.viewfinder.dbobject.DBImage;
import com.nosleep.viewfinder.dbobject.ImageData;
import com.nosleep.viewfinder.util.FirebaseManager;

import java.io.ByteArrayOutputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageData imd = new ImageData();
        FirebaseManager.pushToImageData(imd);
        Log.d("PLSWRK", ""+ FirebaseManager.getClosestImages(0, 0,4));
        setContentView(R.layout.activity_main);

//       Bitmap test = BitmapFactory.decodeResource(getResources(), R.drawable.night_sky_1);
//        String output = FirebaseManager.convertBitmapToString(test);
//        FirebaseManager.pushToImageContent(output, "test");

    }

    /** Called when user selects location from feed */
    public void getLocationDetails(View view) {
        Intent intent = new Intent(this, LocationDetails.class);
        startActivity(intent);
    }
}
