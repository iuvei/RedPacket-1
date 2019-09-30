package com.haisheng.easeim.mvp.model.entity;

import java.io.Serializable;

/**
 * 0
 * creat at 2019/9/20
 * description
 */
public class PublicResponseBean implements Serializable {
    /**
     * status : 1
     * result : null
     * msg : 设置成功
     */

    private int status;
    private Object result;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
