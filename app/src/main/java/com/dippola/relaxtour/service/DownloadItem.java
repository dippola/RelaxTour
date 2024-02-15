package com.dippola.relaxtour.service;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

public class DownloadItem {
    String tid;
    ProgressBar loading;
    ImageView img;
    ImageView download;
    SeekBar seekBar;

    public DownloadItem(String tid, ProgressBar loading, ImageView img, ImageView download, SeekBar seekBar) {
        this.tid = tid;
        this.loading = loading;
        this.img = img;
        this.download = download;
        this.seekBar = seekBar;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public ProgressBar getLoading() {
        return loading;
    }

    public void setLoading(ProgressBar loading) {
        this.loading = loading;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public ImageView getDownload() {
        return download;
    }

    public void setDownload(ImageView download) {
        this.download = download;
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }
}
