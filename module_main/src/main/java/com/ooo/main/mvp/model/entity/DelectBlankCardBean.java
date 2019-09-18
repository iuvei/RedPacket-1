package com.ooo.main.mvp.model.entity;

/**
 * @author lanjian
 * creat at 2019/9/18
 * description 解除银行卡
 */
public class DelectBlankCardBean {

    /**
     * status : 1
     * result : 解绑成功
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
