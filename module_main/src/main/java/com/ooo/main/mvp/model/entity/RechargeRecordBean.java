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
     * result : {"list":[{"gold":"600.00","details":"后台充值","addtime":"2019-09-21 14:28:35","paytype":"0"},{"gold":"100.00","details":"后台充值","addtime":"2019-09-09 12:02:39","paytype":"0"},{"gold":"500.00","details":"后台充值","addtime":"2019-09-03 16:11:08","paytype":"0"},{"gold":"10.00","details":"后台充值","addtime":"2019-09-03 16:10:50","paytype":"0"},{"gold":"100.00","details":"后台充值","addtime":"2019-09-03 15:39:45","paytype":"0"}],"paytype":["后台充值","线上充值"],"allmoney":1310}
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
         * list : [{"gold":"600.00","details":"后台充值","addtime":"2019-09-21 14:28:35","paytype":"0"},{"gold":"100.00","details":"后台充值","addtime":"2019-09-09 12:02:39","paytype":"0"},{"gold":"500.00","details":"后台充值","addtime":"2019-09-03 16:11:08","paytype":"0"},{"gold":"10.00","details":"后台充值","addtime":"2019-09-03 16:10:50","paytype":"0"},{"gold":"100.00","details":"后台充值","addtime":"2019-09-03 15:39:45","paytype":"0"}]
         * paytype : ["后台充值","线上充值"]
         * allmoney : 1310
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

        public static class ListBean implements Serializable{
            /**
             * gold : 600.00
             * details : 后台充值
             * addtime : 2019-09-21 14:28:35
             * paytype : 0
             */

            private String gold;
            private String details;
            private String addtime;
            private String paytype;

            public String getGold() {
                return gold;
            }

            public void setGold(String gold) {
                this.gold = gold;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getPaytype() {
                return paytype;
            }

            public void setPaytype(String paytype) {
                this.paytype = paytype;
            }
        }
    }
}
