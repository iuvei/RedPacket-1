package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lanjian
 * creat at 2019/9/20
 * description
 * 佣金排行榜实体类
 */
public class RankingBean implements Parcelable {

    /**
     * status : 1
     * result : {"today":[{"allmoney":"6.34","uid":"970004","rank":1}],"yesterday":[{"allmoney":"28.62","uid":"970004","rank":1}],"week":[{"allmoney":"35.58","uid":"970004","rank":1}]}
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
        private List <TodayBean> today;
        private List <YesterdayBean> yesterday;
        private List <WeekBean> week;

        public List <TodayBean> getToday() {
            return today;
        }

        public void setToday(List <TodayBean> today) {
            this.today = today;
        }

        public List <YesterdayBean> getYesterday() {
            return yesterday;
        }

        public void setYesterday(List <YesterdayBean> yesterday) {
            this.yesterday = yesterday;
        }

        public List <WeekBean> getWeek() {
            return week;
        }

        public void setWeek(List <WeekBean> week) {
            this.week = week;
        }

        public static class TodayBean {
            /**
             * allmoney : 6.34
             * uid : 970004
             * rank : 1
             */

            private String allmoney;
            private String uid;
            private int rank;

            public String getAllmoney() {
                return allmoney;
            }

            public void setAllmoney(String allmoney) {
                this.allmoney = allmoney;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }
        }

        public static class YesterdayBean {
            /**
             * allmoney : 28.62
             * uid : 970004
             * rank : 1
             */

            private String allmoney;
            private String uid;
            private int rank;

            public String getAllmoney() {
                return allmoney;
            }

            public void setAllmoney(String allmoney) {
                this.allmoney = allmoney;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }
        }

        public static class WeekBean {
            /**
             * allmoney : 35.58
             * uid : 970004
             * rank : 1
             */

            private String allmoney;
            private String uid;
            private int rank;

            public String getAllmoney() {
                return allmoney;
            }

            public void setAllmoney(String allmoney) {
                this.allmoney = allmoney;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList ( this.today );
            dest.writeList ( this.yesterday );
            dest.writeList ( this.week );
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.today = new ArrayList <TodayBean> ();
            in.readList ( this.today, TodayBean.class.getClassLoader () );
            this.yesterday = new ArrayList <YesterdayBean> ();
            in.readList ( this.yesterday, YesterdayBean.class.getClassLoader () );
            this.week = new ArrayList <WeekBean> ();
            in.readList ( this.week, WeekBean.class.getClassLoader () );
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

    public RankingBean() {
    }

    protected RankingBean(Parcel in) {
        this.status = in.readInt ();
        this.result = in.readParcelable ( ResultBean.class.getClassLoader () );
        this.msg = in.readString ();
    }

    public static final Parcelable.Creator <RankingBean> CREATOR = new Parcelable.Creator <RankingBean> () {
        @Override
        public RankingBean createFromParcel(Parcel source) {
            return new RankingBean ( source );
        }

        @Override
        public RankingBean[] newArray(int size) {
            return new RankingBean[size];
        }
    };
}
