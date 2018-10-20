package com.nosleep.viewfinder.util;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageProcessing {


    public static String convertBitmapToString(Bitmap input) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        input.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        String result =  Base64.encodeToString(baos.toByteArray(), Base64.URL_SAFE);

        return result;
    }

}
