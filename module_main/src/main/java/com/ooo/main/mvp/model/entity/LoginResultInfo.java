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
}
