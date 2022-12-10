package com.dippola.relaxtour.community.main;

public class ForHitsModel {
    int postid;
    String date;

    public ForHitsModel(int postid, String date){
        this.postid = postid;
        this.date = date;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
