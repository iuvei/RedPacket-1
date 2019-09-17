package com.ooo.main.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author lanjian
 * creat at 2019/9/17
 * description
 * 提现记录
 */
public class WithRecordBean {

    /**
     * status : 1
     * result : {"list":[{"gold":"100","addtime":"2019-08-20 18:26:14","details":"提现成功"},{"gold":"100","addtime":"2019-08-20 18:21:08","details":"提现失败"},{"gold":"100","addtime":"2019-08-20 18:20:34","details":"申请提现"},{"gold":"100","addtime":"2019-08-20 18:20:18","details":"提现失败"},{"gold":"100","addtime":"2019-08-20 18:20:16","details":"申请提现"}],"paytype":["申请提现","提现成功","提现失败"],"allmoney":500}
     */

    private int status;
    private ResultBean result;

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

    public static class ResultBean {
        /**
         * list : [{"gold":"100","addtime":"2019-08-20 18:26:14","details":"提现成功"},{"gold":"100","addtime":"2019-08-20 18:21:08","details":"提现失败"},{"gold":"100","addtime":"2019-08-20 18:20:34","details":"申请提现"},{"gold":"100","addtime":"2019-08-20 18:20:18","details":"提现失败"},{"gold":"100","addtime":"2019-08-20 18:20:16","details":"申请提现"}]
         * paytype : ["申请提现","提现成功","提现失败"]
         * allmoney : 500
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

        public static class ListBean implements Serializable {
            /**
             * gold : 100
             * addtime : 2019-08-20 18:26:14
             * details : 提现成功
             */

            private String gold;
            private String addtime;
            private String details;

            public String getGold() {
                return gold;
            }

            public void setGold(String gold) {
                this.gold = gold;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }
        }
    }
}
