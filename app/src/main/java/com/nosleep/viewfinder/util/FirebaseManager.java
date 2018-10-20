package com.nosleep.viewfinder.util;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nosleep.viewfinder.dbobject.ImageData;
import com.nosleep.viewfinder.dbobject.DBImage;


import java.util.List;
import java.util.Objects;

public class FirebaseManager {

    public static String pushToImageData(ImageData data) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("Metadata");
        content.push().setValue(data);
        String pushId = content.push().getKey();
        return pushId;
    }

    public static void pushToImageContent(String image, String pushId) {
        //TODO: Check for null
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("Image");
        DatabaseReference childContent = content.child(pushId);
        Log.d("STRINGSIZE", "" + image.length());
        childContent.setValue(image);
    }

    public static void pushImage(DBImage img){
        //TODO: Check for null
        String pushId = pushToImageData(img.getImageData());
        pushToImageContent(img.getImage(), pushId);
    }

    public List<Object> getClosestImages (double latitude, double longitude, int numOfImg) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("Metadata");
        return null;
    }
}
