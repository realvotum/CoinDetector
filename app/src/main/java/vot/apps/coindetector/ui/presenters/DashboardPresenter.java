package vot.apps.coindetector.ui.presenters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.LinearLayout;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import vot.apps.coindetector.ui.listeners.ImageLoaderListener;
import vot.apps.coindetector.ui.model.MatPicture;
import vot.apps.coindetector.ui.model.Picture;
import vot.apps.coindetector.ui.screen_contracts.DashboardScreen;
import vot.apps.coindetector.ui.util.BitmapLoader;
import vot.apps.coindetector.ui.util.FileExtensionFinder;
import vot.apps.coindetector.ui.util.FilePathFinder;
import vot.apps.coindetector.ui.util.GaussianBlur;
import vot.apps.coindetector.ui.util.GrayMatMaker;

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
    private Picture mPicture;
    private MatPicture mMatPicture;
    private String fileExtension;

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

    public void updateScreenImage(){
        mScreen.updateImage(mPicture.getImage());
    }

    @Override
    public void loadingStarted() {
        mScreen.hideImage();
        mScreen.showProgressBar();
    }

    @Override
    public void onImageLoaded(byte[] image) {
        mPicture = new Picture(image);
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

    public void makeItGray(){
        GrayMatMaker grayMat = new GrayMatMaker(mMatPicture.getMatPicture(), fileExtension);
        mMatPicture.setMatPicture(grayMat.getGrayMat());
        mPicture.setImage(grayMat.getGrayMat());
        updateScreenImage();
    }

    public void applyGaussianBlur(){
        GaussianBlur blur = new GaussianBlur(this.mMatPicture.getMatPicture(), this.fileExtension);
        blur.applyGaussianBlur();
        this.mMatPicture.setMatPicture(blur.getGaussianBlurredMat());
        this.mPicture.setImage(blur.getGaussianBlurredMatInByteArray());
        updateScreenImage();
    }
}
