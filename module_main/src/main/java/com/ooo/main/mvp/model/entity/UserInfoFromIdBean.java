package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 0
 * creat at 2019/9/20
 * description
 */
public class UserInfoFromIdBean implements Parcelable {

    /**
     * status : 1
     * result : {"id":"969999","logintime":"1569558228","nickname":"2","avatar":"http://5761.iiio.top/attachment/images/1/2019/07/H93CeEh9A9YHg99z6hXecx9EjjEOA6.png","gender":"1"}
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
         * id : 969999
         * logintime : 1569558228
         * nickname : 2
         * avatar : http://5761.iiio.top/attachment/images/1/2019/07/H93CeEh9A9YHg99z6hXecx9EjjEOA6.png
         * gender : 1
         */

        private String id;
        private String logintime;
        private String nickname;
        private String avatar;
        private String gender;
        private String incode;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogintime() {
            return logintime;
        }

        public void setLogintime(String logintime) {
            this.logintime = logintime;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getIncode() {
            return incode;
        }

        public void setIncode(String incode) {
            this.incode = incode;
        }

        public boolean isMan(){
            if (gender.equals ( "1" )){
                return true;
            }else{
                return false;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString ( this.id );
            dest.writeString ( this.logintime );
            dest.writeString ( this.nickname );
            dest.writeString ( this.avatar );
            dest.writeString ( this.gender );
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.id = in.readString ();
            this.logintime = in.readString ();
            this.nickname = in.readString ();
            this.avatar = in.readString ();
            this.gender = in.readString ();
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

    public UserInfoFromIdBean() {
    }

    protected UserInfoFromIdBean(Parcel in) {
        this.status = in.readInt ();
        this.result = in.readParcelable ( ResultBean.class.getClassLoader () );
        this.msg = in.readString ();
    }

    public static final Parcelable.Creator <UserInfoFromIdBean> CREATOR = new Parcelable.Creator <UserInfoFromIdBean> () {
        @Override
        public UserInfoFromIdBean createFromParcel(Parcel source) {
            return new UserInfoFromIdBean ( source );
        }

        @Override
        public UserInfoFromIdBean[] newArray(int size) {
            return new UserInfoFromIdBean[size];
        }
    };
}
