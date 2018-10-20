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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.google.firebase.database.FirebaseDatabase;
import com.nosleep.viewfinder.dbobject.DBImage;
import com.nosleep.viewfinder.dbobject.ImageData;
import com.nosleep.viewfinder.util.FirebaseManager;
import java.io.ByteArrayOutputStream;

public class MainActivity extends Activity {

    Button btnCallImage;
    ImageView ivPostImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Bitmap test = BitmapFactory.decodeResource(getResources(), R.drawable.night_sky_1);
//        String output = FirebaseManager.convertBitmapToString(test);
//        FirebaseManager.pushToImageContent(output, "test");

        btnCallImage = findViewById(R.id.btnCallImage);

//        btnCallImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //put the firebase getter here
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /** Called when user selects location from feed*/
    public void getLocationDetails(View view) {
        Intent intent = new Intent(this, LocationDetails.class);

        setContentView(R.layout.activity_location_details);

        startActivity(intent);

//        FirebaseManager.pushImage(null);
    }

}
