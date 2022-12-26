package com.dippola.relaxtour.community.bottomsheet_intent;

import android.net.Uri;

public class UriAndFromModel {
    Uri url;
    String from;

    public UriAndFromModel(Uri urllist, String from) {
        this.url = urllist;
        this.from = from;
    }

    public Uri getUrl() {
        return url;
    }

    public void setUrl(Uri url) {
        this.url = url;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
