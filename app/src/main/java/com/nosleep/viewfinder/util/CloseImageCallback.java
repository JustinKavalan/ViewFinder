package com.nosleep.viewfinder.util;

import android.graphics.Bitmap;

import com.nosleep.viewfinder.dbobject.DBImage;

import java.util.List;

public interface CloseImageCallback {

    void callback(List<DBImage> result);

}
