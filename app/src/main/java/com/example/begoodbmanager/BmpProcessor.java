package com.example.begoodbmanager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.nio.ByteBuffer;

public class BmpProcessor
{
    public static Bitmap byteArrayToBmp(int width, int height, byte[] img)
    {
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        ByteBuffer buffer = ByteBuffer.wrap(img);
        bmp.copyPixelsFromBuffer(buffer);

        return bmp;
    }

    public static Bitmap scaleBmp(float scaleFactor, Bitmap bmp)
    {
        int width  = (int) (bmp.getWidth() * scaleFactor);
        int height = (int) (bmp.getHeight() * scaleFactor);

        return Bitmap.createScaledBitmap(bmp, width, height, false);
    }

    public static Bitmap resourceToBmp(int resId, Activity activity)
    {
        return BitmapFactory.decodeResource(activity.getResources(), resId);
    }

    public static byte[] bmpToByteArray(Bitmap bmp)
    {
        ByteBuffer byteBuffer = ByteBuffer.allocate(bmp.getRowBytes() * bmp.getHeight());
        bmp.copyPixelsToBuffer(byteBuffer);

        return byteBuffer.array();
    }
}
