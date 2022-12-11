package com.dippola.relaxtour.community.main.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddHitModel {
    @SerializedName("willAddHit")
    @Expose
    private boolean willAddHit;

    public boolean isWillAddHit() {
        return willAddHit;
    }

    public void setWillAddHit(boolean willAddHit) {
        this.willAddHit = willAddHit;
    }
}
