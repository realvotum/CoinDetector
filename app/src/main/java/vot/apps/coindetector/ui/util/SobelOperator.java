package vot.apps.coindetector.ui.util;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Lap on 2017-08-26.
 */

public class SobelOperator {
    private Mat grayMat;
    private Mat sobelMat;
    private String extension;

    public SobelOperator(Mat grayMat, String extension){
        this.grayMat = grayMat;
        this.extension = extension;
    }

    public void applySobelOperator(){
        Mat xFirstDerivative =new Mat();
        Mat yFirstDerivative =new Mat();
        int ddepth = CvType.CV_16S;
        Imgproc.Sobel(this.grayMat, xFirstDerivative,ddepth , 1,0);
        Imgproc.Sobel(this.grayMat, yFirstDerivative,ddepth , 0,1);
        Mat absXD=new Mat();
        Mat absYD=new Mat();
        Core.convertScaleAbs(xFirstDerivative, absXD);
        Core.convertScaleAbs(yFirstDerivative, absYD);
        sobelMat = new Mat();
        Core.addWeighted(absXD, 0.5, absYD, 0.5, 0, this.sobelMat);
    }

    public Mat getSobelMat(){
        return this.sobelMat;
    }

    public byte[] getSobelMatInByteArray(){
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(extension, this.sobelMat, matOfByte);
        return matOfByte.toArray();
    }
}
