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
    @SerializedName("roomid")
    private String roomid;
    @SerializedName("nums")
    private int number;
    @SerializedName("boom")
    private String boomNumbers;
    @SerializedName("hxid")
    private String hxid;
    @SerializedName("getedtime")
    private String getedtime;

    @SerializedName("addtime")
    private String creatTime;

    @SerializedName("lasttime")
    private Long countdown;
    @SerializedName("overtime_red")
    private int overtime_red;
    @SerializedName("villagenums")
    private String villagenums;
    @SerializedName("notbuynums")
    private String notbuynums;

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

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    public String getGetedtime() {
        return getedtime;
    }

    public void setGetedtime(String getedtime) {
        this.getedtime = getedtime;
    }

    public String getVillagenums() {
        return villagenums;
    }

    public void setVillagenums(String villagenums) {
        this.villagenums = villagenums;
    }

    public String getNotbuynums() {
        return notbuynums;
    }

    public void setNotbuynums(String notbuynums) {
        this.notbuynums = notbuynums;
    }

    public int getOvertime_red() {
        return overtime_red;
    }

    public void setOvertime_red(int overtime_red) {
        this.overtime_red = overtime_red;
    }

    public boolean isOverTime(){
        //是否过期
        if (overtime_red==0){
            //未过期
            return false;
        }
        //已过期
        return true;

    }
}
