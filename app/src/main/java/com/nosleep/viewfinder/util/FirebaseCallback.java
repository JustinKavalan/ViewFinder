package com.nosleep.viewfinder.util;

import com.nosleep.viewfinder.dbobject.DBImage;

import java.util.List;

public interface FirebaseCallback {
    void callback(List<DBImage> result);
}
