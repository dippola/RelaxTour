package com.dippola.relaxtour.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("nickname")
    @Expose
    private String nickname;

    @SerializedName("imageurl")
    @Expose
    private String imageurl;

    @SerializedName("provider")
    @Expose
    private String provider;

    @SerializedName("token")
    @Expose
    private String token;


    public UserModel() {}

    public UserModel(int id, String email, String uid, String nickname, String imageurl, String provider, String token) {
        this.id = id;
        this.email = email;
        this.uid = uid;
        this.nickname = nickname;
        this.imageurl = imageurl;
        this.provider = provider;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
