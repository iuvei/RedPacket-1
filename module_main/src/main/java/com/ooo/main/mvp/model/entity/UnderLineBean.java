package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * creat at 2019/9/11
 * description
 */
public class UnderLineBean implements Parcelable {
    private String nickName;
    private int count;
    private String creatDate;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString ( this.nickName );
        dest.writeInt ( this.count );
        dest.writeString ( this.creatDate );
    }

    public UnderLineBean() {
    }

    protected UnderLineBean(Parcel in) {
        this.nickName = in.readString ();
        this.count = in.readInt ();
        this.creatDate = in.readString ();
    }

    public static final Parcelable.Creator <UnderLineBean> CREATOR = new Parcelable.Creator <UnderLineBean> () {
        @Override
        public UnderLineBean createFromParcel(Parcel source) {
            return new UnderLineBean ( source );
        }

        @Override
        public UnderLineBean[] newArray(int size) {
            return new UnderLineBean[size];
        }
    };
}
