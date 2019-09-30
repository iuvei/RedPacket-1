package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 0
 * creat at 2019/9/11
 * description
 */
public class LuckyBean implements Parcelable {
    private String prize;
    private String award;

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString ( this.prize );
        dest.writeString ( this.award );
    }

    public LuckyBean() {
    }

    protected LuckyBean(Parcel in) {
        this.prize = in.readString ();
        this.award = in.readString ();
    }

    public static final Parcelable.Creator <LuckyBean> CREATOR = new Parcelable.Creator <LuckyBean> () {
        @Override
        public LuckyBean createFromParcel(Parcel source) {
            return new LuckyBean ( source );
        }

        @Override
        public LuckyBean[] newArray(int size) {
            return new LuckyBean[size];
        }
    };
}
