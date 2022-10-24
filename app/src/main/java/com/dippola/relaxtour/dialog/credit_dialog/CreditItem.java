package com.dippola.relaxtour.dialog.credit_dialog;

public class CreditItem {
    String track;
    String url;

    public CreditItem(){}

    public CreditItem(String track, String url) {
        this.track = track;
        this.url = url;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
