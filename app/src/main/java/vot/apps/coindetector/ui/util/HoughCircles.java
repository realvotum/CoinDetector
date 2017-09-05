package vot.apps.coindetector.ui.util;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Lap on 2017-08-26.
 */

public class HoughCircles {
    private Mat sobelMat;
    private Mat originalImageMat;
    private String extension;

    public HoughCircles(Mat sobelMat, Mat originalImage, String extension){
        this.sobelMat = sobelMat;
        this.originalImageMat = originalImage;
        this.extension = extension;
    }

    public void applyHoughCirclesDetection(){
        int thickness=10;

        Mat houghCirclesMat = new Mat(this.sobelMat.rows(), this.sobelMat.cols(), this.sobelMat.type());

        Imgproc.HoughCircles(this.sobelMat, houghCirclesMat, Imgproc.CV_HOUGH_GRADIENT, 1,
                this.sobelMat.rows()/8, 100,90,0,1000);

        for (int i = 0; i < houghCirclesMat.cols(); i++)
        {
            double[] circle = houghCirclesMat.get(0, i);
            double centerX = circle[0],
                    centerY = circle[1],
                    radius = circle[2];
            org.opencv.core.Point center = new org.opencv.core.Point(centerX,
                    centerY);
            Imgproc.circle(this.originalImageMat, center, 2, new
                    Scalar(0,0,255), thickness);
            Imgproc.circle(this.originalImageMat, center, (int) radius, new
                    Scalar(0,0,255), thickness);

        }
    }

    public Mat getHoughCircleslMat(){
        return this.originalImageMat;
    }

    public byte[] getHoughCirclesMatInByteArray(){
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(extension, this.originalImageMat, matOfByte);
        return matOfByte.toArray();
    }
}
