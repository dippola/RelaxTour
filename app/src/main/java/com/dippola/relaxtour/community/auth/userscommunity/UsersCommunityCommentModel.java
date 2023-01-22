package com.dippola.relaxtour.community.auth.userscommunity;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersCommunityCommentModel {
    @Keep
    @SerializedName("parent_id")
    @Expose
    private int parent_id;
    @Keep
    @SerializedName("towho")
    @Expose
    private String towho;
    @Keep
    @SerializedName("body")
    @Expose
    private String body;
    @Keep
    @SerializedName("date")
    @Expose
    private String date;

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getTowho() {
        return towho;
    }

    public void setTowho(String towho) {
        this.towho = towho;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
