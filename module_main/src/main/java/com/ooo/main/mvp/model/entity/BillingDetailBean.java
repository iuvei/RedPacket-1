package com.ooo.main.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author lanjian
 * creat at 2019/9/17
 * description
 */
public class BillingDetailBean implements Serializable {

    /**
     * status : 1
     * result : {"list":[{"gold":"100.00","details":"后台充值","addtime":"2019-09-09 12:02:39"},{"gold":"500.00","details":"后台充值","addtime":"2019-09-03 16:11:08"},{"gold":"5.00","details":"二充奖励","addtime":"2019-09-03 16:10:50"},{"gold":"10.00","details":"后台充值","addtime":"2019-09-03 16:10:50"},{"gold":"10.00","details":"首充奖励","addtime":"2019-09-03 15:39:46"},{"gold":"100.00","details":"后台充值","addtime":"2019-09-03 15:39:45"}],"paytype":null,"allmoney":725}
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
         * list : [{"gold":"100.00","details":"后台充值","addtime":"2019-09-09 12:02:39"},{"gold":"500.00","details":"后台充值","addtime":"2019-09-03 16:11:08"},{"gold":"5.00","details":"二充奖励","addtime":"2019-09-03 16:10:50"},{"gold":"10.00","details":"后台充值","addtime":"2019-09-03 16:10:50"},{"gold":"10.00","details":"首充奖励","addtime":"2019-09-03 15:39:46"},{"gold":"100.00","details":"后台充值","addtime":"2019-09-03 15:39:45"}]
         * paytype : null
         * allmoney : 725
         */

        private Object paytype;
        private double allmoney;
        private List <ListBean> list;

        public Object getPaytype() {
            return paytype;
        }

        public void setPaytype(Object paytype) {
            this.paytype = paytype;
        }

        public double getAllmoney() {
            return allmoney;
        }

        public void setAllmoney(double allmoney) {
            this.allmoney = allmoney;
        }

        public List <ListBean> getList() {
            return list;
        }

        public void setList(List <ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements  Serializable{
            /**
             * gold : 100.00
             * details : 后台充值
             * addtime : 2019-09-09 12:02:39
             */

            private String gold;
            private String details;
            private String addtime;

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
        }
    }
}
