package com.example.bartsprengelmeijer.trackyourprogress.Video;

import android.graphics.Bitmap;

/**
 * Created by bartsprengelmeijer on 05-11-16.
 */
public class VideoFile {
    private String filename;
    private String filepath;
    private Bitmap img;

    public VideoFile(String filename, String filepath, Bitmap img) {
        this.filename = filename;
        this.filepath = filepath;
        this.img = img;
    }

    public String getFilename() {
        return filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
