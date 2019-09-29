package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author lanjian
 * creat at 2019/9/29
 * description
 * 幸运抽奖设置参数
 */
public class LuckyDrawSettingBean implements Parcelable {


    /**
     * status : 1
     * result : {"welfarenums":"2","list":["谢谢参与","888.88","188.88","谢谢参与","88.88","58.88","谢谢参与","28.88","18.88","8.88"]}
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
         * welfarenums : 2
         * list : ["谢谢参与","888.88","188.88","谢谢参与","88.88","58.88","谢谢参与","28.88","18.88","8.88"]
         */

        private String welfarenums;
        private List <String> list;

        public String getWelfarenums() {
            return welfarenums;
        }

        public void setWelfarenums(String welfarenums) {
            this.welfarenums = welfarenums;
        }

        public List <String> getList() {
            return list;
        }

        public void setList(List <String> list) {
            this.list = list;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString ( this.welfarenums );
            dest.writeStringList ( this.list );
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.welfarenums = in.readString ();
            this.list = in.createStringArrayList ();
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

    public LuckyDrawSettingBean() {
    }

    protected LuckyDrawSettingBean(Parcel in) {
        this.status = in.readInt ();
        this.result = in.readParcelable ( ResultBean.class.getClassLoader () );
        this.msg = in.readString ();
    }

    public static final Parcelable.Creator <LuckyDrawSettingBean> CREATOR = new Parcelable.Creator <LuckyDrawSettingBean> () {
        @Override
        public LuckyDrawSettingBean createFromParcel(Parcel source) {
            return new LuckyDrawSettingBean ( source );
        }

        @Override
        public LuckyDrawSettingBean[] newArray(int size) {
            return new LuckyDrawSettingBean[size];
        }
    };
}
