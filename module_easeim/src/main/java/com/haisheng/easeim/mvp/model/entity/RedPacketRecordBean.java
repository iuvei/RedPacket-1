package com.haisheng.easeim.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * creat at 2019/9/25
 * description
 * 收发红包记录
 */
public class RedPacketRecordBean implements Parcelable {


    /**
     * status : 1
     * result : {"list":[{"gold":"-6.00","details":"禁抢发包","addtime":"2019-09-25 15:33:04","paytype":"2","id":"10","setid":"85029","roomid":"2"},{"gold":"-6.00","details":"扫雷发包","addtime":"2019-09-25 15:31:30","paytype":"1","id":"8","setid":"85028","roomid":"1"},{"gold":"-6.00","details":"扫雷发包","addtime":"2019-09-25 15:23:20","paytype":"1","id":"6","setid":"85027","roomid":"1"},{"gold":"-6.00","details":"扫雷发包","addtime":"2019-09-25 15:01:08","paytype":"1","id":"3","setid":"85022","roomid":"1"}],"paytype":["扫雷发包","逾期退包","禁抢发包","福利发包","牛牛发包"],"allmoney":-24}
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
         * list : [{"gold":"-6.00","details":"禁抢发包","addtime":"2019-09-25 15:33:04","paytype":"2","id":"10","setid":"85029","roomid":"2"},{"gold":"-6.00","details":"扫雷发包","addtime":"2019-09-25 15:31:30","paytype":"1","id":"8","setid":"85028","roomid":"1"},{"gold":"-6.00","details":"扫雷发包","addtime":"2019-09-25 15:23:20","paytype":"1","id":"6","setid":"85027","roomid":"1"},{"gold":"-6.00","details":"扫雷发包","addtime":"2019-09-25 15:01:08","paytype":"1","id":"3","setid":"85022","roomid":"1"}]
         * paytype : ["扫雷发包","逾期退包","禁抢发包","福利发包","牛牛发包"]
         * allmoney : -24
         */

        private double allmoney;
        private List <ListBean> list;
        private List <String> paytype;

        public double getAllmoney() {
            return allmoney;
        }

        public void setAllmoney(double allmoney) {
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
             * gold : -6.00
             * details : 禁抢发包
             * addtime : 2019-09-25 15:33:04
             * paytype : 2
             * id : 10
             * setid : 85029
             * roomid : 2
             */

            private String gold;
            private String details;
            private String addtime;
            private String paytype;
            private String id;
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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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
            dest.writeDouble ( this.allmoney );
            dest.writeList ( this.list );
            dest.writeStringList ( this.paytype );
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.allmoney = in.readInt ();
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

    public RedPacketRecordBean() {
    }

    protected RedPacketRecordBean(Parcel in) {
        this.status = in.readInt ();
        this.result = in.readParcelable ( ResultBean.class.getClassLoader () );
        this.msg = in.readString ();
    }

    public static final Parcelable.Creator <RedPacketRecordBean> CREATOR = new Parcelable.Creator <RedPacketRecordBean> () {
        @Override
        public RedPacketRecordBean createFromParcel(Parcel source) {
            return new RedPacketRecordBean ( source );
        }

        @Override
        public RedPacketRecordBean[] newArray(int size) {
            return new RedPacketRecordBean[size];
        }
    };
}
