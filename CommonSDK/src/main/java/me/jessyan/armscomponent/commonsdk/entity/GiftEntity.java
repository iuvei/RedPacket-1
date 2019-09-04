package me.jessyan.armscomponent.commonsdk.entity;

import java.io.Serializable;

public class GiftEntity implements Serializable {

    private int id;
    private String name;
    private String iconUrl;
    private int needCoin;
    private int number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getNeedCoin() {
        return needCoin;
    }

    public void setNeedCoin(int needCoin) {
        this.needCoin = needCoin;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
