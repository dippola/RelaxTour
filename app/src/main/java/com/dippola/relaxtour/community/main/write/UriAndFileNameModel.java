package com.dippola.relaxtour.community.main.write;

import android.net.Uri;

public class UriAndFileNameModel {
    Uri uri;
    String name;

    public UriAndFileNameModel() {

    }

    public UriAndFileNameModel(Uri uri, String name) {
        this.uri = uri;
        this.name = name;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
