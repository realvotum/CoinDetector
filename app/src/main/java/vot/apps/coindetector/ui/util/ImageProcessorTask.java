package vot.apps.coindetector.ui.util;

import android.os.Handler;
import android.os.Message;

import vot.apps.coindetector.ui.model.MatPicture;
import vot.apps.coindetector.ui.model.OriginalPicture;

/**
 * Created by Lap on 2017-09-04.
 */

public class ImageProcessorTask implements Runnable{

    public static final int IMAGE_PROCESSING_FINISHED = 32;

    private MatPicture mMatPicture;
    private OriginalPicture mOriginalPicture;
    private String fileExtension;
    private Handler uiHandler;


    public ImageProcessorTask(MatPicture matPicture, OriginalPicture originalPicture,
                              String fileExtenstion, Handler uiHandler){
        this.mMatPicture = matPicture;
        this.mOriginalPicture = originalPicture;
        this.fileExtension = fileExtenstion;
        this.uiHandler = uiHandler;
    }

    @Override
    public void run(){
        GaussianBlur blur = new GaussianBlur(this.mMatPicture.getMatPicture(), this.fileExtension);
        blur.applyGaussianBlur();

        GrayMatMaker matMaker = new GrayMatMaker(blur.getGaussianBlurredMat(), this.fileExtension);
        matMaker.applyGrayScale();

        SobelOperator sobelOperator = new SobelOperator(matMaker.getGrayMat(), this.fileExtension);
        sobelOperator.applySobelOperator();

        HoughCircles circles = new HoughCircles(sobelOperator.getSobelMat(),
                this.mMatPicture.getMatOriginalPicture(), this.fileExtension);
        circles.applyHoughCirclesDetection();

        Message message = uiHandler.obtainMessage(IMAGE_PROCESSING_FINISHED, circles.getHoughCirclesMatInByteArray());
        message.sendToTarget();
    }
}
