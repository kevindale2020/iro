package com.abc123.iro;

public class Data {
    public String photo;
    public String filename;

    public Data(String photo, String filename) {
        this.photo = photo;
        this.filename = filename;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
