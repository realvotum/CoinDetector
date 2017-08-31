package vot.apps.coindetector.ui.model;

public class OriginalPicture {
    private byte[] image;

    public OriginalPicture(byte[] imageByteArray){
        this.image = imageByteArray;
    }

    public byte[] getImage(){
        return this.image;
    }
    public void setImage(byte[] imageByteArray){
        this.image = imageByteArray;
    }
}
