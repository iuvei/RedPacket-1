package com.ooo.main.mvp.model.entity;

import java.io.Serializable;

public class RewardBean implements Serializable {

    private int imgResId;
    private int bigImgResId;

    public RewardBean(int imgResId, int bigImgResId) {
        this.imgResId = imgResId;
        this.bigImgResId = bigImgResId;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public int getBigImgResId() {
        return bigImgResId;
    }

    public void setBigImgResId(int bigImgResId) {
        this.bigImgResId = bigImgResId;
    }
}
