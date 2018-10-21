package com.nosleep.viewfinder.viewfinder;

import android.app.Activity;
import android.content.Intent;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nosleep.viewfinder.dbobject.DBImage;
import com.nosleep.viewfinder.util.BitmapCallback;
import com.nosleep.viewfinder.util.CloseImageCallback;
import com.nosleep.viewfinder.util.FirebaseManager;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LocationDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        final ViewPager mViewPager = findViewById(R.id.vp_location_images);

        final CustomPageAdapter mViewAdapter = new CustomPageAdapter(LocationDetails.this);
        mViewPager.setAdapter(mViewAdapter);


        CloseImageCallback callback = new CloseImageCallback() {
            ViewPager viewPager = mViewPager;

            @Override
            public void callback(final List<DBImage> result) {

                new AsyncTask<URL,Integer, Integer>(){
                    @Override
                    protected Integer doInBackground(URL... urls) {

                        final Bitmap[] images = new Bitmap[result.size()];
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
                                mViewAdapter.setImages(images);
                                mViewAdapter.notifyDataSetChanged();
                            }
                        });

                        return 0;

                    }
                }.execute();
                //not sure how to fix this but you'll need to restructure

            }
        };
        FirebaseManager.getClosestImages(callback,0.0,0.0,3, true);

        // Get the intent that started this activity
        Intent intent = getIntent();
    }
}
