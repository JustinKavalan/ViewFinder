package com.nosleep.viewfinder.dbobject;

import android.media.Image;

import java.util.Objects;

public class DBImage {

    private ImageData imageData;
    private Image image;

    public DBImage(ImageData metadata, Image image) {
        this.imageData = imageData;
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public ImageData getImageData() {
        return imageData;
    }

}
