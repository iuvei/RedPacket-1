package com.haisheng.easeim.mvp.model.entity;

/**
 * creat at 2019/9/24
 * description
 */
public class CheckPayPasswordBean {

    /**
     * status : 1
     * result : 0
     * msg :
     */

    private int status;
    private int result;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //是否已经设置过支付密码
    public boolean isHasPayPassword(){
        if (result==0){
            return false;
        }
        return true;
    }
}
