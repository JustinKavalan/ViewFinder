package com.nosleep.viewfinder.dbobject;

import android.graphics.Bitmap;

public class DBImage {

    private ImageData imageData;
    private Bitmap image;

    public DBImage(ImageData imageData, Bitmap image) {
        this.imageData = imageData;
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public ImageData getImageData() {
        return imageData;
    }

}
