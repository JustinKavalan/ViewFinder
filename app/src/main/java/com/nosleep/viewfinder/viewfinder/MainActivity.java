package com.nosleep.viewfinder.viewfinder;

import android.app.Activity;
import android.content.Intent;

import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nosleep.viewfinder.util.FirebaseManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btnCallImage;
    Button btnActionPost;
    ImageView ivPostImage;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            case R.id.action_post:
                dispatchTakePictureIntent();
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


    /** Calls phone camera to take picture */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            System.out.println(imageBitmap);

            //Convert to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            //Pass data within activity using intent
            Intent intent = new Intent(this, Post.class);
            intent.putExtra("image", byteArray);
            startActivity(intent);
        }
    }
}
