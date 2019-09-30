package com.ooo.main.mvp.model.entity;

import java.io.Serializable;

/**
 * 0
 * creat at 2019/9/18
 * description 实名认证
 */
public class CertificationBean implements Serializable {

    /**
     * status : 1
     * result : 设置成功
     */

    private int status;
    private String result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
