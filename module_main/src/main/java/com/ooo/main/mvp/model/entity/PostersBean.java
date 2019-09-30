package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * creat at 2019/9/20
 * description
 * 推广海报实体类
 */
public class PostersBean implements Parcelable {

    /**
     * status : 1
     * result : [{"posterid":"1","title":"二维码","visnums":"1","url":"http://5949.iiio.top/addons/sz_yi/data/poster/1/fa0dfef17921de35a6e6f18753c6888f.png"}]
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
         * posterid : 1
         * title : 二维码
         * visnums : 1
         * url : http://5949.iiio.top/addons/sz_yi/data/poster/1/fa0dfef17921de35a6e6f18753c6888f.png
         */

        private String posterid;
        private String title;
        private String visnums;
        private String url;

        public String getPosterid() {
            return posterid;
        }

        public void setPosterid(String posterid) {
            this.posterid = posterid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVisnums() {
            return visnums;
        }

        public void setVisnums(String visnums) {
            this.visnums = visnums;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString ( this.posterid );
            dest.writeString ( this.title );
            dest.writeString ( this.visnums );
            dest.writeString ( this.url );
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.posterid = in.readString ();
            this.title = in.readString ();
            this.visnums = in.readString ();
            this.url = in.readString ();
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

    public PostersBean() {
    }

    protected PostersBean(Parcel in) {
        this.status = in.readInt ();
        this.msg = in.readString ();
        this.result = new ArrayList <ResultBean> ();
        in.readList ( this.result, ResultBean.class.getClassLoader () );
    }

    public static final Parcelable.Creator <PostersBean> CREATOR = new Parcelable.Creator <PostersBean> () {
        @Override
        public PostersBean createFromParcel(Parcel source) {
            return new PostersBean ( source );
        }

        @Override
        public PostersBean[] newArray(int size) {
            return new PostersBean[size];
        }
    };
}
