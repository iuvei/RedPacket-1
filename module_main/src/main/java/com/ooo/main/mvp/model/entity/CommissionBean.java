package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * creat at 2019/9/11
 * description
 */
public class CommissionBean implements Parcelable {
    private String nickname;
    private String commission;
    private int num;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString ( this.nickname );
        dest.writeString ( this.commission );
        dest.writeInt ( this.num );
    }

    public CommissionBean() {
    }

    protected CommissionBean(Parcel in) {
        this.nickname = in.readString ();
        this.commission = in.readString ();
        this.num = in.readInt ();
    }

    public static final Creator <CommissionBean> CREATOR = new Creator <CommissionBean> () {
        @Override
        public CommissionBean createFromParcel(Parcel source) {
            return new CommissionBean ( source );
        }

        @Override
        public CommissionBean[] newArray(int size) {
            return new CommissionBean[size];
        }
    };
}
