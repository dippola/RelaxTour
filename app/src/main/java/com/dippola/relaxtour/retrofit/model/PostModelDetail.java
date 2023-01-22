package com.dippola.relaxtour.retrofit.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostModelDetail {
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
    @SerializedName("user_url")
    @Expose
    private String user_url;
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
    @SerializedName("body")
    @Expose
    private String body;
    @Keep
    @SerializedName("imageurl")
    @Expose
    private String imageurl;
    @Keep
    @SerializedName("view")
    @Expose
    private int view;
    @Keep
    @SerializedName("like")
    @Expose
    private int like;
    @Keep
    @SerializedName("list")
    @Expose
    private String list;
    @Keep
    @SerializedName("commentcount")
    @Expose
    private int commentcount;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }
}
