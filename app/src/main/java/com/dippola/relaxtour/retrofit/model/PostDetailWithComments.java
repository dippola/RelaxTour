package com.dippola.relaxtour.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostDetailWithComments {
    @SerializedName("post")
    @Expose
    private PostModelDetail post;
    @SerializedName("comments")
    @Expose
    private List<PostCommentModel> comments;

    public PostModelDetail getPost() {
        return post;
    }

    public void setPost(PostModelDetail post) {
        this.post = post;
    }

    public List<PostCommentModel> getComments() {
        return comments;
    }

    public void setComments(List<PostCommentModel> comments) {
        this.comments = comments;
    }
}
