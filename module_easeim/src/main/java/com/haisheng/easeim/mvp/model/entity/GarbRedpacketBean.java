package com.haisheng.easeim.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GarbRedpacketBean implements Serializable {

    @SerializedName("nickname")
    private String nickname;
    @SerializedName("avatar")
    private String avatarUrl;
    @SerializedName("money")
    private String money;
    @SerializedName("addtime")
    private String time;
    @SerializedName("isboom")
    private int bombStatus;
    @SerializedName("ismax")
    private int bestStatus;

    @SerializedName("countnums")
    private Integer niuniuNumber;

    @SerializedName("banker")
    private int bankerStatus;
    @SerializedName("uid")
    private String id;

    public Integer getNiuniuNumber() {
        return niuniuNumber;
    }

    public void setNiuniuNumber(Integer niuniuNumber) {
        this.niuniuNumber = niuniuNumber;
    }

    public int getBankerStatus() {
        return bankerStatus;
    }

    public void setBankerStatus(int bankerStatus) {
        this.bankerStatus = bankerStatus;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getBombStatus() {

        return bombStatus;
    }

    public void setBombStatus(int bombStatus) {
        this.bombStatus = bombStatus;
    }

    public int getBestStatus() {
        return bestStatus;
    }

    public void setBestStatus(int bestStatus) {
        this.bestStatus = bestStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
