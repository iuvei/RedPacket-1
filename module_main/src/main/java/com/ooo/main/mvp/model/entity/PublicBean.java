package com.ooo.main.mvp.model.entity;

import java.io.Serializable;

/**
 * @author lanjian
 * creat at 2019/9/18
 * description 公用的请求响应实体类
 */
public class PublicBean implements Serializable {

    /**
     * status : 0
     * result : null
     * msg : 你以设置了支付密码
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
