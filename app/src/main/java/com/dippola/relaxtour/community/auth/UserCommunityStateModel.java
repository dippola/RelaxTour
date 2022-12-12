package com.dippola.relaxtour.community.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCommunityStateModel {
    @SerializedName("posts")
    @Expose
    private int posts;
    @SerializedName("comments")
    @Expose
    private int comments;
    @SerializedName("likes")
    @Expose
    private int likes;

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
