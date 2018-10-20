package com.nosleep.viewfinder.viewfinder;

import android.app.Activity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nosleep.viewfinder.util.FirebaseManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseManager.pushToDB();
        setContentView(R.layout.activity_main);
    }
}
