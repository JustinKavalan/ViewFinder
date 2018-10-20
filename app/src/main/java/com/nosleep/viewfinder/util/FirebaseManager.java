package com.nosleep.viewfinder.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nosleep.viewfinder.dbobject.ImageData;

public class FirebaseManager {

    public static void pushToImageContent(ImageData data, int hash) {
        String hashString = Integer.toString(hash);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("ImageContent");
        DatabaseReference sampleContent = content.child(hashString);
        sampleContent.setValue(data);
    }

    public static void pushToImage () {

    }

}
