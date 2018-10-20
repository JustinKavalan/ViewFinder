package com.nosleep.viewfinder.util;

import android.media.Image;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nosleep.viewfinder.dbobject.ImageData;
import com.nosleep.viewfinder.dbobject.DBImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public class FirebaseManager {

    public static String pushToImageContent(ImageData data) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("Metadata");
        content.setValue(data);
        //TODO: pushId defualts to metadata, probably push than add with key
        String pushId = content.push().getKey();
        return pushId;
    }

    private static void pushToImage (Image image, String pushId) {
        //TODO: Check for null
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("Image");
        DatabaseReference childContent = content.child(pushId);
        childContent.setValue(image);

    }

    public static void pushImage(DBImage img){
        //TODO: Check for null
        String pushId = pushToImageContent(img.getImageData());
        pushToImage(img.getImage(), pushId);
    }

    public static List<Object> getClosestImages (final double latitude, final double longitude,
                                                 final int numOfImg) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("Metadata");
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
                    } else {
                        double largestDistance = 0.0;
                        String largestKeyValue = "";
                        String entryKeyVal = "";
                        double entryDistance = 0.0;
                        for (Map.Entry<String, Double> entry : list.entrySet()) {
                            if (distance < entry.getValue() && entry.getValue() > largestDistance) {
                                largestDistance = entry.getValue();
                                largestKeyValue = entry.getKey();
                                entryKeyVal = entry.getKey();
                                entryDistance = entry.getValue();
                            }
                        }
                        if (!largestKeyValue.isEmpty()) {
                            list.remove(largestKeyValue);
                            list.put(entryKeyVal, entryDistance);
                        }
                    }
                }
                for (Map.Entry<String, Double> e : list.entrySet()) {
                    Log.d("KeyString", e.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return null;
    }
}
