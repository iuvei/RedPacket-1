package com.haisheng.easeim.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GunControlSettlementInfo implements Serializable {

    @SerializedName("nickname")
    private String nickname;
    @SerializedName("avatar")
    private String avatarUrl;
    @SerializedName("gold")
    private double money;
    @SerializedName("nums")
    private int number;
    @SerializedName("setname")
    private String gameRules;
    @SerializedName("boom")
    private String mineNumber;
    @SerializedName("boomnums")
    private int bombNumber;
    @SerializedName("odds")
    private double odds;
    @SerializedName("pricemoney")
    private double awardMoney;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getGameRules() {
        return gameRules;
    }

    public void setGameRules(String gameRules) {
        this.gameRules = gameRules;
    }

    public String getMineNumber() {
        return mineNumber;
    }

    public void setMineNumber(String mineNumber) {
        this.mineNumber = mineNumber;
    }

    public int getBombNumber() {
        return bombNumber;
    }

    public void setBombNumber(int bombNumber) {
        this.bombNumber = bombNumber;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    public double getAwardMoney() {
        return awardMoney;
    }

    public void setAwardMoney(double awardMoney) {
        this.awardMoney = awardMoney;
    }
}
