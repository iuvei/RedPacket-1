package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lanjian
 * creat at 2019/9/22
 * description 获取充值信息实体类
 */
public class GetRechargeInfoBean implements Parcelable {


    /**
     * status : 1
     * result : [{"id":"14","paycode":"中国银行626662666626666666","payname":"sdk6667777","payurl":"images/1/2019/07/gfUl76ur35stau5Fu6SD4S4rSRUSmr.jpg","type":"3"},{"id":"18","paycode":"华夏银行","payname":"bank","payurl":"images/1/2019/09/ZwPSJPHZwsW2r5rW277khD2RH5Rwdm.png","type":"3"},{"id":"19","paycode":"广州银行","payname":"广州","payurl":"images/1/2019/09/xcbyCgYyCRmY4C6S4yY7BwmG1RCVJM.png","type":"3"}]
     * msg :
     */

    private int status;
    private String msg;
    private List <ResultBean> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List <ResultBean> getResult() {
        return result;
    }

    public void setResult(List <ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Parcelable {
        /**
         * id : 14
         * paycode : 中国银行626662666626666666
         * payname : sdk6667777
         * payurl : images/1/2019/07/gfUl76ur35stau5Fu6SD4S4rSRUSmr.jpg
         * type : 3
         */

        private String id;
        private String paycode;
        private String payname;
        private String payurl;
        private String type;
        private String bankname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPaycode() {
            return paycode;
        }

        public void setPaycode(String paycode) {
            this.paycode = paycode;
        }

        public String getPayname() {
            return payname;
        }

        public void setPayname(String payname) {
            this.payname = payname;
        }

        public String getPayurl() {
            return payurl;
        }

        public void setPayurl(String payurl) {
            this.payurl = payurl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString ( this.id );
            dest.writeString ( this.paycode );
            dest.writeString ( this.payname );
            dest.writeString ( this.payurl );
            dest.writeString ( this.type );
            dest.writeString ( this.bankname );
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.id = in.readString ();
            this.paycode = in.readString ();
            this.payname = in.readString ();
            this.payurl = in.readString ();
            this.type = in.readString ();
            this.bankname = in.readString ();
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
        dest.writeString ( this.msg );
        dest.writeList ( this.result );
    }

    public GetRechargeInfoBean() {
    }

    protected GetRechargeInfoBean(Parcel in) {
        this.status = in.readInt ();
        this.msg = in.readString ();
        this.result = new ArrayList <ResultBean> ();
        in.readList ( this.result, ResultBean.class.getClassLoader () );
    }

    public static final Parcelable.Creator <GetRechargeInfoBean> CREATOR = new Parcelable.Creator <GetRechargeInfoBean> () {
        @Override
        public GetRechargeInfoBean createFromParcel(Parcel source) {
            return new GetRechargeInfoBean ( source );
        }

        @Override
        public GetRechargeInfoBean[] newArray(int size) {
            return new GetRechargeInfoBean[size];
        }
    };
}
