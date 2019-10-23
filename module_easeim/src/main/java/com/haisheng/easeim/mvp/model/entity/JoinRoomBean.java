package com.haisheng.easeim.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * creat at 2019/10/23
 * description
 */
public class JoinRoomBean implements Parcelable {
    /**
     *  nickname : 123
     * isforb : 11
     */

    private String nickname;
    private int isforb;

    public int getIsforb() {
        return isforb;
    }

    public void setIsforb(int isforb) {
        this.isforb = isforb;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString ( this.nickname );
        dest.writeInt ( this.isforb );
    }

    public JoinRoomBean() {
    }

    protected JoinRoomBean(Parcel in) {
        this.nickname = in.readString ();
        this.isforb = in.readInt ();
    }

    public static final Parcelable.Creator <JoinRoomBean> CREATOR = new Parcelable.Creator <JoinRoomBean> () {
        @Override
        public JoinRoomBean createFromParcel(Parcel source) {
            return new JoinRoomBean ( source );
        }

        @Override
        public JoinRoomBean[] newArray(int size) {
            return new JoinRoomBean[size];
        }
    };
}
