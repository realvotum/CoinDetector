package vot.apps.coindetector.ui.util;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Lap on 2017-08-29.
 */

public class OtsuBinarizator {
    private Mat mMat;
    private Mat otsuMat;
    private String extension;

    public OtsuBinarizator(Mat mat, String extension){
        this.mMat = mat;
        this.otsuMat = new Mat(mMat.rows(), mMat.cols(), mMat.type());
        this.extension = extension;
    }

    public void applyOtsuBinarization(){
        Imgproc.threshold(this.mMat, this.otsuMat, 0, 255, Imgproc.THRESH_OTSU + Imgproc.THRESH_BINARY);
    }

    public Mat getOtsuMat(){
        return this.otsuMat;
    }

    public byte[] getOtsuMatInByteArray(){
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(extension, this.otsuMat, matOfByte);
        return matOfByte.toArray();
    }
}
