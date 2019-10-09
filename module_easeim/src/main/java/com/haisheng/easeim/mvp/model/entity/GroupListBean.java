package com.haisheng.easeim.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonsdk.utils.CharacterParser;

/**
 * creat at 2019/10/9
 * description 群成员实体类
 */
public class GroupListBean implements Parcelable {

    /**
     * status : 1
     * result : [{"nickname":"群主","avatar":"http://5949.iiio.top/attachment/images/1/2019/07/z9z9wuR6uH98Fz300w33KFPUPPrdef.jpg"},{"nickname":"免死","avatar":"http://5949.iiio.top/attachment/images/1/2019/09/cIPpiZM57M5dhdvxRsWtd7rpZa8PDg.png"},{"uid":"970015","nickname":"1112","avatar":"http://5949.iiio.top/attachment/images/1/2019/10/YCmtE5RO0555P5T3a5YzT5oE5P0yA3.png"},{"uid":"970010","nickname":"9527v","avatar":"http://5949.iiio.top/attachment/images/1/2019/10/peT7754mUU3KSTSUQm3Z5KF2SZu34u.png"},{"uid":"970013","nickname":"123456","avatar":"http://5949.iiio.top/attachment/images/1/2019/10/aAF0LZB5LoUXLNx50VgoKdgLBVZNND.png"},{"uid":"970008","nickname":"lan1","avatar":"http://5949.iiio.top/attachment/0"},{"uid":"970017","nickname":"看看罗","avatar":"http://5949.iiio.top/attachment/images/1/2019/10/f3oKA0qhpVac30mQcMq0mMvkWTO3Zh.png"},{"uid":"970003","nickname":"你好","avatar":"http://5949.iiio.top/attachment/images/1/2019/09/Wa3BWnGwTfmwbb5mB5N2m6r3aFfTVb.png"}]
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
         * nickname : 群主
         * avatar : http://5949.iiio.top/attachment/images/1/2019/07/z9z9wuR6uH98Fz300w33KFPUPPrdef.jpg
         * uid : 970015
         */

        private String nickname;
        private String avatar;
        private String uid ="";
        private CharacterParser characterParser = new CharacterParser ();
        private String spelling;


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

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString ( this.nickname );
            dest.writeString ( this.avatar );
            dest.writeString ( this.uid );
        }

        public ResultBean(){};

        protected ResultBean(Parcel in) {
            this.nickname = in.readString ();
            this.avatar = in.readString ();
            this.uid = in.readString ();
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

        public String getSpelling() {
            characterParser.setResource(nickname);//通过拼音转换类,把汉字转换成拼音//
            this.spelling=characterParser.getSpelling();
            return spelling;
        }
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

    public GroupListBean() {
    }

    protected GroupListBean(Parcel in) {
        this.status = in.readInt ();
        this.msg = in.readString ();
        this.result = new ArrayList <ResultBean> ();
        in.readList ( this.result, ResultBean.class.getClassLoader () );
    }

    public static final Parcelable.Creator <GroupListBean> CREATOR = new Parcelable.Creator <GroupListBean> () {
        @Override
        public GroupListBean createFromParcel(Parcel source) {
            return new GroupListBean ( source );
        }

        @Override
        public GroupListBean[] newArray(int size) {
            return new GroupListBean[size];
        }
    };
}
