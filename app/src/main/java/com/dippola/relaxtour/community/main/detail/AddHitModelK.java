package com.dippola.relaxtour.community.main.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddHitModelK {
    @SerializedName("addHitModel")
    @Expose
    private AddHitModel addHitModel;
    @SerializedName("key")
    @Expose
    private String key;

    public AddHitModel getAddHitModel() {
        return addHitModel;
    }

    public void setAddHitModel(AddHitModel addHitModel) {
        this.addHitModel = addHitModel;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
