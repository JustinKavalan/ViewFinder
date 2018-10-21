package com.nosleep.viewfinder.dbobject;

import android.graphics.Bitmap;

public class DBImage {

    private ImageData imageData;
    private String imageUrl;

    public DBImage(ImageData imageData, String imageUrl) {
        this.imageData = imageData;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ImageData getImageData() {
        return imageData;
    }

}
