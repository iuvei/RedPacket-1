package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lanjian
 * creat at 2019/9/29
 * description
 * 启动幸运抽奖
 */
public class StartLuckyDrawBean implements Parcelable {


    /**
     * status : 1
     * result : {"pricekk":1,"pricemoney":"888.88"}
     * msg :
     */

    private int status;
    private ResultBean result;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ResultBean implements Parcelable {
        /**
         * pricekk : 1
         * pricemoney : 888.88
         */

        private int pricekk;
        private String pricemoney;

        public int getPricekk() {
            return pricekk;
        }

        public void setPricekk(int pricekk) {
            this.pricekk = pricekk;
        }

        public String getPricemoney() {
            return pricemoney;
        }

        public void setPricemoney(String pricemoney) {
            this.pricemoney = pricemoney;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt ( this.pricekk );
            dest.writeString ( this.pricemoney );
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.pricekk = in.readInt ();
            this.pricemoney = in.readString ();
        }

        public static final Creator <ResultBean> CREATOR = new Creator <ResultBean> () {
            @Override
            public ResultBean createFromParcel(Parcel source) {
                return new ResultBean ( source );
            }

            @Override
            public ResultBean[] newArray(int size) {
                return new ResultBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt ( this.status );
        dest.writeParcelable ( this.result, flags );
        dest.writeString ( this.msg );
    }

    public StartLuckyDrawBean() {
    }

    protected StartLuckyDrawBean(Parcel in) {
        this.status = in.readInt ();
        this.result = in.readParcelable ( ResultBean.class.getClassLoader () );
        this.msg = in.readString ();
    }

    public static final Parcelable.Creator <StartLuckyDrawBean> CREATOR = new Parcelable.Creator <StartLuckyDrawBean> () {
        @Override
        public StartLuckyDrawBean createFromParcel(Parcel source) {
            return new StartLuckyDrawBean ( source );
        }

        @Override
        public StartLuckyDrawBean[] newArray(int size) {
            return new StartLuckyDrawBean[size];
        }
    };
}
