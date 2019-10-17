package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * creat at 2019/9/20
 * description
 * 佣金排行榜实体类
 */
public class RankingBean implements Parcelable {


    /**
     * status : 1
     * result : {"today":[{"allmoney":"2076.81","uid":"970013","nickname":"123456","rank":1},{"allmoney":"425.41","uid":"273486","nickname":"抢分","rank":2},{"allmoney":"247.72","uid":"569863","nickname":"177218","rank":3},{"allmoney":"195.26","uid":"101315","nickname":"小小","rank":4},{"allmoney":"182.35","uid":"970020","nickname":"dkdk","rank":5},{"allmoney":"127.24","uid":"536230","nickname":"人生巅峰","rank":6},{"allmoney":"48.96","uid":"970014","nickname":"金都酒店酒店","rank":7},{"allmoney":"47.94","uid":"229307","nickname":"恭喜发财","rank":8},{"allmoney":"19.88","uid":"970021","nickname":"hsfggg","rank":9},{"allmoney":"16.74","uid":"970004","nickname":"水","rank":10}],"yesterday":[{"allmoney":"2499.86","uid":"970013","nickname":"123456","rank":1},{"allmoney":"248.59","uid":"970014","nickname":"金都酒店酒店","rank":2},{"allmoney":"15.97","uid":"970004","nickname":"水","rank":3},{"allmoney":"9.19","uid":"970020","nickname":"dkdk","rank":4}],"week":[{"allmoney":"4576.67","uid":"970013","nickname":"123456","rank":1},{"allmoney":"957.52","uid":"970014","nickname":"金都酒店酒店","rank":2},{"allmoney":"425.41","uid":"273486","nickname":"抢分","rank":3},{"allmoney":"247.72","uid":"569863","nickname":"177218","rank":4},{"allmoney":"195.26","uid":"101315","nickname":"小小","rank":5},{"allmoney":"191.54","uid":"970020","nickname":"dkdk","rank":6},{"allmoney":"127.24","uid":"536230","nickname":"人生巅峰","rank":7},{"allmoney":"47.94","uid":"229307","nickname":"恭喜发财","rank":8},{"allmoney":"38.62","uid":"970004","nickname":"水","rank":9},{"allmoney":"19.88","uid":"970021","nickname":"hsfggg","rank":10}]}
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
             * allmoney : 2076.81
             * uid : 970013
             * nickname : 123456
             * rank : 1
             */

            private String allmoney;
            private String uid;
            private String nickname;
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

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
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
             * allmoney : 2499.86
             * uid : 970013
             * nickname : 123456
             * rank : 1
             */

            private String allmoney;
            private String uid;
            private String nickname;
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

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
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
             * allmoney : 4576.67
             * uid : 970013
             * nickname : 123456
             * rank : 1
             */

            private String allmoney;
            private String uid;
            private String nickname;
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

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
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
