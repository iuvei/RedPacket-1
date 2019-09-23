package com.haisheng.easeim.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CheckRedpacketInfo implements Serializable {

    @SerializedName("code")
    private int status;
    @SerializedName("mess")
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
