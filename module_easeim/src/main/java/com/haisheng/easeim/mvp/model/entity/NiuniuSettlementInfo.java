package com.haisheng.easeim.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NiuniuSettlementInfo implements Serializable {

    @SerializedName("nickname")
    private String nickname;
    @SerializedName("avatar")
    private String avatarUrl;
    @SerializedName("countnums")
    private int niuniuNumber;
    @SerializedName("setid")
    private Long redpacketId;
    @SerializedName("villagenums")
    private int bankNumber;
    @SerializedName("notbuynums")
    private int playerNumber;
    @SerializedName("alltype")
    private int status;

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

    public int getNiuniuNumber() {
        return niuniuNumber;
    }

    public void setNiuniuNumber(int niuniuNumber) {
        this.niuniuNumber = niuniuNumber;
    }

    public Long getRedpacketId() {
        return redpacketId;
    }

    public void setRedpacketId(Long redpacketId) {
        this.redpacketId = redpacketId;
    }

    public int getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(int bankNumber) {
        this.bankNumber = bankNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
