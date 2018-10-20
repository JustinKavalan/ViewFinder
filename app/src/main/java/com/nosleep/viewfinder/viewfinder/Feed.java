package com.nosleep.viewfinder.viewfinder;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

public class Feed extends Activity {

    Button btnCallImage;
    ImageView ivPostImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        btnCallImage = findViewById(R.id.btnCallImage);
        ivPostImage = findViewById(R.id.ivPostImage);

        btnCallImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //put the firebase getter here to get closest image
             }
        });
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

    private List<Image>  getClosestImages(int numImages, double longitude, double latitude) {
        //get closest data through metadata
        return null;
    }
}
