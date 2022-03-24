package com.dippola.relaxtour.maintablayout;

public class MainTabItem {

    int img;
    String title;
    Boolean isOpen;

    public MainTabItem(int img, String title, boolean isOpen) {
        this.img = img;
        this.title = title;
        this.isOpen = isOpen;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}
