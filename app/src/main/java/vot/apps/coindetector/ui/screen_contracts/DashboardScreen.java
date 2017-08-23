package vot.apps.coindetector.ui.screen_contracts;

import android.graphics.Bitmap;

/**
 * Created by Lap on 2017-08-19.
 */

public interface DashboardScreen {
    void updateImage(byte[] imageArray);
    void chooseImageFromGallery();
    void showImageFromGalleryLoadingError();
    void showProgressBar();
    void hideProgressBar();
    void showImage();
    void hideImage();
}
