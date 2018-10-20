package com.nosleep.viewfinder.dbobject;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.Objects;

public class DBImage {

    private ImageData imageData;
    private String image;

    public DBImage(ImageData imageData, String image) {
        this.imageData = imageData;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public ImageData getImageData() {
        return imageData;
    }

}
