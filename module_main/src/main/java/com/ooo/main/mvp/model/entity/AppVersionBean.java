package com.ooo.main.mvp.model.entity;

import java.io.Serializable;

/**
 * @author lanjian
 * creat at 2019/9/18
 * description
 */
public class AppVersionBean implements Serializable {

    /**
     * status : 1
     * result : {"Code":0,"Msg":"","UpdateStatus":1,"VersionCode":1,"VersionName":"1.0.0","ModifyContent":"1、优化api接口。 2、添加使用demo演示。 3、新增自定义更新服务API接口。 4、优化更新提示界面。","DownloadUrl":"http://5761.iiio.top//NewH5/Webprom.html","ApkSize":2048,"ApkMd5":""}
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
         * Code : 0
         * Msg :
         * UpdateStatus : 1
         * VersionCode : 1
         * VersionName : 1.0.0
         * ModifyContent : 1、优化api接口。 2、添加使用demo演示。 3、新增自定义更新服务API接口。 4、优化更新提示界面。
         * DownloadUrl : http://5761.iiio.top//NewH5/Webprom.html
         * ApkSize : 2048
         * ApkMd5 :
         */

        private int Code;
        private String Msg;
        private int UpdateStatus;
        private int VersionCode;
        private String VersionName;
        private String ModifyContent;
        private String DownloadUrl;
        private int ApkSize;
        private String ApkMd5;

        public int getCode() {
            return Code;
        }

        public void setCode(int Code) {
            this.Code = Code;
        }

        public String getMsg() {
            return Msg;
        }

        public void setMsg(String Msg) {
            this.Msg = Msg;
        }

        public int getUpdateStatus() {
            return UpdateStatus;
        }

        public void setUpdateStatus(int UpdateStatus) {
            this.UpdateStatus = UpdateStatus;
        }

        public int getVersionCode() {
            return VersionCode;
        }

        public void setVersionCode(int VersionCode) {
            this.VersionCode = VersionCode;
        }

        public String getVersionName() {
            return VersionName;
        }

        public void setVersionName(String VersionName) {
            this.VersionName = VersionName;
        }

        public String getModifyContent() {
            return ModifyContent;
        }

        public void setModifyContent(String ModifyContent) {
            this.ModifyContent = ModifyContent;
        }

        public String getDownloadUrl() {
            return DownloadUrl;
        }

        public void setDownloadUrl(String DownloadUrl) {
            this.DownloadUrl = DownloadUrl;
        }

        public int getApkSize() {
            return ApkSize;
        }

        public void setApkSize(int ApkSize) {
            this.ApkSize = ApkSize;
        }

        public String getApkMd5() {
            return ApkMd5;
        }

        public void setApkMd5(String ApkMd5) {
            this.ApkMd5 = ApkMd5;
        }
    }
}
