package me.jessyan.armscomponent.commonsdk.http;

/**
 * creat at 2019/10/15
 * description
 */
public class ResponseBean {


    /**
     * status : 2
     * msg : token失效，请重新登录
     */

    private int status;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
