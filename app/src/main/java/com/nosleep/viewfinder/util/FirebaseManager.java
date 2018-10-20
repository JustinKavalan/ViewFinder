package com.nosleep.viewfinder.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseManager {

    public static void pushToDB() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("Content");
        DatabaseReference sampleContent = content.child("PlaceholderHash");
        sampleContent.setValue("PlaceholderImage");
    }

//    public static
}
