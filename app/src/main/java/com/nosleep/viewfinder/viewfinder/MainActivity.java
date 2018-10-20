package com.nosleep.viewfinder.viewfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;
import com.nosleep.viewfinder.dbobject.ImageData;
import com.nosleep.viewfinder.util.FirebaseManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageData imd = new ImageData();
        FirebaseManager.pushToImageContent(imd);
        FirebaseManager.getClosestImages(0, 0,4);
        setContentView(R.layout.activity_main);
    }

    /** Called when user selects location from feed */
    public void getLocationDetails(View view) {
        Intent intent = new Intent(this, LocationDetails.class);
        startActivity(intent);
    }
}
