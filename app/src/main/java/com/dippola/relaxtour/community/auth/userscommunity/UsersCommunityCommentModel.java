package com.dippola.relaxtour.community.auth.userscommunity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersCommunityCommentModel {
    @SerializedName("parent_id")
    @Expose
    private int parent_id;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("date")
    @Expose
    private String date;

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
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
