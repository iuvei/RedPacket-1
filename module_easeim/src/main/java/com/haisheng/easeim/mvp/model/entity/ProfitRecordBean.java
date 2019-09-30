package com.haisheng.easeim.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 0
 * creat at 2019/9/27
 * description
 * 盈亏记录实体类
 */
public class ProfitRecordBean implements Parcelable {

    /**
     * status : 1
     * result : {"list":[{"gold":"50.00","details":"您的点数【1】,闲家点数【】,押金【50】赔付【0】手续费【0】","addtime":"2019-09-27 19:30:34","paytype":"27","setid":"72","roomid":"3"},{"gold":"-50.00","details":"【押金:5.00*(11-1)*1.00】","addtime":"2019-09-27 19:27:33","paytype":"18","setid":"72","roomid":"3"},{"gold":"9.95","details":"您的点数【9】,庄家点数【4】,押金【5】赔付【5】手续费【0.05】","addtime":"2019-09-27 19:03:55","paytype":"27","setid":"59","roomid":"3"},{"gold":"0.00","details":"您的点数【5】,庄家点数【8】,押金【5】赔付【-5】手续费【0】","addtime":"2019-09-27 19:03:00","paytype":"27","setid":"58","roomid":"3"},{"gold":"-5.00","details":"【押金:5.00*1.00】","addtime":"2019-09-27 19:01:01","paytype":"26","setid":"59","roomid":"3"},{"gold":"-5.00","details":"【押金:5.00*1.00】","addtime":"2019-09-27 19:00:49","paytype":"26","setid":"58","roomid":"3"},{"gold":"0.00","details":"您的点数【1】,庄家点数【7】,押金【5】赔付【-5】手续费【0】","addtime":"2019-09-27 18:56:46","paytype":"27","setid":"55","roomid":"3"},{"gold":"9.95","details":"您的点数【4】,庄家点数【3】,押金【5】赔付【5】手续费【0.05】","addtime":"2019-09-27 18:54:55","paytype":"27","setid":"54","roomid":"3"},{"gold":"-5.00","details":"【押金:5.00*1.00】","addtime":"2019-09-27 18:54:34","paytype":"26","setid":"55","roomid":"3"},{"gold":"9.95","details":"您的点数【0】,庄家点数【3】,押金【5】赔付【5】手续费【0.05】","addtime":"2019-09-27 18:54:21","paytype":"27","setid":"53","roomid":"3"},{"gold":"-5.00","details":"【押金:5.00*1.00】","addtime":"2019-09-27 18:52:19","paytype":"26","setid":"54","roomid":"3"},{"gold":"-5.00","details":"【押金:5.00*1.00】","addtime":"2019-09-27 18:52:11","paytype":"26","setid":"53","roomid":"3"},{"gold":"54.95","details":"您的点数【4】,闲家点数【4】,押金【50】赔付【5】手续费【0.05】","addtime":"2019-09-27 18:50:30","paytype":"27","setid":"52","roomid":"3"},{"gold":"-50.00","details":"【押金:5.00*(11-1)*1.00】","addtime":"2019-09-27 18:47:29","paytype":"18","setid":"52","roomid":"3"},{"gold":"50.00","details":"您的点数【5】,闲家点数【】,押金【50】赔付【0】手续费【0】","addtime":"2019-09-27 18:44:22","paytype":"27","setid":"48","roomid":"3"}],"paytype":["扫雷包踩雷赔付","扫雷群赔付到账","禁抢群赔付到账","牛牛结算","牛牛发包","牛牛抢包","牛牛手续费支出"],"allmoney":"99998936.81"}
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
         * list : [{"gold":"50.00","details":"您的点数【1】,闲家点数【】,押金【50】赔付【0】手续费【0】","addtime":"2019-09-27 19:30:34","paytype":"27","setid":"72","roomid":"3"},{"gold":"-50.00","details":"【押金:5.00*(11-1)*1.00】","addtime":"2019-09-27 19:27:33","paytype":"18","setid":"72","roomid":"3"},{"gold":"9.95","details":"您的点数【9】,庄家点数【4】,押金【5】赔付【5】手续费【0.05】","addtime":"2019-09-27 19:03:55","paytype":"27","setid":"59","roomid":"3"},{"gold":"0.00","details":"您的点数【5】,庄家点数【8】,押金【5】赔付【-5】手续费【0】","addtime":"2019-09-27 19:03:00","paytype":"27","setid":"58","roomid":"3"},{"gold":"-5.00","details":"【押金:5.00*1.00】","addtime":"2019-09-27 19:01:01","paytype":"26","setid":"59","roomid":"3"},{"gold":"-5.00","details":"【押金:5.00*1.00】","addtime":"2019-09-27 19:00:49","paytype":"26","setid":"58","roomid":"3"},{"gold":"0.00","details":"您的点数【1】,庄家点数【7】,押金【5】赔付【-5】手续费【0】","addtime":"2019-09-27 18:56:46","paytype":"27","setid":"55","roomid":"3"},{"gold":"9.95","details":"您的点数【4】,庄家点数【3】,押金【5】赔付【5】手续费【0.05】","addtime":"2019-09-27 18:54:55","paytype":"27","setid":"54","roomid":"3"},{"gold":"-5.00","details":"【押金:5.00*1.00】","addtime":"2019-09-27 18:54:34","paytype":"26","setid":"55","roomid":"3"},{"gold":"9.95","details":"您的点数【0】,庄家点数【3】,押金【5】赔付【5】手续费【0.05】","addtime":"2019-09-27 18:54:21","paytype":"27","setid":"53","roomid":"3"},{"gold":"-5.00","details":"【押金:5.00*1.00】","addtime":"2019-09-27 18:52:19","paytype":"26","setid":"54","roomid":"3"},{"gold":"-5.00","details":"【押金:5.00*1.00】","addtime":"2019-09-27 18:52:11","paytype":"26","setid":"53","roomid":"3"},{"gold":"54.95","details":"您的点数【4】,闲家点数【4】,押金【50】赔付【5】手续费【0.05】","addtime":"2019-09-27 18:50:30","paytype":"27","setid":"52","roomid":"3"},{"gold":"-50.00","details":"【押金:5.00*(11-1)*1.00】","addtime":"2019-09-27 18:47:29","paytype":"18","setid":"52","roomid":"3"},{"gold":"50.00","details":"您的点数【5】,闲家点数【】,押金【50】赔付【0】手续费【0】","addtime":"2019-09-27 18:44:22","paytype":"27","setid":"48","roomid":"3"}]
         * paytype : ["扫雷包踩雷赔付","扫雷群赔付到账","禁抢群赔付到账","牛牛结算","牛牛发包","牛牛抢包","牛牛手续费支出"]
         * allmoney : 99998936.81
         */

