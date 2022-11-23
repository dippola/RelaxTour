package com.dippola.relaxtour.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainModel {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("parent_user")
    @Expose
    private int parent_user;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("body")
    @Expose
    private String body;

    @SerializedName("imageurl")
    @Expose
    private String imageurl;

    @SerializedName("count")
    @Expose
    private String count;

    @SerializedName("like")
    @Expose
    private String like;

    @SerializedName("list")
    @Expose
    private String list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserModel() {
        return parent_user;
    }

    public void setUserModel(int userModel) {
        this.parent_user = userModel;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }
}
