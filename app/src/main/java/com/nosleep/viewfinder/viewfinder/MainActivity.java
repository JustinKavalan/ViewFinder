package com.nosleep.viewfinder.viewfinder;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import java.io.ByteArrayOutputStream;
import com.nosleep.viewfinder.dbobject.DBImage;
import com.nosleep.viewfinder.dbobject.ImageData;
import com.nosleep.viewfinder.util.CloseImageCallback;
import com.nosleep.viewfinder.util.FirebaseManager;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnCallImage;
    double longitude, latitude;
    static final int REQUEST_LOCATION = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private RecyclerView mRecyclerView;
    final RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(new Bitmap[0]);
    private RecyclerView.LayoutManager mLayoutManager;
    LocationManager locationManager;

    public static List<String> ITEM_ID_ON_PAGE = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Bitmap test = BitmapFactory.decodeResource(getResources(), R.drawable.night_sky_2);
//        FirebaseManager.pushImage(new ImageData(), test);
        int size = 10;

        final Bitmap[] images = new Bitmap[size];

        CloseImageCallback callback = new CloseImageCallback() {

            @Override
            public void callback(final List<DBImage> result) {

                new AsyncTask<URL,Integer, Integer>(){
                    @Override
                    protected Integer doInBackground(URL... urls) {

                        for (int i = 0; i < result.size(); i++) {
                            String url = result.get(i).getImageUrl();
                            try {
                                images[i] = Picasso.get().load(url).get();
                            } catch(Exception e) {
                                Log.e("Error", "Failed to get https resource: ", e);
                                return 1;
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.setmDataset(images);
                                mAdapter.notifyDataSetChanged();
                            }
                        });

                        return 0;

                    }
                }.execute();
                //not sure how to fix this but you'll need to restructure

            }
        };
        FirebaseManager.getClosestImages(callback,0.0,0.0,size, true);

        // Create Recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mRecyclerView.setAdapter(mAdapter);


        // Location stuff
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getLocation();

        btnCallImage = findViewById(R.id.btnCallImage);
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
                return super.onOptionsItemSelected(item);
        }
    }

    /** Called when user selects location from feed*/
    public void getLocationDetails(View view) {
        Intent intent = new Intent(this, LocationDetails.class);
        startActivity(intent);
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
