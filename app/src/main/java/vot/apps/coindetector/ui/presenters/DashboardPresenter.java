package vot.apps.coindetector.ui.presenters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.LinearLayout;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import vot.apps.coindetector.ui.listeners.ImageLoaderListener;
import vot.apps.coindetector.ui.model.MatPicture;
import vot.apps.coindetector.ui.model.OriginalPicture;
import vot.apps.coindetector.ui.screen_contracts.DashboardScreen;
import vot.apps.coindetector.ui.util.BitmapLoader;
import vot.apps.coindetector.ui.util.FileExtensionFinder;
import vot.apps.coindetector.ui.util.FilePathFinder;
import vot.apps.coindetector.ui.util.ImageProcessorTask;

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
    private OriginalPicture mPicture;
    private MatPicture mMatPicture;
    private String fileExtension;
    private Thread thread;
    private Handler mHandler;

    public DashboardPresenter(Context context, DashboardScreen mScreen,
                              CircularProgressBar progressBar, LinearLayout layout, ImageView view){
        this.mScreen = mScreen;
        this.mContext = context;
        this.mProgressBar = progressBar;
        this.layoutWithProgressBar = layout;
        this.mImageView = view;
        this.mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message inputMessage) {
                mPicture.setImage((byte[]) inputMessage.obj);
                mMatPicture.setMatPicture((byte[]) inputMessage.obj);
                mScreen.hideProgressBar();
                mScreen.updateImage(mPicture.getImage());
            }
        };
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
                FilePathFinder finder = new FilePathFinder(mContext, selectedImageUri);
                this.fileExtension = FileExtensionFinder.getExtension(finder.getRealPathFromUri());
                BitmapLoader loader = new BitmapLoader(mContext, DashboardPresenter.this);
                loader.execute(selectedImageUri);
            }
        }
    }

    public void showImageLoadingError(){
        mScreen.showImageFromGalleryLoadingError();
    }

    @Override
    public void loadingStarted() {
        mScreen.hideImage();
        mScreen.showProgressBar();
    }

    @Override
    public void onImageLoaded(byte[] image) {
        mPicture = new OriginalPicture(image);
        if(fileExtension.length() > 0) {
            mMatPicture = new MatPicture(image, fileExtension);
        }else{
            mMatPicture = new MatPicture(image);
        }
    }

    @Override
    public void loadingFinished() {
        mScreen.hideProgressBar();
        mScreen.showImage();
        mScreen.updateImage(mPicture.getImage());
    }

    @Override
    public void loadingImageError() {
        showImageLoadingError();
    }

    public void startImageProcessingProcedure(){
        mScreen.showProgressBar();
        ImageProcessorTask task = new ImageProcessorTask(this.mMatPicture,
                this.mPicture, this.fileExtension, this.mHandler);
        this.thread = new Thread(task);
        thread.start();
    }

    public void stopProcessing(){
        if(thread != null){
            if(thread.isAlive()){
                thread.interrupt();
            }
        }
    }

}
