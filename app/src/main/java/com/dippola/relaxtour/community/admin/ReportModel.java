package com.dippola.relaxtour.community.admin;

import androidx.annotation.Keep;

public class ReportModel {

    @Keep
    int choice;
    @Keep
    int commentid;
    @Keep
    String date;
    @Keep
    int decUser;
    @Keep
    String edit;
    @Keep
    String from;
    @Keep
    int postid;

    public ReportModel() {

    }

//    public ReportModel(int choice, int commentid, String date, int decUser, String edit, String from, int postid) {
//        this.choice = choice;
//        this.commentid = commentid;
//        this.date = date;
//        this.decUser = decUser;
//        this.edit = edit;
//        this.from = from;
//        this.postid = postid;
//    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDecUser() {
        return decUser;
    }

    public void setDecUser(int decUser) {
        this.decUser = decUser;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }
}
