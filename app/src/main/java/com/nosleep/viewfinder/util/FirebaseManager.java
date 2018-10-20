package com.nosleep.viewfinder.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseManager {

    /**
     *
     */
    public void pushToDB() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("/Content");
        DatabaseReference samplePic = content.child("PlaceholderHash");
        samplePic.setValue("PlaceholderImage");

////        myRef.setValue("Test1");
//    }
//
//    private void pushImageToDB() {
        DatabaseReference metadata = db.getReference("/Metadata");
        DatabaseReference sampleMetadata = metadata.child("PlaceHolderHash");
        sampleMetadata.setValue("PlaceholderMetadata");
    }
}
