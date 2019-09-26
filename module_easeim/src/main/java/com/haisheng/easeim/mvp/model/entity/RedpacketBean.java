package com.haisheng.easeim.mvp.model.entity;

import com.google.gson.annotations.SerializedName;
import com.haisheng.easeim.app.IMConstants;
import com.haisheng.easeim.mvp.utils.RedPacketUtil;

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
    private String redPackType;

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

    public void setRoomType(String redPackType){
        this.redPackType = redPackType;
    }

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    public RedPacketUtil.RedType getRoomType(){
        if (redPackType.equals ( "扫雷红包" )){
            return RedPacketUtil.RedType.SAO_LEI;
        }else if(redPackType.equals ( "禁抢红包" )){
            return RedPacketUtil.RedType.GAME_CONTRAL;
        }else if(redPackType.equals ( "牛牛红包" )){
            return RedPacketUtil.RedType.NIU_NIU;
        }else if(redPackType.equals ( "福利红包" )){
            return RedPacketUtil.RedType.FU_LI;
        }else{
            return RedPacketUtil.RedType.NONE;
        }
    }
}
