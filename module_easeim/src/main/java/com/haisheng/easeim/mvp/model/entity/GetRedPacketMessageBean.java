package com.haisheng.easeim.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * creat at 2019/10/16
 * description
 * 机器人抢包发送的消息实体类
 */
public class GetRedPacketMessageBean implements Parcelable {


    /**
     * hxid : 97002064359d
     * nickname : 有有有由于
     * redid : 167615
     * roomid : 2
     */

    private String hxid;
    private String nickname;
    private String redid;
    private String roomid;

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRedid() {
        return redid;
    }

    public void setRedid(String redid) {
        this.redid = redid;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString ( this.hxid );
        dest.writeString ( this.nickname );
        dest.writeString ( this.redid );
        dest.writeString ( this.roomid );
    }

    public GetRedPacketMessageBean() {
    }

    protected GetRedPacketMessageBean(Parcel in) {
        this.hxid = in.readString ();
        this.nickname = in.readString ();
        this.redid = in.readString ();
        this.roomid = in.readString ();
    }

    public static final Parcelable.Creator <GetRedPacketMessageBean> CREATOR = new Parcelable.Creator <GetRedPacketMessageBean> () {
        @Override
        public GetRedPacketMessageBean createFromParcel(Parcel source) {
            return new GetRedPacketMessageBean ( source );
        }

        @Override
        public GetRedPacketMessageBean[] newArray(int size) {
            return new GetRedPacketMessageBean[size];
        }
    };
}
