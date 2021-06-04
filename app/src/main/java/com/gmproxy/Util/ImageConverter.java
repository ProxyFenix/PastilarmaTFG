package com.gmproxy.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class ImageConverter {
    @TypeConverter
    public byte[] bitmapToBiteArray(Bitmap value) {
        if (value == null)
            return null;
        else {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            value.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            return outputStream.toByteArray();
        }
    }


    @TypeConverter
    public Bitmap byteArrayToBitmap(byte[] value) {
        if (value == null) {
            return null;
        } else {
            return BitmapFactory.decodeByteArray(value, 0, value.length);
        }
    }
}