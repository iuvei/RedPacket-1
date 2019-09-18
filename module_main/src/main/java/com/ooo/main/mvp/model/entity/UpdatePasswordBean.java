package com.ooo.main.mvp.model.entity;

/**
 * @author lanjian
 * creat at 2019/9/18
 * description 修改密码实体类
 */
public class UpdatePasswordBean {

    /**
     * status : 0
     * result : null
     * msg : 旧密码不正确
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
