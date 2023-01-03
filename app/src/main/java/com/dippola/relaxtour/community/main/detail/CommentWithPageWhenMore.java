package com.dippola.relaxtour.community.main.detail;

import com.dippola.relaxtour.retrofit.model.PostCommentModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentWithPageWhenMore {
    @SerializedName("comments")
    @Expose
    private List<PostCommentModel> comments;
    @SerializedName("pages")
    @Expose
    private int pages;

    public List<PostCommentModel> getComments() {
        return comments;
    }

    public void setComments(List<PostCommentModel> comments) {
        this.comments = comments;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
