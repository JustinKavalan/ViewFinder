package com.nosleep.viewfinder.viewfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.nosleep.viewfinder.dbobject.ImageData;

public class Post extends AppCompatActivity {

    double longitude, latitude;
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;

    Button btnPost;
    ImageView ivPostPreview;
    EditText etCaption;
    RatingBar rbRating;
    double star = 5.0;
    String caption = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        ImageView img = (ImageView) findViewById(R.id.ivPostPreview);
        img.setImageBitmap(bmp);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getLocation();

        btnPost = findViewById(R.id.btnPost);
        ivPostPreview = findViewById(R.id.ivPostPreview);
        etCaption = findViewById(R.id.etCaption);
        rbRating = findViewById(R.id.rbRating);

        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                star = rbRating.getRating();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caption = etCaption.getText().toString();
                ImageData imgData = new ImageData();
                imgData.setCaption(caption);
            }
        });

    }

    void getLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                System.out.println("Latitude: " + latitude + " | Longitude: " + longitude);
            } else {
                System.out.println("unable to find current location");
            }
        }
    }
}
