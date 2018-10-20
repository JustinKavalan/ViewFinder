package com.nosleep.viewfinder.viewfinder;

import android.app.Activity;
import android.content.Intent;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.util.ArrayList;
import java.util.List;

public class LocationDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        final ViewPager mViewPager = findViewById(R.id.vp_location_images);

        CloseImageCallback callback = new CloseImageCallback() {
            ViewPager viewPager = mViewPager;

            @Override
            public void callback(List<DBImage> result) {
                final Bitmap[] images = new Bitmap[result.size()];
                for (int i = 0; i < result.size(); i++) {
                    FirebaseManager.getBitmapFromCloud(new BitmapCallback() {
                        @Override
                        public void callback(Bitmap result) {
//                            images[i] =
                        }
                    }, result.get(i).getImageUrl());
                }

                //not sure how to fix this but you'll need to restructure
                viewPager.setAdapter(new CustomPageAdapter(LocationDetails.this, images));

            }
        };
        FirebaseManager.getClosestImages(callback,0.0,0.0,3, true);

        // Get the intent that started this activity
        Intent intent = getIntent();
    }
}
