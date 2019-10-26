package com.haisheng.easeim.mvp.model.entity;

import com.google.gson.annotations.SerializedName;
import com.haisheng.easeim.mvp.model.db.IMDatabase;
import com.hyphenate.chat.EMChatRoom;
import com.jess.arms.di.scope.ActivityScope;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import me.jessyan.armscomponent.commonsdk.entity.UserInfo;


@Table(database = IMDatabase.class)
public class ChatRoomBean extends BaseModel implements Serializable {

    @PrimaryKey
    @SerializedName("id")
    private Long id;
    @Column
    @SerializedName("hxgroupid")
    private String hxId;
    @Column
    @SerializedName("roomname")
    private String name;
    @Column
    @SerializedName("describe")
    private String describe;
    @Column
    @SerializedName("surl")
    private String imgUrl;

    //"type": "0",//房间类型（0扫雷区1禁抢区2牛牛不翻倍3牛牛翻倍4福利区）
    @SerializedName("type")
    private int type;

    @SerializedName("isbanned")
    private int bannedStatus;

    @SerializedName("min")
    private double minMoney;
    @SerializedName("max")
    private double maxMoney;
    @SerializedName("minrednums")
    private int minRedpacketNumber;
    @SerializedName("maxrednums")
    private int maxRedpacketNumber;
    @SerializedName("rednums")
    private int redpacketNumber;

    @SerializedName("welfminmoney")
    private double welfareMinMoney;
    @SerializedName("welfmaxmoney")
    private double welfareMaxMoney;
    @SerializedName("welfminnums")
    private int welfareMinRedpacketNumber;
    @SerializedName("welfmaxnums")
    private int welfareMaxRedpacketNumber;

    @SerializedName("affiche")
    private String  affiche;
    @SerializedName("notice")
    private String notice;
    @SerializedName("regulation")
    private String groupRules;
    @SerializedName("regulationurl")
    private String groupRulesImgUrl;
    @SerializedName("playing")
    private String gameRules;
    @SerializedName("playingurl")
    private String gameRulesImgUrl;
    @SerializedName("compensate")
    private String compensate;

    @SerializedName("basenums")
    private int userNumber;
    @SerializedName("list")
    private List<UserInfo> userInfos;
    @SerializedName("ban7")
    private Map<String,String> ban7;

    public int getBannedStatus() {
        return bannedStatus;
    }

    public void setBannedStatus(int bannedStatus) {
        this.bannedStatus = bannedStatus;
    }

    public double getWelfareMinMoney() {
        return welfareMinMoney;
    }

    public void setWelfareMinMoney(double welfareMinMoney) {
        this.welfareMinMoney = welfareMinMoney;
    }

    public double getWelfareMaxMoney() {
        return welfareMaxMoney;
    }

    public void setWelfareMaxMoney(double welfareMaxMoney) {
        this.welfareMaxMoney = welfareMaxMoney;
    }

    public int getWelfareMinRedpacketNumber() {
        return welfareMinRedpacketNumber;
    }

    public void setWelfareMinRedpacketNumber(int welfareMinRedpacketNumber) {
        this.welfareMinRedpacketNumber = welfareMinRedpacketNumber;
    }

    public int getWelfareMaxRedpacketNumber() {
        return welfareMaxRedpacketNumber;
    }

    public void setWelfareMaxRedpacketNumber(int welfareMaxRedpacketNumber) {
        this.welfareMaxRedpacketNumber = welfareMaxRedpacketNumber;
    }

    public double getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(double minMoney) {
        this.minMoney = minMoney;
    }

    public double getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(double maxMoney) {
        this.maxMoney = maxMoney;
    }

    public int getMinRedpacketNumber() {
        return minRedpacketNumber;
    }

    public void setMinRedpacketNumber(int minRedpacketNumber) {
        this.minRedpacketNumber = minRedpacketNumber;
    }

    public int getMaxRedpacketNumber() {
        return maxRedpacketNumber;
    }

    public void setMaxRedpacketNumber(int maxRedpacketNumber) {
        this.maxRedpacketNumber = maxRedpacketNumber;
    }

    public int getRedpacketNumber() {
        return redpacketNumber;
    }

    public void setRedpacketNumber(int redpacketNumber) {
        this.redpacketNumber = redpacketNumber;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAffiche() {
        return affiche;
    }

    public void setAffiche(String affiche) {
        this.affiche = affiche;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getGroupRules() {
        return groupRules;
    }

    public void setGroupRules(String groupRules) {
        this.groupRules = groupRules;
    }

    public String getGroupRulesImgUrl() {
        return groupRulesImgUrl;
    }

    public void setGroupRulesImgUrl(String groupRulesImgUrl) {
        this.groupRulesImgUrl = groupRulesImgUrl;
    }

    public String getGameRules() {
        return gameRules;
    }

    public void setGameRules(String gameRules) {
        this.gameRules = gameRules;
    }

    public String getGameRulesImgUrl() {
        return gameRulesImgUrl;
    }

    public void setGameRulesImgUrl(String gameRulesImgUrl) {
        this.gameRulesImgUrl = gameRulesImgUrl;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHxId() {
        return hxId;
    }

    public void setHxId(String hxId) {
        this.hxId = hxId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCompensate() {
        return compensate;
    }

    public void setCompensate(String compensate) {
        this.compensate = compensate;
    }

    public Map<String, String>  getBan7() {
        return ban7;
    }

    public String getBanFromBan7(int key){
        return ban7.get ( key+"" );
    }

    public void setBan7(Map ban7) {
        this.ban7 = ban7;
    }
}
