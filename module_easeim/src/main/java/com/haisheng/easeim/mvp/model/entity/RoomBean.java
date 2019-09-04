package com.haisheng.easeim.mvp.model.entity;

import com.google.gson.annotations.SerializedName;
import com.haisheng.easeim.mvp.model.db.IMDatabase;
import com.hyphenate.chat.EMChatRoom;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;


@Table(database = IMDatabase.class)
public class RoomBean extends BaseModel implements Serializable {

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

    private EMChatRoom emChatRoom;

    public EMChatRoom getEmChatRoom() {
        return emChatRoom;
    }

    public void setEmChatRoom(EMChatRoom emChatRoom) {
        this.emChatRoom = emChatRoom;
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
}
