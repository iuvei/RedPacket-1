package com.haisheng.easeim.mvp.model.entity;

import com.google.gson.annotations.SerializedName;
import com.haisheng.easeim.app.IMConstants;

import java.io.Serializable;
import java.util.List;

public class RedpacketBean implements Serializable {

    @SerializedName("setid")
    private Long id;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("avatar")
    private String avatarUrl;
    @SerializedName("gold")
    private double money;
    @SerializedName("isforb")
    private int type;
    @SerializedName("nums")
    private int number;
    @SerializedName("boom")
    private String boomNumbers;

    @SerializedName("addtime")
    private String creatTime;

    @SerializedName("lasttime")
    private Long countdown;

    private int welfareStatus;

    // 0 可领取 1 已截止
    @SerializedName("status")
    private int status;

    @SerializedName("list")
    private List<GarbRedpacketBean> garbRedpackets;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getCountdown() {
        return countdown;
    }

    public void setCountdown(Long countdown) {
        this.countdown = countdown;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public int getWelfareStatus() {
        return type == IMConstants.MSG_TYPE_WELFARE_REDPACKET ? 1: 0;
    }


    public List<GarbRedpacketBean> getGarbRedpackets() {
        return garbRedpackets;
    }

    public void setGarbRedpackets(List<GarbRedpacketBean> garbRedpackets) {
        this.garbRedpackets = garbRedpackets;
    }

    public String getBoomNumbers() {
        return boomNumbers;
    }

    public void setBoomNumbers(String boomNumbers) {
        this.boomNumbers = boomNumbers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
