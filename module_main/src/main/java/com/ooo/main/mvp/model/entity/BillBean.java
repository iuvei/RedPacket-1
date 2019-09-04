package com.ooo.main.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BillBean implements Serializable {

    private Long id;
    @SerializedName("details")
    private String describe;
    @SerializedName("gold")
    private double money;
    @SerializedName("setid")
    private Long redPacketId;
    @SerializedName("roomid")
    private Long roomId;
    @SerializedName("addtime")
    private String time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Long getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(Long redPacketId) {
        this.redPacketId = redPacketId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
