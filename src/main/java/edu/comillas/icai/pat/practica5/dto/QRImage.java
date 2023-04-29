package edu.comillas.icai.pat.practica5.dto;

public class QRImage {
    private String data;
    private String size;
    private byte[] image;

    public QRImage(String data, String size, byte[] image) {
        this.data = data;
        this.size = size;
        this.image = image;
    }

    public String getData() {
        return data;
    }
    
    public String getSize() {
        return size;
    }

    public byte[] getImage() {
        return image;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "QRImage [data=" + data + ", size=" + size + ", image=" + image + "]";
    }



}
