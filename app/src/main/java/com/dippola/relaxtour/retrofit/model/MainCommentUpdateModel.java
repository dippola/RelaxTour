package com.dippola.relaxtour.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainCommentUpdateModel {
    @SerializedName("body")
    @Expose
    private String body;

    @SerializedName("to")
    @Expose
    private String to;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
