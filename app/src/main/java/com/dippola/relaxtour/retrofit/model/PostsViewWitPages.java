package com.dippola.relaxtour.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostsViewWitPages {
    @SerializedName("pages")
    @Expose
    private int pages;
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
