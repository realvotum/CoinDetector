package vot.apps.coindetector.ui.listeners;

/**
 * Created by Lap on 2017-08-21.
 */

public interface ImageLoaderListener {
    void loadingStarted();
    void onImageLoaded(byte[] image);
    void loadingFinished();
    void loadingImageError();
}
