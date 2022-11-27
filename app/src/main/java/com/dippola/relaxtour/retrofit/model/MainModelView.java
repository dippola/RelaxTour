package com.dippola.relaxtour.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainModelView {
    @SerializedName("parent_id")
    @Expose
    private int parent_id;

    @SerializedName("parent_user")
    @Expose
    private int parent_user;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("imageurl")
    @Expose
    private String imageurl;
    @SerializedName("commentcount")
    @Expose
    private int commentcount;
    @SerializedName("like")
    @Expose
    private int like;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
