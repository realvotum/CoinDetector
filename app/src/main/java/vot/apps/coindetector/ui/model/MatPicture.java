package vot.apps.coindetector.ui.model;


import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class MatPicture {

    private Mat picture;

    public MatPicture(byte[] array){
       this.picture = Imgcodecs.imdecode(new MatOfByte(array), Imgcodecs.CV_IMWRITE_JPEG_QUALITY);
    }

    public MatPicture(byte[] array, String extension){
        switch (extension){
            case "png":
                this.picture = Imgcodecs.imdecode(new MatOfByte(array), Imgcodecs.CV_IMWRITE_PNG_BILEVEL);
                break;
            case "jpg":
                this.picture = Imgcodecs.imdecode(new MatOfByte(array), Imgcodecs.CV_IMWRITE_JPEG_QUALITY);
                break;
            case "jpeg":
                this.picture = Imgcodecs.imdecode(new MatOfByte(array), Imgcodecs.CV_IMWRITE_JPEG_QUALITY);
                break;
            case "webp":
                this.picture = Imgcodecs.imdecode(new MatOfByte(array), Imgcodecs.CV_IMWRITE_WEBP_QUALITY);
            default:
                this.picture = Imgcodecs.imdecode(new MatOfByte(array), Imgcodecs.CV_IMWRITE_JPEG_QUALITY);
                break;
        }
        this.picture = Imgcodecs.imdecode(new MatOfByte(array), Imgcodecs.CV_IMWRITE_JPEG_QUALITY);
    }

    public Mat getMatPicture(){
        return this.picture;
    }

    public void setMatPicture(byte[] array){
        this.picture = Imgcodecs.imdecode(new MatOfByte(array), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
    }

    public void setMatPicture(Mat mat){
        this.picture = mat;
    }
}
