package com.dippola.relaxtour.community.main.notification;

public class NotificationItem {
    String title;
    String body;
    String date;
    int postid;

    public NotificationItem(String title, String body, String date, int postid) {
        this.title = title;
        this.body = body;
        this.date = date;
        this.postid = postid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }
}
