package com.nosleep.viewfinder.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class ImageProcessing {


    public static String convertBitmapToString(Bitmap input) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        String result;
        int quality = 100;

        do {
            input.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            result = Base64.encodeToString(baos.toByteArray(), Base64.URL_SAFE);
            quality -= 10;
        } while (result.length() >= 10000000);

        return result;
    }

    public static Bitmap convertStringToBitmap(String input) {
        byte[] decodedBytes = Base64.decode(input, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

}
