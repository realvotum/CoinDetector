package vot.apps.coindetector.ui.model;

public class Picture {
    private byte[] image;

    public Picture(byte[] imageByteArray){
        this.image = imageByteArray;
    }

    public byte[] getImage(){
        return this.image;
    }
    public void setImage(byte[] imageByteArray){
        this.image = imageByteArray;
    }
}
