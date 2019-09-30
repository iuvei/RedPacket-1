package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 0
 * creat at 2019/9/19
 * description 弹出公告实体类
 */
public class AdvertisingBean implements Parcelable {


    /**
     * status : 1
     * result : {"affiche":"1","question":"1"}
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
         * affiche : 1
         * question : 1
         */

        private String affiche;
        private String question;

        public String getAffiche() {
            return affiche;
        }

        public void setAffiche(String affiche) {
            this.affiche = affiche;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString ( this.affiche );
            dest.writeString ( this.question );
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.affiche = in.readString ();
            this.question = in.readString ();
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

    public AdvertisingBean() {
    }

    protected AdvertisingBean(Parcel in) {
        this.status = in.readInt ();
        this.result = in.readParcelable ( ResultBean.class.getClassLoader () );
        this.msg = in.readString ();
    }

    public static final Parcelable.Creator <AdvertisingBean> CREATOR = new Parcelable.Creator <AdvertisingBean> () {
        @Override
        public AdvertisingBean createFromParcel(Parcel source) {
            return new AdvertisingBean ( source );
        }

        @Override
        public AdvertisingBean[] newArray(int size) {
            return new AdvertisingBean[size];
        }
    };
}
