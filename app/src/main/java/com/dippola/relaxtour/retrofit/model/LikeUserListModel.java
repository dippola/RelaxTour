package com.dippola.relaxtour.retrofit.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeUserListModel {
    @Keep
    @SerializedName("parent_id")
    @Expose
    private int parent_id;
    @Keep
    @SerializedName("user_ids")
    @Expose
    private int user_ids;

    public LikeUserListModel(int parent_id, int user_ids) {
        this.parent_id = parent_id;
        this.user_ids = user_ids;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(int user_ids) {
        this.user_ids = user_ids;
    }
}
