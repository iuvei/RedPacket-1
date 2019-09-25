package com.haisheng.easeim.mvp.model.entity;

import com.google.gson.annotations.SerializedName;
import com.haisheng.easeim.mvp.utils.RedPacketUtil;
import com.haisheng.easeim.mvp.utils.RedPacketUtil.RedType;

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
    @SerializedName("roomid")
    private int roomid;
    private String redPackType;

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

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public void setRoomType(String redPackType){
        this.redPackType = redPackType;
    }

    public RedType getRoomType(){
        if (redPackType.equals ( "扫雷红包" )){
            return RedType.SAO_LEI;
        }else if(redPackType.equals ( "禁抢红包" )){
            return RedType.GAME_CONTRAL;
        }else if(redPackType.equals ( "牛牛红包" )){
            return RedType.NIU_NIU;
        }else if(redPackType.equals ( "福利红包" )){
            return RedType.FU_LI;
        }else{
            return RedType.NONE;
        }
    }
}
