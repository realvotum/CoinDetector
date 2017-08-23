package vot.apps.coindetector.ui.presenters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import vot.apps.coindetector.ui.activities.DashboardActivity;
import vot.apps.coindetector.ui.listeners.ImageLoaderListener;
import vot.apps.coindetector.ui.model.Picture;
import vot.apps.coindetector.ui.screen_contracts.DashboardScreen;
import vot.apps.coindetector.ui.util.BitmapLoader;
import vot.apps.coindetector.ui.util.FilePathFinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Lap on 2017-08-19.
 */

public class DashboardPresenter implements ImageLoaderListener{

    public static final int SELECT_PICTURE = 1;

    private DashboardScreen mScreen;
    private Context mContext;
    private CircularProgressBar mProgressBar;
    private LinearLayout layoutWithProgressBar;
    private ImageView mImageView;
    private Picture mImage;

    public DashboardPresenter(Context context, DashboardScreen mScreen,
                              CircularProgressBar progressBar, LinearLayout layout, ImageView view){
        this.mScreen = mScreen;
        this.mContext = context;
        this.mProgressBar = progressBar;
        this.layoutWithProgressBar = layout;
        this.mImageView = view;
    }

    public void setProgressBarDimensAndHide(){
        mProgressBar.post(() -> {

            int progressBarWidth = mProgressBar.getMeasuredWidth();
            LinearLayout.LayoutParams lp =
                    new LinearLayout.LayoutParams(progressBarWidth, progressBarWidth);
            mProgressBar.setLayoutParams(lp);
            mScreen.hideProgressBar();
        });
    }

    public void onChooseImageFromGallery(){
        mScreen.chooseImageFromGallery();
    }

    public void setImageChosen(int requestCode, int resultCode, Intent selectedImage){
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = selectedImage.getData();
                BitmapLoader loader = new BitmapLoader(mContext, DashboardPresenter.this);
                loader.execute(selectedImageUri);
            }
        }
    }

    public void showImageLoadingError(){
        mScreen.showImageFromGalleryLoadingError();
    }

    public void updateScreenImage(){
        mScreen.updateImage(mImage.getImage());
    }

    @Override
    public void loadingStarted() {
        mScreen.hideImage();
        mScreen.showProgressBar();
    }

    @Override
    public void onImageLoaded(byte[] image) {
        mImage = new Picture(image);
    }

    @Override
    public void loadingFinished() {
        mScreen.hideProgressBar();
        mScreen.showImage();
        mScreen.updateImage(mImage.getImage());
    }

    @Override
    public void loadingImageError() {
        showImageLoadingError();
    }
}
