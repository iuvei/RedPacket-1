package me.jessyan.armscomponent.commonsdk.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AdBannerInfo implements Serializable {

    @SerializedName("adv1")
    private List<BannerEntity> msgAdBanners;
    @SerializedName("adv2")
    private List<BannerEntity> roomAdBanners;

    @SerializedName("notice")
    private List<String> notices;

    public List<BannerEntity> getMsgAdBanners() {
        return msgAdBanners;
    }

    public void setMsgAdBanners(List<BannerEntity> msgAdBanners) {
        this.msgAdBanners = msgAdBanners;
    }

    public List<BannerEntity> getRoomAdBanners() {
        return roomAdBanners;
    }

    public void setRoomAdBanners(List<BannerEntity> roomAdBanners) {
        this.roomAdBanners = roomAdBanners;
    }

    public List<String> getNotices() {
        return notices;
    }

    public void setNotices(List<String> notices) {
        this.notices = notices;
    }
}
