package com.dippola.relaxtour.community.translate;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestModel {
    @SerializedName("q")
    List<String> q;
    @SerializedName("target")
    String target;
    @SerializedName("source")
    String source;
    @SerializedName("format")
    String format;

    public RequestModel(List<String> q, String target) {
        this.q = q;
        this.target = target;
        this.source = "";
        this.format = "text";
    }
}
