package com.nosleep.viewfinder.util;

import android.media.Image;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nosleep.viewfinder.dbobject.ImageData;
import com.nosleep.viewfinder.dbobject.DBImage;


import java.util.Objects;

public class FirebaseManager {

    private static void pushToImageContent(ImageData data, int hash) {
        String hashString = Integer.toString(hash);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("ImageContent");
        DatabaseReference sampleContent = content.child(hashString);
        sampleContent.setValue(data);
    }

    /**
     * @param image the image to push
     * @param hash the hash of the image
     */
    private static void pushToImage (Image image, int hash) {
        //TODO: Check for null
        String hashString = Integer.toString(hash);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("Metadata");
        DatabaseReference sampleContent = content.child(hashString);
        sampleContent.setValue(image);

    }

    public static void pushImage(DBImage image){
        //TODO: Check for null
        int hash = Objects.hash(image.getMetadata());
        pushToImage(image.getImage(), image.getHash());
        pushToImageContent(image.getMetadata(), image.getHash());
    }

}
