package com.ooo.main.mvp.model.entity;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResultInfo implements Serializable {

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
    @SerializedName("realname")
    private String realname;
    @SerializedName("rechangemoney")
    private String rechangemoney; //手续费百分比
    @SerializedName("mobile")
    private String mobile; //手机号
    @SerializedName("incode")
    private String incode; //邀请码
    @SerializedName("id")
    private long account;
    private double balance;
    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }
    public String getRealnameScreat() {
        return BlankCardBean.getStarString2 ( realname,1,0 );
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

    public String  getRechangemoneyPercent(){
        return rechangemoney+"%";
    }

    //是否实名认证
    public boolean isCertification(){
        if (TextUtils.isEmpty ( realname )){
            return false;
        }
        return true;
    }

    public String getIncode() {
        return incode;
    }

    public void setIncode(String incode) {
        this.incode = incode;
    }

    public String getMobile() {
        return mobile;
    }
    public String getMobileScreat() {
        return BlankCardBean.getStarString2 ( mobile,3,4 );
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
