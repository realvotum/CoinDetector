package vot.apps.coindetector.ui.util;

import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Lap on 2017-08-26.
 */

public class GaussianBlur {
    private Mat src;
    private String extension;

    public GaussianBlur(Mat src, String extension){
        this.src = src;
        this.extension = extension;
    }

    public void applyGaussianBlur(){
        Size size = new Size(11,11);
        Imgproc.GaussianBlur(src, src, size, 11, 11);
    }

    public Mat getGaussianBlurredMat(){

        return this.src;
    }

    public byte[] getGaussianBlurredMatInByteArray(){
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(extension, this.src, matOfByte);
        return matOfByte.toArray();
    }
}
