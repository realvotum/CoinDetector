package vot.apps.coindetector.ui.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.lang.ref.WeakReference;

import vot.apps.coindetector.ui.listeners.ImageLoaderListener;


public class BitmapLoader extends AsyncTask<Uri, Void, byte[]> {

    private WeakReference<Context> mContextWeakReference;
    private WeakReference<ImageLoaderListener> mImageLoaderListenerWeakReference;

    public BitmapLoader(Context context, ImageLoaderListener loaderListener){
        mContextWeakReference = new WeakReference<>(context);
        mImageLoaderListenerWeakReference = new WeakReference<>(loaderListener);
    }

    @Override
    protected void onPreExecute(){
        if(mContextWeakReference.get() != null && mImageLoaderListenerWeakReference.get() != null)
            mImageLoaderListenerWeakReference.get().loadingStarted();
    }

    @Override
    protected byte[] doInBackground(Uri... params) {

        FilePathFinder finder = new FilePathFinder(mContextWeakReference.get(), params[0]);
        File file = new File(finder.getRealPathFromUri());


        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {

            int degreesToRotate = degreesOfRotation(file);

            if (degreesToRotate != 0) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                bitmap = rotate(bitmap, degreesToRotate);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                bytes = stream.toByteArray();

            }else{
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(bytes, 0, bytes.length);
                buf.close();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private int degreesOfRotation(File file) throws IOException {
        ExifInterface ei = new ExifInterface(file.getAbsolutePath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return 90;
            case ExifInterface.ORIENTATION_ROTATE_90:
                return 0;
            case ExifInterface.ORIENTATION_ROTATE_180:
                return 270;
            case ExifInterface.ORIENTATION_ROTATE_270:
                return 0;
            default:
                return 0;
        }
    }

    private Bitmap rotate(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @Override
    protected void onPostExecute(byte[] result){

        if(mContextWeakReference.get() != null && mImageLoaderListenerWeakReference.get() != null){
            mImageLoaderListenerWeakReference.get().onImageLoaded(result);
            mImageLoaderListenerWeakReference.get().loadingFinished();
        }
    }
}
