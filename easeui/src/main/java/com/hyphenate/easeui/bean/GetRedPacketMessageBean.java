package com.hyphenate.easeui.bean;

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


    //发包者id
    private String hxid;
    //抢包人昵称
    private String nickname;
    private String redid;
    //发包人昵称
    private String nickname1;
    private String roomid;
    private int over;

    //抢包者id
    private String hxid1;

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

    public int getOver() {
        return over;
    }

    public void setOver(int over) {
        this.over = over;
    }

    public String getHxid1() {
        return hxid1;
    }

    public void setHxid1(String hxid1) {
        this.hxid1 = hxid1;
    }

    public String getNickname1() {
        return nickname1;
    }

    public void setNickname1(String nickname1) {
        this.nickname1 = nickname1;
    }

    public boolean isGetAllRedPacket(){
        if (over==1){
            //红包已领完
            return true;
        }else{
            //红包未领完
            return false;
        }
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
        dest.writeString ( this.hxid1 );
        dest.writeString ( this.nickname1 );
    }

    public GetRedPacketMessageBean() {
    }

    protected GetRedPacketMessageBean(Parcel in) {
        this.hxid = in.readString ();
        this.nickname = in.readString ();
        this.redid = in.readString ();
        this.roomid = in.readString ();
        this.hxid1 = in.readString ();
        this.nickname1 = in.readString ();
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
