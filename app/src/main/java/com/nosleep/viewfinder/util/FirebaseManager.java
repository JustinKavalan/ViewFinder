package com.nosleep.viewfinder.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nosleep.viewfinder.dbobject.ImageData;
import com.nosleep.viewfinder.dbobject.DBImage;
import com.nosleep.viewfinder.viewfinder.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public class FirebaseManager {

    public static String pushToImageData(ImageData data) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("Metadata");
        String pushId = content.push().getKey();
        content.child(pushId).setValue(data);
        return pushId;
    }

    public static void pushToImageContent(Bitmap image, String pushId) {
        //TODO: Check for null
        String imgStr = ImageProcessing.convertBitmapToString(image);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("Image");
        DatabaseReference childContent = content.child(pushId);
        childContent.setValue(imgStr);
    }

    public static void pushImage(DBImage img){
        //TODO: Check for null
        String pushId = pushToImageData(img.getImageData());
        pushToImageContent(img.getImage(), pushId);
    }

    private static Bitmap getImageByKey (final String key) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("Image").child(key);
        final Bitmap[] image = new Bitmap[1];
        content.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                image[0] = ImageProcessing.convertStringToBitmap(dataSnapshot.getValue(
                        String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return image[0];
    }

    public static List<DBImage> getClosestImages (final double latitude, final double longitude,
                                                 final int numOfImg) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("Metadata");
        final HashMap<String, ImageData> imgDataListWithKey = new HashMap<>();

        content.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Double> list = new HashMap<>();
                for (DataSnapshot imgDataSnapshot : dataSnapshot.getChildren()) {
                    ImageData imgData = imgDataSnapshot.getValue(ImageData.class);
                    double imgLatitude = imgData.getLatitude();
                    double imgLongitude = imgData.getLongitude();
                    double distance = MathSupport.Haversine(latitude, longitude, imgLatitude,
                            imgLongitude);
                    if (list.size() < numOfImg) {
                        list.put(imgDataSnapshot.getKey(), distance);
                        imgDataListWithKey.put(imgDataSnapshot.getKey(), imgData);
                    } else {
                        String largestKeyValue = findLargestValueInMap(list);
                        if (distance < list.get(largestKeyValue)) {
                            list.remove(largestKeyValue);
                            imgDataListWithKey.remove(largestKeyValue);
                            list.put(imgDataSnapshot.getKey(), distance);
                            imgDataListWithKey.put(imgDataSnapshot.getKey(), imgData);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            private String findLargestValueInMap(HashMap<String, Double> map) {
                double largestVal = 0.0;
                String largestKey = "";
                for (Map.Entry<String, Double> entry : map.entrySet()) {
                    if (entry.getValue() >= largestVal) {
                        largestVal = entry.getValue();
                        largestKey = entry.getKey();
                    }
                }
                return largestKey;
            }
        });

        List<ImageData> imgDataList = new ArrayList<>();
        List<String> keyValues = new ArrayList<>();
        List<Bitmap> images = new ArrayList<>();
        List<DBImage> imageList = new ArrayList<>();
        for (Map.Entry<String, ImageData> entry : imgDataListWithKey.entrySet()) {
            imgDataList.add(entry.getValue());
            keyValues.add(entry.getKey());
        }
        for (String key : keyValues) {
            images.add(getImageByKey(key));
        }
        for (int i = 0; i < keyValues.size(); i++) {
            DBImage dbi = new DBImage(imgDataList.get(i), images.get(i));
            imageList.add(dbi);
        }
        return imageList;
    }
}
