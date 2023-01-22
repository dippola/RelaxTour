package com.dippola.relaxtour.retrofit.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostsViewWitPages {
    @Keep
    @SerializedName("pages")
    @Expose
    private int pages;
    @Keep
    @SerializedName("posts")
    @Expose
    private List<PostModelView> posts;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<PostModelView> getPosts() {
        return posts;
    }

    public void setPosts(List<PostModelView> posts) {
        this.posts = posts;
    }
}
