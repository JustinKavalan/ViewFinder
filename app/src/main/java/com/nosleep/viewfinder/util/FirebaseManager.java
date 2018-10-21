package com.nosleep.viewfinder.util;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nosleep.viewfinder.dbobject.ImageData;
import com.nosleep.viewfinder.dbobject.DBImage;
import com.nosleep.viewfinder.viewfinder.MainActivity;

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

    public static void pushToImageUrlStore(String imageUrl, String pushId) {
        //TODO: Check for null
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference("Image");
        DatabaseReference childContent = content.child(pushId);
        childContent.setValue(imageUrl);
    }

    public static void pushImage(ImageData imgData, Bitmap image) {
        //TODO: Check for null
        String pushId = pushToImageData(imgData);
        //TODO: IMPORTANT CHANGE URL TO REAL ONE
        String uri = "/Images/" + pushId;
        String url = "https://storage.googleapis.com/viewfinder-68e3f.appspot.com/Images/" + pushId;
        pushToImageUrlStore(url, pushId);
        uploadImageToCloud(image, uri);
    }

    public static void uploadImageToCloud(Bitmap image, String directory) {
        FirebaseStorage filesystem = FirebaseStorage.getInstance();
        StorageReference root = filesystem.getReference();
        root.child(directory).putBytes(ImageProcessing.convertBitmapToBytes(image));
    }


    public static void getClosestImages(final CloseImageCallback callback, final double latitude,
                                        final double longitude, final int numOfImg,
                                        final boolean isForFeed) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference content = db.getReference();
        final HashMap<String, ImageData> imgDataListWithKey = new HashMap<>();

        content.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Double> list = new HashMap<>();
                DataSnapshot metadata = dataSnapshot.child("Metadata");
                for (DataSnapshot imgDataSnapshot : metadata.getChildren()) {
                    if (!isForFeed
                            || !MainActivity.ITEM_ID_ON_PAGE.contains(imgDataSnapshot.getKey())) {
                        ImageData imgData = imgDataSnapshot.getValue(ImageData.class);
                        double imgLatitude = imgData.getLatitude();
                        double imgLongitude = imgData.getLongitude();
                        double distance = MathSupport.Haversine(latitude, longitude, imgLatitude,
                                imgLongitude);
                        if (list.size() < numOfImg) {
                            list.put(imgDataSnapshot.getKey(), distance);
                            imgDataListWithKey.put(imgDataSnapshot.getKey(), imgData);
                            MainActivity.ITEM_ID_ON_PAGE.add(imgDataSnapshot.getKey());
                        } else {
                            String largestKeyValue = findLargestValueInMap(list);
                            if (distance < list.get(largestKeyValue)) {
                                list.remove(largestKeyValue);
                                imgDataListWithKey.remove(largestKeyValue);
                                MainActivity.ITEM_ID_ON_PAGE.remove(largestKeyValue);
                                list.put(imgDataSnapshot.getKey(), distance);
                                imgDataListWithKey.put(imgDataSnapshot.getKey(), imgData);
                            }
                        }
                    }
                }
                List<ImageData> imgDataList = new ArrayList<>();
                List<String> keyValues = new ArrayList<>();
                List<String> images = new ArrayList<>();
                List<DBImage> imageList = new ArrayList<>();
                for (Map.Entry<String, ImageData> entry : imgDataListWithKey.entrySet()) {
                    imgDataList.add(entry.getValue());
                    keyValues.add(entry.getKey());
                }
                for (String key : keyValues) {
                    DataSnapshot image = dataSnapshot.child("Image").child(key);
                    String imageUrl = image.getValue(String.class);
                    images.add(imageUrl);
                }
                for (int i = 0; i < keyValues.size(); i++) {
                    DBImage dbi = new DBImage(imgDataList.get(i), images.get(i));
                    imageList.add(dbi);
                }

                callback.callback(imageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            private String findLargestValueInMap(Map<String, Double> map) {
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
    }
}
