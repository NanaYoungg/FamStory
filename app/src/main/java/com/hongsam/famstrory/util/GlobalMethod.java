package com.hongsam.famstrory.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class GlobalMethod {

    // 비트맵 회전이슈 관련 함수
    public static Bitmap RotateBitmap(Bitmap bm, String path) {
        Matrix matrix = new Matrix();
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true); // rotating bitmap
    }

    public static String GetPathFromUri(Context context, Uri uri){

        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null );
        cursor.moveToNext();
        String path = cursor.getString( cursor.getColumnIndex( "_data" ) );
        cursor.close();

        return path;

    }

    public static Bitmap FileToBitmap(File file) {
        String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);

        return bitmap;
    }

    public static String saveToInternalStorage(Context context, Bitmap bitmapImage, String fileName){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("data", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory,fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public static Bitmap loadImageFromStorage(String p, String fileName)
    {
        String path = p.replace("\"", "");

        try {
            File f=new File(path, fileName);
            Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(f));
            return bm;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }

    }
}
