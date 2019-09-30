package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * creat at 2019/9/21
 * description 充值金额列表实体类
 */
public class RechargeMoneyBean implements Parcelable {

    /**
     * status : 1
     * result : {"wechat":[{"paylist":[50,100,300,500,1000,2000,3000,5000],"title":"微信扫码充值50-5000","min":"50","max":"6000","paycode":"wxqr"}],"alipay":[{"paylist":[50,100,300,500,1000,2000,3000,5000],"title":"支付宝扫码充值50-5000","min":"50","max":"6000","paycode":"aliqr"}],"bank":[{"paylist":[50,100,300,500,1000,2000,3000,5000],"title":"支付宝扫码充值50-5000","min":"50","max":"6000"}]}
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
        private List <WechatBean> wechat;
        private List <AlipayBean> alipay;
        private List <BankBean> bank;

        public List <WechatBean> getWechat() {
            return wechat;
        }

        public void setWechat(List <WechatBean> wechat) {
            this.wechat = wechat;
        }

        public List <AlipayBean> getAlipay() {
            return alipay;
        }

        public void setAlipay(List <AlipayBean> alipay) {
            this.alipay = alipay;
        }

        public List <BankBean> getBank() {
            return bank;
        }

        public void setBank(List <BankBean> bank) {
            this.bank = bank;
        }


        public static class WechatBean implements Parcelable {
            /**
             * paylist : [50,100,300,500,1000,2000,3000,5000]
             * title : 微信扫码充值50-5000
             * min : 50
             * max : 6000
             * paycode : wxqr
             */

            private String title;
            private String min;
            private String max;
            private String paycode;
            private List <Integer> paylist;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMin() {
                return min;
            }

            public void setMin(String min) {
                this.min = min;
            }

            public String getMax() {
                return max;
            }

            public void setMax(String max) {
                this.max = max;
            }

            public String getPaycode() {
                return paycode;
            }

            public void setPaycode(String paycode) {
                this.paycode = paycode;
            }

            public List <Integer> getPaylist() {
                return paylist;
            }

            public void setPaylist(List <Integer> paylist) {
                this.paylist = paylist;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString ( this.title );
                dest.writeString ( this.min );
                dest.writeString ( this.max );
                dest.writeString ( this.paycode );
                dest.writeList ( this.paylist );
            }

            public WechatBean() {
            }

            protected WechatBean(Parcel in) {
                this.title = in.readString ();
                this.min = in.readString ();
                this.max = in.readString ();
                this.paycode = in.readString ();
                this.paylist = new ArrayList <Integer> ();
                in.readList ( this.paylist, Integer.class.getClassLoader () );
            }

            public static final Parcelable.Creator <WechatBean> CREATOR = new Parcelable.Creator <WechatBean> () {
                @Override
                public WechatBean createFromParcel(Parcel source) {
                    return new WechatBean ( source );
                }

                @Override
                public WechatBean[] newArray(int size) {
                    return new WechatBean[size];
                }
            };
        }

        public static class AlipayBean implements Parcelable {
            /**
             * paylist : [50,100,300,500,1000,2000,3000,5000]
             * title : 支付宝扫码充值50-5000
             * min : 50
             * max : 6000
             * paycode : aliqr
             */

            private String title;
            private String min;
            private String max;
            private String paycode;
            private List <Integer> paylist;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMin() {
                return min;
            }

            public void setMin(String min) {
                this.min = min;
            }

            public String getMax() {
                return max;
            }

            public void setMax(String max) {
                this.max = max;
            }

            public String getPaycode() {
                return paycode;
            }

            public void setPaycode(String paycode) {
                this.paycode = paycode;
            }

            public List <Integer> getPaylist() {
                return paylist;
            }

            public void setPaylist(List <Integer> paylist) {
                this.paylist = paylist;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString ( this.title );
                dest.writeString ( this.min );
                dest.writeString ( this.max );
                dest.writeString ( this.paycode );
                dest.writeList ( this.paylist );
            }

            public AlipayBean() {
            }

            protected AlipayBean(Parcel in) {
                this.title = in.readString ();
                this.min = in.readString ();
                this.max = in.readString ();
                this.paycode = in.readString ();
                this.paylist = new ArrayList <Integer> ();
                in.readList ( this.paylist, Integer.class.getClassLoader () );
            }

            public static final Parcelable.Creator <AlipayBean> CREATOR = new Parcelable.Creator <AlipayBean> () {
                @Override
                public AlipayBean createFromParcel(Parcel source) {
                    return new AlipayBean ( source );
                }

                @Override
                public AlipayBean[] newArray(int size) {
                    return new AlipayBean[size];
                }
            };
        }

        public static class BankBean implements Parcelable {
            /**
             * paylist : [50,100,300,500,1000,2000,3000,5000]
             * title : 支付宝扫码充值50-5000
             * min : 50
             * max : 6000
             */

            private String title;
            private String min;
            private String max;
            private List <Integer> paylist;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMin() {
                return min;
            }

            public void setMin(String min) {
                this.min = min;
            }

            public String getMax() {
                return max;
            }

            public void setMax(String max) {
                this.max = max;
            }

            public List <Integer> getPaylist() {
                return paylist;
            }

            public void setPaylist(List <Integer> paylist) {
                this.paylist = paylist;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString ( this.title );
                dest.writeString ( this.min );
                dest.writeString ( this.max );
                dest.writeList ( this.paylist );
            }

            public BankBean() {
            }

            protected BankBean(Parcel in) {
                this.title = in.readString ();
                this.min = in.readString ();
                this.max = in.readString ();
                this.paylist = new ArrayList <Integer> ();
                in.readList ( this.paylist, Integer.class.getClassLoader () );
            }

            public static final Parcelable.Creator <BankBean> CREATOR = new Parcelable.Creator <BankBean> () {
                @Override
                public BankBean createFromParcel(Parcel source) {
                    return new BankBean ( source );
                }

                @Override
                public BankBean[] newArray(int size) {
                    return new BankBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList ( this.wechat );
            dest.writeTypedList ( this.alipay );
            dest.writeTypedList ( this.bank );
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.wechat = in.createTypedArrayList ( WechatBean.CREATOR );
            this.alipay = in.createTypedArrayList ( AlipayBean.CREATOR );
            this.bank = in.createTypedArrayList ( BankBean.CREATOR );
        }

        public static final Parcelable.Creator <ResultBean> CREATOR = new Parcelable.Creator <ResultBean> () {
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

    public RechargeMoneyBean() {
    }

    protected RechargeMoneyBean(Parcel in) {
        this.status = in.readInt ();
        this.result = in.readParcelable ( ResultBean.class.getClassLoader () );
        this.msg = in.readString ();
    }

    public static final Parcelable.Creator <RechargeMoneyBean> CREATOR = new Parcelable.Creator <RechargeMoneyBean> () {
        @Override
        public RechargeMoneyBean createFromParcel(Parcel source) {
            return new RechargeMoneyBean ( source );
        }

        @Override
        public RechargeMoneyBean[] newArray(int size) {
            return new RechargeMoneyBean[size];
        }
    };
}
