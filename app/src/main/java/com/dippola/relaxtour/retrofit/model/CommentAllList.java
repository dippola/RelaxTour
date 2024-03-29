package com.dippola.relaxtour.retrofit.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentAllList {

    @Keep
    @SerializedName("comments")
    @Expose
    private List<PostCommentModel> comments;

    public List<PostCommentModel> getComments() {
        return comments;
    }

    public void setComments(List<PostCommentModel> comments) {
        this.comments = comments;
    }
}
