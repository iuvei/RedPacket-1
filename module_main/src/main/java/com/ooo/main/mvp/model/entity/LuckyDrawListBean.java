package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.jessyan.armscomponent.commonres.utils.ConvertNumUtils;

/**
 * @author lanjian
 * creat at 2019/9/29
 * description
 * 幸运抽奖记录
 */
public class LuckyDrawListBean implements Parcelable {

    /**
     * status : 1
     * result : [{"id":"13","uniacid":"1","uid":"970008","gold":"888.88","getnums":"0.00","name":"幸运转盘","addtime":"1569729493"},{"id":"14","uniacid":"1","uid":"970008","gold":"888.88","getnums":"0.00","name":"幸运转盘","addtime":"1569729511"},{"id":"15","uniacid":"1","uid":"970008","gold":"888.88","getnums":"0.00","name":"幸运转盘","addtime":"1569729526"}]
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
         * id : 13
         * uniacid : 1
         * uid : 970008
         * gold : 888.88
         * getnums : 0.00
         * name : 幸运转盘
         * addtime : 1569729493
         */

        private String id;
        private String uniacid;
        private String uid;
        private String gold;
        private String getnums;
        private String name;
        private String addtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUniacid() {
            return uniacid;
        }

        public void setUniacid(String uniacid) {
            this.uniacid = uniacid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public String getGetnums() {
            return getnums;
        }

        public void setGetnums(String getnums) {
            this.getnums = getnums;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat ( "YYYY-MM-dd HH:mm:ss" );
        public String getAddtimeYYYYMMDD() {
            return simpleDateFormat.format ( new Date ( ConvertNumUtils.stringToLong ( addtime ) ) );
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString ( this.id );
            dest.writeString ( this.uniacid );
            dest.writeString ( this.uid );
            dest.writeString ( this.gold );
            dest.writeString ( this.getnums );
            dest.writeString ( this.name );
            dest.writeString ( this.addtime );
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.id = in.readString ();
            this.uniacid = in.readString ();
            this.uid = in.readString ();
            this.gold = in.readString ();
            this.getnums = in.readString ();
            this.name = in.readString ();
            this.addtime = in.readString ();
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

    public LuckyDrawListBean() {
    }

    protected LuckyDrawListBean(Parcel in) {
        this.status = in.readInt ();
        this.msg = in.readString ();
        this.result = new ArrayList <ResultBean> ();
        in.readList ( this.result, ResultBean.class.getClassLoader () );
    }

    public static final Parcelable.Creator <LuckyDrawListBean> CREATOR = new Parcelable.Creator <LuckyDrawListBean> () {
        @Override
        public LuckyDrawListBean createFromParcel(Parcel source) {
            return new LuckyDrawListBean ( source );
        }

        @Override
        public LuckyDrawListBean[] newArray(int size) {
            return new LuckyDrawListBean[size];
        }
    };
}
