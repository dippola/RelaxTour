package com.dippola.relaxtour.pages.item;

public class FavListItem {
    int page;
    int position;
    String pnp;
    byte[] imgdefault;
    byte[] img;
    int seek;
    int isplay;
    String favtitlename;

    public FavListItem() {}

    public FavListItem(int page, int position, String pnp, byte[] imgdefault, byte[] img, int seek, int isplay, String favtitlename) {
        this.page = page;
        this.position = position;
        this.pnp = pnp;
        this.imgdefault = imgdefault;
        this.img = img;
        this.seek = seek;
        this.isplay = isplay;
        this.favtitlename = favtitlename;
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

    public String getPnp() {
        return pnp;
    }

    public void setPnp(String pnp) {
        this.pnp = pnp;
    }

    public byte[] getImgdefault() {
        return imgdefault;
    }

    public void setImgdefault(byte[] imgdefault) {
        this.imgdefault = imgdefault;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public int getSeek() {
        return seek;
    }

    public void setSeek(int seek) {
        this.seek = seek;
    }

    public int getIsplay() {
        return isplay;
    }

    public void setIsplay(int isplay) {
        this.isplay = isplay;
    }

    public String getFavtitlename() {
        return favtitlename;
    }

    public void setFavtitlename(String favtitlename) {
        this.favtitlename = favtitlename;
    }
}
