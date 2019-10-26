package com.ooo.main.mvp.model.entity;

import com.blankj.utilcode.util.AppUtils;

import java.io.Serializable;

import me.jessyan.armscomponent.commonres.utils.ConvertNumUtils;

/**
 * creat at 2019/9/18
 * description
 */
public class AppVersionBean implements Serializable {


    /**
     * status : 1
     * result : {"name":"TT","copyright":"TT","levelurl":"","androidappurl":"123","iosappurl":"456","AndVersionName":"789","IosVersionName":"741","content":"66666666666666"}
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

    public static class ResultBean implements Serializable{
        /**
         * name : TT
         * copyright : TT
         * levelurl :
         * androidappurl : 123
         * iosappurl : 456
         * AndVersionName : 789
         * IosVersionName : 741
         * content : 66666666666666
         */

        private String name;
        private String copyright;
        private String levelurl;
        private String androidappurl;
        private String iosappurl;
        private String AndVersionName;
        private String IosVersionName;
        private String content;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public String getLevelurl() {
            return levelurl;
        }

        public void setLevelurl(String levelurl) {
            this.levelurl = levelurl;
        }

        public String getAndroidappurl() {
            return androidappurl;
        }

        public void setAndroidappurl(String androidappurl) {
            this.androidappurl = androidappurl;
        }

        public String getIosappurl() {
            return iosappurl;
        }

        public void setIosappurl(String iosappurl) {
            this.iosappurl = iosappurl;
        }

        public String getAndVersionName() {
            return AndVersionName;
        }

        public void setAndVersionName(String AndVersionName) {
            this.AndVersionName = AndVersionName;
        }

        public String getIosVersionName() {
            return IosVersionName;
        }

        public void setIosVersionName(String IosVersionName) {
            this.IosVersionName = IosVersionName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isNeetUpdate(){
            if (AppUtils.getAppVersionCode ()< ConvertNumUtils.stringToInt ( AndVersionName )){
                return true;
            }
            return false;
        }
    }
}
