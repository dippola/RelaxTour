package com.dippola.relaxtour.pages.item;

public class FavTitleItem {
    String title;
    int isopen;
    int isedit;

    public FavTitleItem() {}

    public FavTitleItem(String title, int isopen, int isedit) {
        this.title = title;
        this.isopen = isopen;
        this.isedit = isedit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsopen() {
        return isopen;
    }

    public void setIsopen(int isopen) {
        this.isopen = isopen;
    }

    public int getIsedit() {
        return isedit;
    }

    public void setIsedit(int isedit) {
        this.isedit = isedit;
    }
}
