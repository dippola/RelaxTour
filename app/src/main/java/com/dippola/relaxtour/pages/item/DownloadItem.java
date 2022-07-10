package com.dippola.relaxtour.pages.item;

public class DownloadItem {
    int page, position;
    public DownloadItem(int page, int position) {
        this.page = page;
        this.position = position;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
