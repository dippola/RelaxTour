package com.dippola.relaxtour.community.auth.userscommunity;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserCommentWithPageModel {
    @Keep
    @SerializedName("pages")
    @Expose
    private int pages;
    @Keep
    @SerializedName("result")
    @Expose
    private List<UsersCommunityCommentModel> list;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<UsersCommunityCommentModel> getList() {
        return list;
    }

    public void setList(List<UsersCommunityCommentModel> list) {
        this.list = list;
    }
}
