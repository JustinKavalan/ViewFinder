package com.nosleep.viewfinder.util;

import android.media.Image;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nosleep.viewfinder.dbobject.ImageData;
import com.nosleep.viewfinder.dbobject.DBImage;


import java.util.List;
import java.util.Objects;

public class FirebaseManager {

    public static String pushToImageContent(ImageData data) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("Metadata");
        content.push().setValue(data);
        String pushId = content.getKey();
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

    public List<Object> getClosestImages (double latitude, double longitude, int numOfImg) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("Metadata");
        return null;
    }
}
