package com.ooo.main.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class LoginResultInfo {

    @SerializedName("token")
    private String token;
    @SerializedName("hxid")
    private String hxUsername;
    @SerializedName("hxpwd")
    private String hxPassword;

    @SerializedName("nickname")
    private String nickname;
    @SerializedName("avatar")
    private String avatarUrl;
    @SerializedName("gender")
    private int gender;

    private long account;
    private double balance;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHxUsername() {
        return hxUsername;
    }

    public void setHxUsername(String hxUsername) {
        this.hxUsername = hxUsername;
    }

    public String getHxPassword() {
        return hxPassword;
    }

    public void setHxPassword(String hxPassword) {
        this.hxPassword = hxPassword;
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

    public int getGender() {
        return gender;
    }
    public String getGenderMean() {
        return gender==0?"女":"男";
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getAccount() {
        return account;
    }

    public void setAccount(long account) {
        this.account = account;
    }

    public String getBalanceValue() {
        return String.format("%.2f",balance);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
