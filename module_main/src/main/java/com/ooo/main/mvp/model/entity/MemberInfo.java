package com.ooo.main.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MemberInfo implements Serializable {

    @SerializedName("id")
    private Long id;

    @SerializedName("mobile")
    private String phoneNumber;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("avatar")
    private String avatarUrl;

    @SerializedName("gender")
    private int sex;
    @SerializedName("realname")
    private String realname;

    @SerializedName("rechangemoney")
    private String rechangemoney;
    public static final int MALE = 1;
    public static final int FAMALE = 0;

    @SerializedName("incode")
    private String inviteCode;

    @SerializedName("incodeurl")
    private String inviteCodeUrl;

    @SerializedName("goldmoney")
    private double balance;

    @SerializedName("agencystatus")
    private int agencyStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteCodeUrl() {
        return inviteCodeUrl;
    }

    public void setInviteCodeUrl(String inviteCodeUrl) {
        this.inviteCodeUrl = inviteCodeUrl;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getAgencyStatus() {
        return agencyStatus;
    }

    public void setAgencyStatus(int agencyStatus) {
        this.agencyStatus = agencyStatus;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRechangemoney() {
        return rechangemoney;
    }

    public void setRechangemoney(String rechangemoney) {
        this.rechangemoney = rechangemoney;
    }
}
