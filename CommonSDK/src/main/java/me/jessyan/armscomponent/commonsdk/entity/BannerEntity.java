package me.jessyan.armscomponent.commonsdk.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BannerEntity implements Serializable {

    @SerializedName("thumb")
    private String imageUrl;
    @SerializedName("link")
    private String link;
    private String tip;
    private String title;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getTitle() {
        if (link.equals ( "0" )){
            return "扫雷区";
        }else if (link.equals ( "1" )){
            return "禁抢区";
        }else if (link.equals ( "2" )){
            return "牛牛不翻倍";
        }else if (link.equals ( "3" )){
            return "牛牛翻倍";
        }else if (link.equals ( "4" )){
            return "福利区";
        }
        return "";
    }

}