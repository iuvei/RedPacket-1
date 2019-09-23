package me.jessyan.armscomponent.commonsdk.entity;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VersionEntity implements Serializable {

    @SerializedName("UpdateStatus")
    private int updateStatus;

    @SerializedName("VersionCode")
    private int versionCode;

    @SerializedName("VersionName")
    private String versionName;

    @SerializedName("ModifyContent")
    private String modifyContent;

    @SerializedName("DownloadUrl")
    private String downloadUrl;

    @SerializedName("ApkSize")
    private int apkSize;

    @SerializedName("ApkMd5")
    private String apkMd5;

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getModifyContent() {
        return modifyContent;
    }

    public void setModifyContent(String modifyContent) {
        this.modifyContent = modifyContent;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getApkSize() {
        return apkSize;
    }

    public void setApkSize(int apkSize) {
        this.apkSize = apkSize;
    }

    public String getApkMd5() {
        return apkMd5;
    }

    public void setApkMd5(String apkMd5) {
        this.apkMd5 = apkMd5;
    }
}
