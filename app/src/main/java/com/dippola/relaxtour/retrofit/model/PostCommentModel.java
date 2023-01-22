package com.dippola.relaxtour.retrofit.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostCommentModel {
    @Keep
    @SerializedName("id")
    @Expose
    private int id;

    @Keep
    @SerializedName("date")
    @Expose
    private String date;

    @Keep
    @SerializedName("parent_id")
    @Expose
    private int parent_id;

    @Keep
    @SerializedName("parent_user")
    @Expose
    private int parent_user;

    @Keep
    @SerializedName("body")
    @Expose
    private String body;

    @Keep
    @SerializedName("nickname")
    @Expose
    private String nickname;

    @Keep
    @SerializedName("user_url")
    @Expose
    private String user_url;

    @Keep
    @SerializedName("to_id")
    @Expose
    private int to_id;

    @Keep
    @SerializedName("to_nickname")
    @Expose
    private String to_nickname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getParent_user() {
        return parent_user;
    }

    public void setParent_user(int parent_user) {
        this.parent_user = parent_user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUser_url() {
        return user_url;
    }

    public void setUser_url(String user_url) {
        this.user_url = user_url;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public String getTo_nickname() {
        return to_nickname;
    }

    public void setTo_nickname(String to_nickname) {
        this.to_nickname = to_nickname;
    }
}
