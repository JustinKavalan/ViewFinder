package com.nosleep.viewfinder.dbobject.;

import java.util.List;

public class ImageData {

    public double latitude;
    public double longitude;
    public List<String> comments;
    public double rating;

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }
}
