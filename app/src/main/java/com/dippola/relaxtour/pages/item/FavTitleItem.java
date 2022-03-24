package com.dippola.relaxtour.pages.item;

public class FavTitleItem {
    String title;
    int isopen;

    public FavTitleItem() {}

    public FavTitleItem(String title, int isopen) {
        this.title = title;
        this.isopen = isopen;
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
}
