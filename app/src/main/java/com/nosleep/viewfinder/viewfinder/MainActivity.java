package com.nosleep.viewfinder.viewfinder;

<<<<<<< HEAD
import android.app.Activity;
=======
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
>>>>>>> e0af219df7ef2e87ffea807ce3100c34e11ea788
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nosleep.viewfinder.util.FirebaseManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FirebaseManager.pushImage(null);
        setContentView(R.layout.activity_main);
    }

    /** Called when user selects location from feed */
    public void getLocationDetails(View view) {
        Intent intent = new Intent(this, LocationDetails.class);
        startActivity(intent);
    }
}
