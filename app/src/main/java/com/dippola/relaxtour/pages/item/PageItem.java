package com.dippola.relaxtour.pages.item;

public class PageItem {

    int page;
    int position;
    String pnp;
    byte[] imgdefault;
    byte[] img;
    int seek;
    int isplay;
    int time;
    String name;
    int ispro;

    public PageItem() {

    }

    public PageItem(int page, int position, String pnp, byte[] imgdefault, byte[] img, int seek, int isplay, int time, String name, int ispro) {
        this.page = page;
        this.position = position;
        this.pnp = pnp;
        this.imgdefault = imgdefault;
        this.img = img;
        this.seek = seek;
        this.isplay = isplay;
        this.time = time;
        this.name = name;
        this.ispro = ispro;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIspro() {
        return ispro;
    }

    public void setIspro(int ispro) {
        this.ispro = ispro;
    }
}
