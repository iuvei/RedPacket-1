package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lanjian
 * creat at 2019/9/11
 * description
 * 佣金列表
 */
public class CommissionInfo implements Parcelable {
    private String ID;
    private String commission;
    private String detail;
    private String date;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString ( this.ID );
        dest.writeString ( this.commission );
        dest.writeString ( this.detail );
        dest.writeString ( this.date );
    }

    public CommissionInfo() {
    }

    protected CommissionInfo(Parcel in) {
        this.ID = in.readString ();
        this.commission = in.readString ();
        this.detail = in.readString ();
        this.date = in.readString ();
    }

    public static final Parcelable.Creator <CommissionInfo> CREATOR = new Parcelable.Creator <CommissionInfo> () {
        @Override
        public CommissionInfo createFromParcel(Parcel source) {
            return new CommissionInfo ( source );
        }

        @Override
        public CommissionInfo[] newArray(int size) {
            return new CommissionInfo[size];
        }
    };
}