        private String allmoney;
        private List <ListBean> list;
        private List <String> paytype;

        public String getAllmoney() {
            return allmoney;
        }

        public void setAllmoney(String allmoney) {
            this.allmoney = allmoney;
        }

        public List <ListBean> getList() {
            return list;
        }

        public void setList(List <ListBean> list) {
            this.list = list;
        }

        public List <String> getPaytype() {
            return paytype;
        }

        public void setPaytype(List <String> paytype) {
            this.paytype = paytype;
        }

        public static class ListBean {
            /**
             * gold : 50.00
             * details : 您的点数【1】,闲家点数【】,押金【50】赔付【0】手续费【0】
             * addtime : 2019-09-27 19:30:34
             * paytype : 27
             * setid : 72
             * roomid : 3
             */

            private String gold;
            private String details;
            private String addtime;
            private String paytype;
            private String setid;
            private String roomid;

            public String getGold() {
                return gold;
            }

            public void setGold(String gold) {
                this.gold = gold;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getPaytype() {
                return paytype;
            }

            public void setPaytype(String paytype) {
                this.paytype = paytype;
            }

            public String getSetid() {
                return setid;
            }

            public void setSetid(String setid) {
                this.setid = setid;
            }

            public String getRoomid() {
                return roomid;
            }

            public void setRoomid(String roomid) {
                this.roomid = roomid;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString ( this.allmoney );
            dest.writeList ( this.list );
            dest.writeStringList ( this.paytype );
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.allmoney = in.readString ();
            this.list = new ArrayList <ListBean> ();
            in.readList ( this.list, ListBean.class.getClassLoader () );
            this.paytype = in.createStringArrayList ();
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

    public ProfitRecordBean() {
    }

    protected ProfitRecordBean(Parcel in) {
        this.status = in.readInt ();
        this.result = in.readParcelable ( ResultBean.class.getClassLoader () );
        this.msg = in.readString ();
    }

    public static final Parcelable.Creator <ProfitRecordBean> CREATOR = new Parcelable.Creator <ProfitRecordBean> () {
        @Override
        public ProfitRecordBean createFromParcel(Parcel source) {
            return new ProfitRecordBean ( source );
        }

        @Override
        public ProfitRecordBean[] newArray(int size) {
            return new ProfitRecordBean[size];
        }
    };
}
