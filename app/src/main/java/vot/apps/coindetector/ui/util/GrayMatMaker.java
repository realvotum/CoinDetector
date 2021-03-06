package vot.apps.coindetector.ui.util;

import android.util.Log;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;



public class GrayMatMaker {
    private Mat originalMat;
    private Mat grayMat;
    private String extension;

    public GrayMatMaker(Mat mat, String extension){
        this.originalMat = mat;
        this.grayMat = new Mat(mat.rows(), mat.cols(), CvType.CV_8UC1);
        this.extension = extension;
    }

    public void applyGrayScale(){
        Imgproc.cvtColor(this.originalMat, this.grayMat, Imgproc.COLOR_BGR2GRAY);
    }

    public Mat getGrayMat(){
        return this.grayMat;
    }

    public byte[] getGrayMatInByteArray(){
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(extension, this.grayMat, matOfByte);
        return matOfByte.toArray();
    }
}
