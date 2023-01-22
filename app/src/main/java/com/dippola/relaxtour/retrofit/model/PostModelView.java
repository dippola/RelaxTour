package com.dippola.relaxtour.retrofit.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostModelView {
    @Keep
    @SerializedName("parent_id")
    @Expose
    private int parent_id;

    @Keep
    @SerializedName("parent_user")
    @Expose
    private int parent_user;
    @Keep
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @Keep
    @SerializedName("user_image")
    @Expose
    private String user_image;
    @Keep
    @SerializedName("category")
    @Expose
    private String category;
    @Keep
    @SerializedName("date")
    @Expose
    private String date;

    @Keep
    @SerializedName("title")
    @Expose
    private String title;
    @Keep
    @SerializedName("imageurl")
    @Expose
    private String imageurl;
    @Keep
    @SerializedName("imageurlcount")
    @Expose
    private int imageurlcount;
    @Keep
    @SerializedName("commentcount")
    @Expose
    private int commentcount;
    @Keep
    @SerializedName("view")
    @Expose
    private int view;
    @Keep
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public int getImageurlcount() {
        return imageurlcount;
    }

    public void setImageurlcount(int imageurlcount) {
        this.imageurlcount = imageurlcount;
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

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }
}
