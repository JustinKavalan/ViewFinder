package com.nosleep.viewfinder.dbobject;

import android.media.Image;

import java.util.Objects;

public class DBImage {
    private ImageData metadata;
    private Image image;
    private int hash;

    public DBImage(ImageData metadata, Image image) {
        this.metadata = metadata;
        this.image = image;
        this.hash = Objects.hash(image);
    }

    public Image getImage() {
        return image;
    }

    public ImageData getMetadata() {
        return metadata;
    }

    public int getHash() {
        return hash;
    }
}
