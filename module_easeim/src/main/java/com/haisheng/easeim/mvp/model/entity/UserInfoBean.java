package com.haisheng.easeim.mvp.model.entity;

import java.io.Serializable;

/**
 * creat at 2019/10/14
 * description
 * 客服信息
 */
public class UserInfoBean implements Serializable{

    /**
     * status : 1
     * result : {"id":"10","uniacid":"1","nickname":"首页客服","hxid":"kf-sy","thumb":"http://5949.iiio.top/attachment/","content":"1.你好2.请问有什么可以帮助的3.你的问题是什么4.？？？"}
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

    public static class ResultBean implements Serializable {
        /**
         * id : 10
         * uniacid : 1
         * nickname : 首页客服
         * hxid : kf-sy
         * thumb : http://5949.iiio.top/attachment/
         * content : 1.你好2.请问有什么可以帮助的3.你的问题是什么4.？？？
         */

        private String id;
        private String uniacid;
        private String nickname;
        private String hxid;
        private String thumb="";
        private String content;

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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHxid() {
            return hxid;
        }

        public void setHxid(String hxid) {
            this.hxid = hxid;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
