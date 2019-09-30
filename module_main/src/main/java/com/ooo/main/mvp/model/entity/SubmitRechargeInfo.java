package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 0
 * creat at 2019/9/22
 * description 提交充值信息实体类
 */
public class SubmitRechargeInfo implements Parcelable {

    /**
     * status : 1
     * result : 提交成功，请等待审核！
     * msg :
     */

    private int status;
    private String result;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt ( this.status );
        dest.writeString ( this.result );
        dest.writeString ( this.msg );
    }

    public SubmitRechargeInfo() {
    }

    protected SubmitRechargeInfo(Parcel in) {
        this.status = in.readInt ();
        this.result = in.readString ();
        this.msg = in.readString ();
    }

    public static final Parcelable.Creator <SubmitRechargeInfo> CREATOR = new Parcelable.Creator <SubmitRechargeInfo> () {
        @Override
        public SubmitRechargeInfo createFromParcel(Parcel source) {
            return new SubmitRechargeInfo ( source );
        }

        @Override
        public SubmitRechargeInfo[] newArray(int size) {
            return new SubmitRechargeInfo[size];
        }
    };
}
