package com.nosleep.viewfinder.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class ImageProcessing {


    public static String convertBitmapToString(Bitmap input) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        input.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        String result =  Base64.encodeToString(baos.toByteArray(), Base64.URL_SAFE);
        Log.d("RESULT", "" + result.length());

        return result;
    }

    public static Bitmap convertStringToBitmap(String input) {
        byte[] decodedBytes = Base64.decode(input, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

}
