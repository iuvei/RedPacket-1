package com.ooo.main.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * creat at 2019/9/17
 * description
 * 充值记录
 */
public class RechargeRecordBean implements Serializable {

    /**
     * status : 1
     * result : {"list":[{"id":"58","uniacid":"1","uid":"970021","money":"300.52","paycode":"支付宝66666666","payname":"马云","payimg":"http://5949.iiio.top/attachment/images/1/2019/10/Fy67qRxuF677xTTcRHtF7KW7FFJF2k.jpg","addtime":"2019-10-11 19:09:06","restime":null,"status":"0","postscript":""}],"paytype":["后台充值","线下充值"],"allmoney":0}
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
         * list : [{"id":"58","uniacid":"1","uid":"970021","money":"300.52","paycode":"支付宝66666666","payname":"马云","payimg":"http://5949.iiio.top/attachment/images/1/2019/10/Fy67qRxuF677xTTcRHtF7KW7FFJF2k.jpg","addtime":"2019-10-11 19:09:06","restime":null,"status":"0","postscript":""}]
         * paytype : ["后台充值","线下充值"]
         * allmoney : 0
         */

        private int allmoney;
        private List <ListBean> list;
        private List <String> paytype;

        public int getAllmoney() {
            return allmoney;
        }

        public void setAllmoney(int allmoney) {
            this.allmoney = allmoney;
        }

        public List <ListBean> getList() {
            return list;
        }

        public void setList(List <ListBean> list) {
            this.list = list;
        }

        public List <String> getPaytype() {
            return paytype;
        }

        public void setPaytype(List <String> paytype) {
            this.paytype = paytype;
        }

        public static class ListBean {
            /**
             * id : 58
             * uniacid : 1
             * uid : 970021
             * money : 300.52
             * paycode : 支付宝66666666
             * payname : 马云
             * payimg : http://5949.iiio.top/attachment/images/1/2019/10/Fy67qRxuF677xTTcRHtF7KW7FFJF2k.jpg
             * addtime : 2019-10-11 19:09:06
             * restime : null
             * status : 0
             * postscript :
             */

            private String id;
            private String uniacid;
            private String uid;
            private String money;
            private String paycode;
            private String payname;
            private String payimg;
            private String addtime;
            private Object restime;
            private String status;
            private String postscript;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUniacid() {
                return uniacid;
            }

            public void setUniacid(String uniacid) {
                this.uniacid = uniacid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getPaycode() {
                return paycode;
            }

            public void setPaycode(String paycode) {
                this.paycode = paycode;
            }

            public String getPayname() {
                return payname;
            }

            public void setPayname(String payname) {
                this.payname = payname;
            }

            public String getPayimg() {
                return payimg;
            }

            public void setPayimg(String payimg) {
                this.payimg = payimg;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public Object getRestime() {
                return restime;
            }

            public void setRestime(Object restime) {
                this.restime = restime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
            public String getStatusValue() {
                //0待审核 1充值成功 2拒绝审核
                if (status.equals ( "0" )){
                    return "待审核";
                }else if(status.equals ( "1" )){
                    return "充值成功";
                }else{
                    return "审核拒绝";
                }
            }

            public String getPostscript() {
                return postscript;
            }

            public void setPostscript(String postscript) {
                this.postscript = postscript;
            }
        }
    }
}
