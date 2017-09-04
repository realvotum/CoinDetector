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

                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(bytes, 0, bytes.length);
                buf.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            bytes = null;
        } catch (IOException e) {
            bytes = null;
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    protected void onPostExecute(byte[] result){

        if(mContextWeakReference.get() != null && mImageLoaderListenerWeakReference.get() != null){
            if(result == null){
                mImageLoaderListenerWeakReference.get().loadingImageError();
            }else {
                mImageLoaderListenerWeakReference.get().onImageLoaded(result);
                mImageLoaderListenerWeakReference.get().loadingFinished();
            }
        }
    }
}
