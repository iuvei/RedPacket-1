package com.ooo.main.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

import me.jessyan.armscomponent.commonsdk.utils.CharacterParser;

/**
 * creat at 2019/9/21
 * description 通讯录好友实体类
 */
public class ContactForMobileBean implements Serializable{

    /**
     * status : 1
     * result : [{"id":"970000","logintime":"1569634309","nickname":"露露","avatar":"http://5761.iiio.top/attachment/images/1/2019/09/df3z2367jJKbL8f6fq2l3M5lbNN2Q6.png","gender":"0"},{"id":"969988","logintime":"1569637338","nickname":"哇哈哈","avatar":"http://5761.iiio.top/attachment/images/1/2019/08/l8l28R7Uz8720L5p0p75P0pp7rlpk5.png","gender":"1"}]
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

    public static class ResultBean implements Serializable {
        /**
         * id : 970000
         * logintime : 1569634309
         * nickname : 露露
         * avatar : http://5761.iiio.top/attachment/images/1/2019/09/df3z2367jJKbL8f6fq2l3M5lbNN2Q6.png
         * gender : 0
         */

        private String id;
        private String logintime;
        private String nickname;
        private String avatar;
        private String gender;
        private String mobile;
        private final CharacterParser characterParser;
        private String spelling;

        public ResultBean() {
            characterParser = new CharacterParser ();
        }

        public String getSpelling() {
            characterParser.setResource(nickname);//通过拼音转换类,把汉字转换成拼音//
            this.spelling=characterParser.getSpelling();
            return spelling;
        }

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
            characterParser.setResource(nickname);//通过拼音转换类,把汉字转换成拼音//
            this.spelling=characterParser.getSpelling();
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
