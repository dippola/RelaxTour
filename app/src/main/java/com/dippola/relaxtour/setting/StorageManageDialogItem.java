package com.dippola.relaxtour.setting;

public class StorageManageDialogItem {
    String name;
    String pnp;
    int page;
    int position;

    public StorageManageDialogItem(String name, String pnp, int page, int position) {
        this.name = name;
        this.pnp = pnp;
        this.page = page;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPnp() {
        return pnp;
    }

    public void setPnp(String pnp) {
        this.pnp = pnp;
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
